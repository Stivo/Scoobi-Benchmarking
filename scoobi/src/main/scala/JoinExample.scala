package com.nicta.scoobi.examples

import com.nicta.scoobi._
import com.nicta.scoobi.Scoobi._
import com.nicta.scoobi.io.text._
import java.io._
import java.util.Date
import java.text.SimpleDateFormat
import java.util.regex.Pattern
import com.nicta.scoobi.lib.Join._

object JoinExample {

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
	val path = "/home/stivo/master/testdata/"
	val firstIn = TextInput.fromTextFile(path+"xaa")
	val secondIn = TextInput.fromTextFile(path+"xab")
    val left =firstIn.map{ line =>
        (line.split(" ")(2), 1)
    }
    val right = secondIn.map{
      line =>
        (line.split(" ")(2), 1)
    }
    val joined = join(left, right)
    val result = joined.groupByKey.combine((x : Int, y : Int) => x+y)
	DList.persist(TextOutput.toTextFile(result, outputPath + "/word-results"));
  }
  
}

object rand {
  lazy val rand = new scala.util.Random()
}