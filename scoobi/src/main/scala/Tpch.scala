package com.nicta.scoobi.examples

import com.nicta.scoobi._
import com.nicta.scoobi.Scoobi._
import com.nicta.scoobi.io.text._
import java.io._
import java.util.Date
import java.text.SimpleDateFormat
import java.util.regex.Pattern
import com.nicta.scoobi.lib.Join._
import scala.collection.mutable.Buffer

object Tpch {

   val format = new SimpleDateFormat("yy/MM/dd HH:mm:ss.SSS")
    def getTime = format.format(new Date)+" "
    def log(s : String) {
	  System.err.println(getTime+s)
    }
   
  def filter[A : Manifest : WireFormat](dlist : DList[A], p: A => Boolean): DList[A] = {
    val dofn = new DoFn[A, A] {
      def setup() = {
        log("Starting filter")
      }
      def process(input: A, emitter: Emitter[A]) = {if (p(input)) { emitter.emit(input) }}
      def cleanup(emitter: Emitter[A]) = {
        log("Ending filter")
      }
    }
    dlist.parallelDo(dofn)
  }
  def main(args: Array[String]) = withHadoopArgs(args) { a =>
    """
266407760 1199314252.550 http://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/RAF_roundel.svg/75px-RAF_roundel.svg.png -
266407764 1199314252.591 http://es.wikipedia.org/skins-1.5/common/images/magnify-clip.png -
266407762 1199314252.549 http://upload.wikimedia.org/fundraising/2007/people-meter-ltr.png -
266407763 1199314252.545 http://upload.wikimedia.org/fundraising/2007/red-button-left.png -
   """
    val (inputPath, outputPath) = ("/home/stivo/master/testdata/currenttmp", "output")
	// on command line
	//    grep "de.wiki" currenttmp | grep ".svg" | grep -v "thumb.php"
    val lines: DList[String] = TextInput.fromTextFile(inputPath)
	val ordersFile = "/home/stivo/master/testdata/tpch/orders.tbl"
	val customersFile = "/home/stivo/master/testdata/tpch/customer.tbl"
	val orders = TextInput.fromTextFile(ordersFile)
	val customers = TextInput.fromTextFile(customersFile)
	def select(line : String, toselect : Iterable[Int]) = {
      val list = line.split("|")
      var out = Buffer[String]()
      for (int <- toselect) {
        out += list(int)
      }
      out.toArray
    }
    val orderProj =orders.map{ line =>
      val list = line.split("\\|")
      	(list(1), ((list(3), list(4))))
    }.filter(_._2._1.toDouble >= 250000)
    
    val customerProj = customers.map{
      line =>
      val list = line.split("\\|")
      	(list(0), list(1))
    }
    val joined = join(orderProj, customerProj)
    val result = joined
	DList.persist(TextOutput.toTextFile(result, outputPath + "/word-results"));
  }
  
}
