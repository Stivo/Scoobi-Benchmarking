Benchmarking Scoobi against hand written code
---
Currently running examples:

* examples.wikilog.WikipediaLog: Uses the wikibench access trace
* examples.weblog.WebLogAnalyzer: Uses the pagecounts from wikipedia

This project contains 3 folders:

 *    manual: The hand written programs, which should resemble what our DSL will output
 *    scoobi: The same programs written with scoobi
 *    benchmark: Some helper scripts to compile, run, collect and interpret the results

### Installation:

1. Download the test data sets. They are listed in testdatas.txt. I use only one file, sometimes not even the full one. For cluster testing, multiple files should probably be used to have a bigger total datasize.
2. I used milliseconds for more exact times. compare.py currently expects those kind of timestamps.
Either:
	- Use the provided log4j.properties used by hadoop
	- Only change the timestamp format in log4j.properties used by hadoop
	- Change compare.py to recognize desired format for the timestamp
3. I use a different version of scoobi, which does not override the default progress reporter. Otherwise the Total written and read sizes are not reported. See my github repository.
4. My sbt installation is called sbt11 and is a 0.11.2. Maybe create an alias for that.

### Basic usage:

- Change the programs in scoobi and manual. Make sure they are in the same package and are named the same.
- within benchmark folder:
	- run compile.sh. This will create the jars for both programs and put them in progs, as manual.jar and scoobi.jar
	- run run.sh. First argument is the input folder, second one is the output folder. Additional program specific arguments can be given. This will:
		- Clear the output folder
		- Run the manual programs, show the total time and the total output size on the console
		- Collect system.err in manual.txt or scoobi.txt, respectively
	- run copy.sh, first argument is used as an addon to the filename. The manual.txt and scoobi.txt will be copied to the results folder.
	- run compare.py, which creates a table. Might be later extended to create plots again.

Change whatever you need.

### TODO: 
- License: Apache? Public Domain?
- Describe which job uses which data set
