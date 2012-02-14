
cd ../manual ; sbt11 package-hadoop ; cp target/*.jar ../benchmark/progs/manual.jar; cd -
cd ../scoobi ; sbt11 package-hadoop ; cp target/*.jar ../benchmark/progs/scoobi.jar; cd -
