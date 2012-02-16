package examples.weblog

import java.text.SimpleDateFormat
import java.util.regex.Pattern
import java.util.Date
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.NullWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Mapper


class WebLogAnalyzerMapper extends Mapper[LongWritable, Text, Text, NullWritable] {
  val format = new SimpleDateFormat("yy/MM/dd HH:mm:ss.SSS")
    def getTime = format.format(new Date)+" "
    def log(s : String) {
	  System.err.println(getTime+s)
    }
  
  	def matches(pattern : Pattern, input : String) = {
  	  val m = pattern.matcher(input);
  	  m.matches()
  	}
  	
    def run40Filters(context : Mapper[LongWritable,Text,Text,NullWritable]#Context) {
      log("Starting filter")
	  val outKeyWrapper = new Text();
      val outValueWrapper = NullWritable.get();
	  while (context.nextKeyValue){
		    val in = context.getCurrentValue().toString();
	        if (in.length > 0) {
	          if (in.length > 4) {
	            if (in.length > 8) {
	              if (in.length > 12) {
	                if (in.length > 16) {
	                  if (in.length > 20) {
	                    if (in.length > 24) {
	                      if (in.length > 28) {
	                        if (in.length > 32) {
	                          if (in.length > 36) {
	                            if (in.length > 40) {
	                              if (in.length > 44) {
	                                if (in.length > 48) {
	                                  if (in.length > 52) {
	                                    if (in.length > 56) {
	                                      if (in.length > 60) {
	                                        if (in.length > 64) {
	                                          if (in.length > 68) {
	                                            if (in.length > 72) {
	                                              if (in.length > 76) {
	                                                if (in.length > 80) {
	                                                  if (in.length > 84) {
	                                                    if (in.length > 88) {
	                                                      if (in.length > 92) {
	                                                        if (in.length > 96) {
	                                                          if (in.length > 100) {
	                                                            if (in.length > 104) {
	                                                              if (in.length > 108) {
	                                                                if (in.length > 112) {
	                                                                  if (in.length > 116) {
	                                                                    if (in.length > 120) {
	                                                                      if (in.length > 124) {
	                                                                        if (in.length > 128) {
	                                                                          if (in.length > 132) {
	                                                                            if (in.length > 136) {
	                                                                              if (in.length > 140) {
	                                                                                if (in.length > 144) {
	                                                                                  if (in.length > 148) {
	                                                                                    if (in.length > 152) {
	                                                                                      if (in.length > 156) {
	                                                                                        if (in.length > 200) {
	                                                                                          outKeyWrapper.set(in);
	                                                                                          context.write(outKeyWrapper, outValueWrapper);
	                                                                                        }
	                                                                                      }
	                                                                                    }
	                                                                                  }
	                                                                                }
	                                                                              }
	                                                                            }
	                                                                          }
	                                                                        }
	                                                                      }
	                                                                    }
	                                                                  }
	                                                                }
	                                                              }
	                                                            }
	                                                          }
	                                                        }
	                                                      }
	                                                    }
	                                                  }
	                                                }
	                                              }
	                                            }
	                                          }
	                                        }
	                                      }
	                                    }
	                                  }
	                                }
	                              }
	                            }
	                          }
	                        }
	                      }
	                    }
	                  }
	                }
	              }
	            }
	          }
	        }

	  }
      log("Ending filter")

	} 

    def runFindYears(context : Mapper[LongWritable,Text,Text,NullWritable]#Context) {
  	
      log("Starting filter")
	  val outKeyWrapper = new Text();
      val outValueWrapper = NullWritable.get();
	  while (context.nextKeyValue){
	    val inKeyWrapper = context.getCurrentKey();
	    val inValueWrapper = context.getCurrentValue();
	    val inValue = inValueWrapper.toString();
		    
	    if (inValue.length > 0) {
		    // flatmap code
		    val x1 = inValue.split(" ", 0);
		    var x2 = 0 
		    while(x2 < x1.length) {
		      val x3 = x1(x2);
		      val x10 = x3.contains("/");
		      if (x10) {
			      val x4 = x3.split("/", 0);
			      var x5 = 0;
			      while (x5 < x4.length) {
			        val x6 = x4(x5);
//				        val pattern1 = Pattern.compile("18\\d{2}")
//				        val x7 = pattern1.matcher(x6).matches();
			        val x7 = x6.matches("18\\d{2}")
			        if (x7) {
//			          val x8 = x6.length == 4;
//			          if (x8) {
//			            val x9 = x6.startsWith("18")
//			            if (x9) {
			              outKeyWrapper.set(x6);
			              context.write(outKeyWrapper, outValueWrapper);
//			            }
//			          }
			        }
			        x5+=1
			      }
		      }
		      x2+=1
		    }
	    }
		    
	  }
      log("Ending filter")
	}

   def runFindYearsHoisted(context : Mapper[LongWritable,Text,Text,NullWritable]#Context) {
  	
      log("Starting filter")
	  val outKeyWrapper = new Text();
      val outValueWrapper = NullWritable.get();
      val pattern1 = Pattern.compile(" ")
	  val pattern2 = Pattern.compile("/")
      val pattern3 = Pattern.compile("18\\d{2}")
	  while (context.nextKeyValue){
	    val inKeyWrapper = context.getCurrentKey();
	    val inValueWrapper = context.getCurrentValue();
	    val inValue = inValueWrapper.toString();
		    
	    if (inValue.length > 0) {
		    // flatmap code
	    	val x1 = pattern1.split(inValue, 0)
		    var x2 = 0 
		    while(x2 < x1.length) {
		      val x3 = x1(x2);
		      val x10 = x3.contains("/");
		      if (x10) {
			      val x4 = pattern2.split(x3);
			      var x5 = 0;
			      while (x5 < x4.length) {
			        val x6 = x4(x5);
			        val x7 = pattern3.matcher(x6).matches();
//			        val x7 = x6.matches("18\\d{2}")
			        if (x7) {
//			          val x8 = x6.length == 4;
//			          if (x8) {
//			            val x9 = x6.startsWith("18")
//			            if (x9) {
			              outKeyWrapper.set(x6);
			              context.write(outKeyWrapper, outValueWrapper);
//			            }
//			          }
			        }
			        x5+=1
			      }
		      }
		      x2+=1
		    }
	    }
		    
	  }
      log("Ending filter")
	}

    
    override def run(context : Mapper[LongWritable,Text,Text,NullWritable]#Context) {
	  if (context.getConfiguration.getBoolean("doYears", false)) {
	    if (context.getConfiguration.getBoolean("doYearsHoisted", false)) {
		  	runFindYearsHoisted(context)
	    } else {
	      runFindYears(context)
	    }
	  } else {
		  throw new RuntimeException("Not implemented yet") 
	  }
	}
	
//	def extracted(outKeyWrapper : Text, outValueWrapper : NullWritable, x3 : String, output : OutputCollector[Text, NullWritable] ) {
//		val x5 = x3.endsWith(".svg");
//		  if (x5) {
//		      val x6 = x3.split("/");
//		      val x7 = x6.last;
//		      outKeyWrapper.set(x7);
//		      output.collect(outKeyWrapper, outValueWrapper);
//		  }
//	  }
}
