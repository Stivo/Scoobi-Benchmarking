package com.nicta.scoobi.examples

import com.nicta.scoobi._
import com.nicta.scoobi.Scoobi._
import com.nicta.scoobi.io.text._
import java.io._
import java.util.Date
import java.text.SimpleDateFormat
import java.util.regex.Pattern
import com.nicta.scoobi.lib.Join._

object WikipediaLog {

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
    val (inputPath, outputPath) = ("/home/stivo/master/testdata/pagecounts", "output")
	// on command line
	//    grep "de.wiki" currenttmp | grep ".svg" | grep -v "thumb.php"
    val lines: DList[String] = TextInput.fromTextFile(inputPath)
	val path = "/home/stivo/master/testdata/"
//	val firstIn = TextInput.fromTextFile(inputPath)
   case class LogEntry(lang : String, project : String, url : String, count : Int, size : Long)
//   case class LogEntriesByLang(lang : String, count : Int)
	val all = filter(lines, {x : String => true})
	val parsed = all.map{
    	line =>
    	  val parts = line.split(" ")
    	  val project = if (parts(0).contains(".")) {
    	    parts(0).split("\\.").last
    	  } else {
    	    "p"
    	  }
    	  val lang = parts(0).takeWhile(_!='.')
    	  LogEntry(lang, project, parts(1), parts(2).toInt, parts(3).toLong)
    }

    val ms = lines.filter{
      line =>
        val first = line.split(" ")(0)
    	  if (first.contains(".")) {
    	    val proj = first.split("\\.",2)(1)
    	    proj=="m"
    	  } else false
    }
    val dsqs = lines.filter{
      line =>
        val first = line.split(" ")(0)
    	  if (first.contains(".")) {
    	    val proj = first.split("\\.",2)(1)
    	    proj=="d" || proj=="q"
    	  } else false
    }

    val ens = lines.filter(_.contains("en"))
    val uks = lines.filter{ x=> x.contains("u") || x.contains("k")}
//    val bylang = parsed.map(x=> (x.lang, 1)).groupByKey
//    val langStats = bylang.combine((x : Int, y : Int) => (x+ y))
//    val byproject = parsed.map(x=> (x.project, 1)).groupByKey
//    val projectStats = byproject.combine((x : Int, y : Int) => (x+ y))
//    val totalSize = parsed.map(x=> (0, (x.url, x.size*x.count))).groupByKey.combine(((x : (String, Long), y:(String, Long)) => (x._1, x._2+y._2)))
//    val als = parsed.filter(_.project==("als"))
//    val hitler = parsed.filter(_.url.contains("Hitler"))
//    val als = all.filter(_.startsWith("als"))
//    val hitler = all.filter(_.contains("Hitler"))
//    val hitlerandals = hitler.++(als) 
//	DList.persist(TextOutput.toTextFile(langStats, outputPath + "/lang"));
//	DList.persist(TextOutput.toTextFile(projectStats, outputPath + "/project"));
//    DList.persist(TextOutput.toTextFile(als, outputPath + "/als"));
//    DList.persist(TextOutput.toTextFile(hitler, outputPath + "/hitler"));
//    DList.persist(TextOutput.toTextFile(totalSize, outputPath + "/totalSize"));
//    DList.persist(TextOutput.toTextFile(all, outputPath + "/lines"))
//	DList.persist(TextOutput.toTextFile(ens, outputPath + "/ens"));
//	DList.persist(TextOutput.toTextFile(uks, outputPath + "/uks"));
	DList.persist(TextOutput.toTextFile(dsqs, outputPath + "/dsqs"));
//	DList.persist(TextOutput.toTextFile(ms, outputPath + "/ms"));
  }
  
}
