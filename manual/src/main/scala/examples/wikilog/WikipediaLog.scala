package examples.wikilog

import java.text.SimpleDateFormat
import org.apache.hadoop.mapreduce.Mapper
import org.apache.hadoop.io._
import java.util.Date
import org.apache.hadoop.mapreduce.Reducer
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs

object Funcs {
  def matcher1(line : String) = line.startsWith("als")
  def matcher2(line : String) = line.startsWith("als")
}


class WikipediaLogMapper extends Mapper[LongWritable, Text, Text, NullWritable] {
 val format = new SimpleDateFormat("yy/MM/dd HH:mm:ss.SSS")
    def getTime = format.format(new Date)+" "
    def log(s : String) {
	  System.err.println(getTime+s)
    }
 
  override def run(context : Mapper[LongWritable,Text,Text,NullWritable]#Context) {
      log("Starting filter")
      val outs = new MultipleOutputs(context)
      while (context.nextKeyValue()) {
//    	  val inKey = context.getCurrentKey();
    	  val inValue = context.getCurrentValue();
    	  if (inValue.toString.startsWith("als")) {
//    	    System.err.println("Found one "+inValue.toString)
//    	    outs.write("all", inValue, NullWritable.get)
    	    context.write(inValue, NullWritable.get)
    	  }
    	  if (inValue.toString.contains("Hitler"))
    	    outs.write("hitler", inValue, NullWritable.get)
      }
      outs.close()
      log("Ending filter")
      ()
  }

}

class Mapper1Job extends Mapper[LongWritable, Text, Text, ByteWritable] {
 val format = new SimpleDateFormat("yy/MM/dd HH:mm:ss.SSS")
    def getTime = format.format(new Date)+" "
    def log(s : String) {
	  System.err.println(getTime+s)
    }
 
  override def run(context : Mapper[LongWritable,Text,Text,ByteWritable]#Context) {
      log("Starting filter")
      val i1 = new ByteWritable(0)
      val i2 = new ByteWritable(1)
      while (context.nextKeyValue()) {
//    	  val inKey = context.getCurrentKey();
    	  val inValue = context.getCurrentValue();
//    	  val first = inValue.toString.split(" ")(0)
//	   	  if (first.contains(".")) {
//	    	    val proj = first.split("\\.",2)(1)
//	    	    if (proj=="m")
//	    	      context.write(inValue, i1)
//	    	    if (proj=="d" || proj=="q")
//	    	      context.write(inValue, i2)
//	    	  }
    	  val s = inValue.toString
	   	  if (s.contains("en")) 
	    	      context.write(inValue, i1)
	      if (s.contains("u") || s.contains("k"))
	    	      context.write(inValue, i2)
    	 
      }
      log("Ending filter")
      ()
  }

}

class Reducer1Job extends Reducer[Text, ByteWritable, Text, NullWritable] {
  override def run(context : Reducer[Text,ByteWritable,Text,NullWritable]#Context) {
    
    val byte : Byte = 0
      val outs = new MultipleOutputs(context)
       while (context.nextKeyValue()) {
         val key = context.getCurrentKey()
         val values = context.getValues().iterator()
         while (values.hasNext()) {
        	 val value = values.next()
	         if (byte == value.get()) {
	           context.write(key, NullWritable.get)
	         } else {
		         outs.write("dq", key, NullWritable.get())
	         }
         }
       }
      outs.close()
  }  
}


class MapperForBothJobs extends Mapper[LongWritable, Text, Text, NullWritable] {
 val format = new SimpleDateFormat("yy/MM/dd HH:mm:ss.SSS")
    def getTime = format.format(new Date)+" "
    def log(s : String) {
	  System.err.println(getTime+s)
    }
 
  override def run(context : Mapper[LongWritable,Text,Text,NullWritable]#Context) {
      log("Starting filter")
      val outs = new MultipleOutputs(context)
      while (context.nextKeyValue()) {
//    	  val inKey = context.getCurrentKey();
    	  val inValue = context.getCurrentValue();
//    	  val first = inValue.toString.split(" ")(0)
//    	  if (first.contains("."))
//    	  {
//    	    val proj = first.split("\\.",2)(1)
// 	    	      context.write(inValue, i2)
//    	    if (proj=="m")
//    	      context.write(inValue, NullWritable.get)
//    	    if (proj=="d" || proj=="q")
//    	      outs.write("dq", inValue, NullWritable.get)
//    	  }
    	  val s = inValue.toString
	   	  if (s.contains("en")) 
	    	 context.write(inValue, NullWritable.get)
	      if (s.contains("u") || s.contains("k"))
	         outs.write("dq", inValue, NullWritable.get)
      }
      outs.close()
      log("Ending filter")
      ()
  }

}


class Mapper2Jobs1 extends Mapper[LongWritable, Text, Text, NullWritable] {
 val format = new SimpleDateFormat("yy/MM/dd HH:mm:ss.SSS")
    def getTime = format.format(new Date)+" "
    def log(s : String) {
	  System.err.println(getTime+s)
    }
 
  override def run(context : Mapper[LongWritable,Text,Text,NullWritable]#Context) {
      log("Starting filter")
      val outs = new MultipleOutputs(context)
      while (context.nextKeyValue()) {
//    	  val inKey = context.getCurrentKey();
    	  val inValue = context.getCurrentValue();
//    	  val first = inValue.toString.split(" ")(0)
//    	  if (first.contains("."))
//    	  {
//    		  val proj = first.split("\\.",2)(1)
//    		  if (proj=="m")
//    			  context.write(inValue, NullWritable.get)
//    	  }
    	  val s = inValue.toString
	   	  if (s.contains("en")) 
	    	 context.write(inValue, NullWritable.get)
      }
      outs.close()
      log("Ending filter")
      ()
  }

}

class Mapper2Jobs2 extends Mapper[LongWritable, Text, Text, NullWritable] {
 val format = new SimpleDateFormat("yy/MM/dd HH:mm:ss.SSS")
    def getTime = format.format(new Date)+" "
    def log(s : String) {
	  System.err.println(getTime+s)
    }
 
  override def run(context : Mapper[LongWritable,Text,Text,NullWritable]#Context) {
      log("Starting filter")
      val outs = new MultipleOutputs(context)
      while (context.nextKeyValue()) {
//    	  val inKey = context.getCurrentKey();
    	  val inValue = context.getCurrentValue();
//    	  val first = inValue.toString.split(" ")(0)
//    	  if (first.contains("."))
//    	  {
//    	    val proj = first.split("\\.",2)(1)
//    	    if (proj=="d" || proj=="q")
//    	      context.write(inValue, NullWritable.get)
//    	  }
    	  val s = inValue.toString
	   	  if (s.contains("u") || s.contains("k")) 
	    	 context.write(inValue, NullWritable.get)
      }
      outs.close()
      log("Ending filter")
      ()
  }

}


class OnlyTextMapper extends Mapper[LongWritable, Text, Text, NullWritable] {
 val format = new SimpleDateFormat("yy/MM/dd HH:mm:ss.SSS")
    def getTime = format.format(new Date)+" "
    def log(s : String) {
	  System.err.println(getTime+s)
    }
 
  override def run(context : Mapper[LongWritable,Text,Text,NullWritable]#Context) {
      log("Starting filter")
      while (context.nextKeyValue()) {
//    	  val inKey = context.getCurrentKey();
    	  val inValue = context.getCurrentValue();
//    	  if (inValue.toString.startsWith("als")) {
//    	    System.err.println("Found one "+inValue.toString)
//    	    outs.write("all", inValue, NullWritable.get)
    	    context.write(inValue, NullWritable.get)
//    	  }
      }
      log("Ending filter")
      ()
  }

}

class JoinExampleReducer extends Reducer[DoubleWritable, NullWritable, Text, NullWritable] {
  
//  def run(context : Context) {
//    
//  }
// val leftCollection = ArrayBuffer[String]()
// val rightCollection = ArrayBuffer[String]()
// val format = new SimpleDateFormat("yy/MM/dd HH:mm:ss.SSS")
//    def getTime = format.format(new Date)+" "
//    def log(s : String) {
//	  System.err.println(getTime+s)
//    }
// 
// override def reduce(key : DoubleWritable, values : java.util.Iterator[JoinValue], 
//     output : OutputCollector[Text, NullWritable], reporter : Reporter) : Unit = {
//	   leftCollection.clear()
//	   rightCollection.clear()
//		 while (values.hasNext()) {
//		   val value = values.next()
//		   if (value.typ == Types.left) {
//		     leftCollection += (value.valueLeft)
//		   }
//		   if (value.typ == Types.right) {
//		     rightCollection += (value.valueRight)
//		   }
//		   
//	    }
//	 	joined(key, leftCollection, rightCollection, output)
//    ()
//  }
//  
//  def joined(key : DoubleWritable, lefts : Iterable[String], rights : Iterable[String], 
//      output : OutputCollector[Text, NullWritable]) {
//	 if (lefts.isEmpty || rights.isEmpty) {
//	   return
//	 }
//	 val outKeyWrapper = new Text()
//	 // perform 
//	 for (left <- lefts) {
//	   for (right <- rights) {
//	     val out = left.split(" ")(0)+ " and "+right.split(" ")(0)
//	     outKeyWrapper.set(out)
//	     output.collect(outKeyWrapper, NullWritable.get())
//	   }
//	 }
////	 val out = "at time "+key+" there were "+lefts.size+" requests, one second later "+rights.size
//  }
}
 
