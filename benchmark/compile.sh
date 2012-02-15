
VERSIONS="manual scoobi"
if [[ $1 ]]; then
	VERSIONS=$1
fi;

for VERSION in $VERSIONS
do
	cd ../$VERSION ; rm target/*.jar; sbt11 package-hadoop ; cp target/*.jar ../benchmark/progs/$VERSION.jar; cd -
done
