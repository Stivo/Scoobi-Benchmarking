package examples.weblog

import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.Text
import org.apache.hadoop.io.NullWritable
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat

object WebLogAnalyzer {
  
  var doYears = false
  
  def main(args : Array[String]) { 
     	doYears = args.length>2
	    val conf = new Configuration();
	    val job = new Job(conf, "web log analysis");
		job.setJobName( "web log analysis" );
		job.setJarByClass( getClass() );
		job.setMapperClass( classOf[WebLogAnalyzerMapper] );
//		jobConf.setReducerClass( WordCountReducer.class );
		job.setMapOutputKeyClass( classOf[Text] );
		job.setMapOutputValueClass( classOf[NullWritable]);
		job.setOutputKeyClass( classOf[Text] );
		job.setOutputValueClass( classOf[NullWritable] );
		//jobConf.setCombinerClass( WordCountReducer.class );
		//System.err.println(args.mkString(" "))
		FileInputFormat.addInputPath(job, new Path( args(0) ) );
//		job.setOutputFormat( classOf[TextOutputFormat[_,_]] );
		FileOutputFormat.setOutputPath(job, new Path( args(1) ) );
		job.waitForCompletion(true)
  }

}