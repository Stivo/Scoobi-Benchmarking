#!/bin/bash

#PROG=generated.test.WebLogAnalyzerJob
#PROG=generated.test.WordCountJob
#PROG=examples.join.JoinExampleJob
PROG=examples.wikilog.WikipediaLog

for VERSION in manual scoobi 
do
	echo Running $VERSION
	if [[ $2 ]]; then
		rm -rf $2
	fi;
	time hadoop jar progs/$VERSION.jar $PROG $@ 2> ./$VERSION.txt
	if [[ $2 ]]; then
		du -h $2
		rm -rf $2
	fi;

done

