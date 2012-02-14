#!/bin/bash

#PROG=generated.test.WebLogAnalyzerJob
#PROG=generated.test.WordCountJob
#PROG=examples.join.JoinExampleJob
PROG=examples.wikilog.WikipediaLog

VERSION=manual

if [[ $2 ]]; then
	rm -rf $2
fi;
time hadoop jar progs/$VERSION.jar $PROG $@ 2> ./$VERSION.txt
if [[ $2 ]]; then
	du -h $2
	rm -rf $2
fi;

VERSION=scoobi
### COPIED ### (need to look up how to create a function in bash)
if [[ $2 ]]; then
	rm -rf $2
fi;
time hadoop jar progs/$VERSION.jar $PROG $@ 2> ./$VERSION.txt
if [[ $2 ]]; then
	du -h $2
	rm -rf $2
fi;

