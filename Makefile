package:
	perl -pi -w -e 's:^//package:package:' *java

unpackage:
	perl -pi -w -e 's:^package gps://package gps:' *java

tests: TestQuery.java
	javac -Xlint:unchecked TestQuery.java && \
	java org.junit.runner.JUnitCore TestQuery

clean:
	rm *.class

