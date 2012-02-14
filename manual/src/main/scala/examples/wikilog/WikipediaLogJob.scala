package examples.wikilog
import org.apache.hadoop.mapred.JobConf
import org.apache.hadoop.io._
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.fs.Path
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat

object WikipediaLogJob {
     def do2Jobs() {
	     val conf = new Configuration();
	     val job = new Job(conf, "2jobs1");
	     job.setMapperClass(classOf[Mapper2Jobs1])
	     job.setJarByClass(classOf[Mapper2Jobs2])
	
	     job.setInputFormatClass(classOf[TextInputFormat]);
	     job.setOutputFormatClass(classOf[TextOutputFormat[_,_]])
	     
	     job.setMapOutputKeyClass(classOf[Text])
	     job.setMapOutputValueClass(classOf[NullWritable])
	     
	     FileInputFormat.setInputPaths(job, new Path("/home/stivo/master/testdata/pagecounts"));
	     FileOutputFormat.setOutputPath(job, new Path("output/output1"));
	     
	     job.waitForCompletion(true)
	    
	     val job2 = new Job(conf, "2jobs2");
	     job2.setMapperClass(classOf[Mapper2Jobs2])
	     job2.setJarByClass(classOf[Mapper2Jobs2])
	
	     job2.setInputFormatClass(classOf[TextInputFormat]);
	     job2.setOutputFormatClass(classOf[TextOutputFormat[_,_]])
	     
	     job2.setMapOutputKeyClass(classOf[Text])
	     job2.setMapOutputValueClass(classOf[NullWritable])
	     
	     FileInputFormat.setInputPaths(job2, new Path("/home/stivo/master/testdata/pagecounts"));
	     FileOutputFormat.setOutputPath(job2, new Path("output/output2"));
	     
	     job2.waitForCompletion(true)

     }

   def main(args : Array[String])  = {
     do2Jobs()
//     doBothJobs()
//     do1Job()
   }
   
   def do1Job() {
	     val conf = new Configuration();
	     val job = new Job(conf, "2jobs1");
	     job.setMapperClass(classOf[Mapper1Job])
	     job.setJarByClass(classOf[Mapper2Jobs2])
	     MultipleOutputs.addNamedOutput(job, "dq", 
	         classOf[TextOutputFormat[_,_]],
	         classOf[Text], classOf[NullWritable])
	
	     job.setReducerClass(classOf[Reducer1Job])
	     job.setInputFormatClass(classOf[TextInputFormat]);
	     job.setOutputFormatClass(classOf[TextOutputFormat[_,_]])
	     
	     job.setMapOutputKeyClass(classOf[Text])
	     job.setMapOutputValueClass(classOf[ByteWritable])
	     job.setOutputKeyClass(classOf[Text])
	     job.setOutputValueClass(classOf[NullWritable])
	     FileInputFormat.setInputPaths(job, new Path("/home/stivo/master/testdata/pagecounts"));
	     FileOutputFormat.setOutputPath(job, new Path("output/output1"));
	     
	     job.waitForCompletion(true)
     
   }
   
   def doBothJobs() {
     val conf = new Configuration();
	 val job = new Job(conf, "2jobs1");
    
	 job.setMapperClass(classOf[MapperForBothJobs])
	 job.setJarByClass(classOf[Mapper2Jobs2])
     MultipleOutputs.addNamedOutput(job, "dq", 
         classOf[TextOutputFormat[_,_]],
         classOf[Text], classOf[NullWritable])
     job.setInputFormatClass(classOf[TextInputFormat]);
     job.setOutputFormatClass(classOf[TextOutputFormat[_,_]])
     
     job.setMapOutputKeyClass(classOf[Text])
     job.setMapOutputValueClass(classOf[NullWritable])
     
     FileInputFormat.setInputPaths(job, new Path("/home/stivo/master/testdata/pagecounts"));
     FileOutputFormat.setOutputPath(job, new Path("output/output1"));
     
     job.waitForCompletion(true)
     val job2 = new Job(conf, "wikiLogMerge")
     
     job2.setInputFormatClass(classOf[TextInputFormat]);
     job2.setOutputFormatClass(classOf[TextOutputFormat[_,_]])
     job2.setMapperClass(classOf[OnlyTextMapper])
     job2.setMapOutputKeyClass(classOf[Text])
     job2.setMapOutputValueClass(classOf[NullWritable])
     FileInputFormat.setInputPaths(job2, new Path("output/output1/dq*"));
     FileOutputFormat.setOutputPath(job2, new Path("output/output2"));
     job2.waitForCompletion(true)

   }
}
