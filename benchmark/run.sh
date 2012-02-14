#!/bin/bash

PROG=generated.test.WebLogAnalyzerJob
#PROG=generated.test.WordCountJob
#PROG=examples.join.JoinExampleJob
#PROG=examples.wikilog.WikipediaLogJob
if [[ $2 ]]; then
	rm -rf $2
fi;
rm -rf word-count/output;
rm -rf output;rm -rf outputtmp; time hadoop jar test.jar $PROG $@ 2> ./mine.txt > hotspot.log
if [[ $2 ]]; then
	du -h $2
	rm -rf $2
fi;

