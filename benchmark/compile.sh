
for VERSION in manual scoobi 
do
	cd ../$VERSION ; rm target/*.jar; sbt11 package-hadoop ; cp target/*.jar ../benchmark/progs/$VERSION.jar; cd -
done
