#!/bin/bash
#PROG=com.nicta.scoobi.examples.WebLogAnalyzer
PROG=com.nicta.scoobi.examples.JoinExample
PROG=com.nicta.scoobi.examples.WikipediaLog
#PROG=com.nicta.scoobi.examples.Tpch
rm output/ -rf
time hadoop jar target/Scoobi_Word_Count-hadoop-0.1.jar $PROG 2> ~/master/hadoopPackage/scoobi.txt 

