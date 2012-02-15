package examples.weblog

import org.apache.hadoop.fs.Path
import org.apache.hadoop.mapred.TextInputFormat
import org.apache.hadoop.mapred.FileInputFormat
import org.apache.hadoop.mapred.FileOutputFormat
import org.apache.hadoop.mapred.JobClient
import org.apache.hadoop.mapred.JobConf
import org.apache.hadoop.mapred.TextOutputFormat
import org.apache.hadoop.io.Text
import org.apache.hadoop.io.NullWritable

object WebLogAnalyzer {
  
  var doYears = false
  
  def main(args : Array[String]) { 
     	doYears = args.length>2
    	val jobConf = new JobConf( classOf[WebLogAnalyzerMapper]);
		jobConf.setJobName( "web log analysis" );
		jobConf.setJarByClass( getClass() );
		jobConf.setMapRunnerClass( classOf[WebLogAnalyzerMapper] );
//		jobConf.setReducerClass( WordCountReducer.class );
		jobConf.setMapOutputKeyClass( classOf[Text] );
		jobConf.setMapOutputValueClass( classOf[NullWritable]);
		jobConf.setOutputKeyClass( classOf[Text] );
		jobConf.setOutputValueClass( classOf[NullWritable] );
		//jobConf.setCombinerClass( WordCountReducer.class );
		jobConf.setInputFormat( classOf[TextInputFormat]);
		//System.err.println(args.mkString(" "))
		FileInputFormat.addInputPath(jobConf, new Path( args(0) ) );
		jobConf.setOutputFormat( classOf[TextOutputFormat[_,_]] );
		FileOutputFormat.setOutputPath(jobConf, new Path( args(1) ) );
		JobClient.runJob( jobConf );
  }

}