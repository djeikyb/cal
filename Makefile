p = /opt/jars

dbunit_home		:= $(p)#/dbunit
junit_home 		:= $(p)#/junit
mysqlj_home		:= $(p)#/mysqlj
slf4j_home 		:= $(p)#/slf4j
sqlite_home		:= $(p)#/sqlite
joda_home			:= $(p)#/joda-time
jcurses_home	:= $(p)#/jcurses


dbunit_jar    := $(dbunit_home)/dbunit-2.4.8.jar
junit_jar     := $(junit_home)/junit-4.10.jar
mysql_jar     := $(mysqlj_home)/mysql-connector-java-5.1.20-bin.jar
slf4j_api_jar := $(slf4j_home)/slf4j-api-1.6.4.jar
slf4j_nop_jar := $(slf4j_home)/slf4j-nop-1.6.4.jar
sqlite_jar    := $(sqlite_home)/sqlite.jar
joda_jar      := $(joda_home)/joda-time-2.1.jar
jcurses_jar   := $(jcurses_home)/jcurses-linux-0.9.5b.jar


package = gps.tasks.task3663


class_path := .:$(junit_jar):$(dbunit_jar):$(slf4j_api_jar):$(slf4j_nop_jar):$(mysql_jar):$(joda_jar):/home/jake


flags := "-Xlint:unchecked"


package:
	perl -pi -w -e 's:^//package:package:' *java

unpackage:
	perl -pi -w -e 's:^package gps://package gps:' *java

tm: TestModify.java
	javac -Xlint:unchecked -cp $(class_path) TestModify.java && \
	java -cp $(class_path) org.junit.runner.JUnitCore $(package).TestModify | grep -v "at org.junit"

tr: TestRegistry.java
	javac -Xlint:unchecked -cp $(class_path) TestRegistry.java && \
	java -cp $(class_path) org.junit.runner.JUnitCore $(package).TestRegistry | grep -v "at org.junit"

tq: TestQuery.java
	javac -Xlint:unchecked -cp $(class_path) TestQuery.java && \
	java -cp $(class_path) org.junit.runner.JUnitCore $(package).TestQuery | grep -v "at org.junit"

gc: GimmeConn.java
	javac $(flags) -cp $(class_path) GimmeConn.java
	java -cp $(class_path) $(package).GimmeConn

ui: Main.java
	javac $(flags) -cp $(class_path) Main.java
	java -cp $(class_path) $(package).Main

clean:
	rm *.class

