# JAR

A file that encapsulates multiple Java `.class` files.

It is the standard way to distribute Java libraries.

A JAR is just a ZIP with a standardized file structure inside: you can open it up directly with:

    unzip a.jar

But you can also use:

    jar -xvf a.jar

List file in JAR without opening it:

    jar -tf a.jar

TODO what is the structure of a Jar?

Run a Jar with `java`: you must define the `Main` class in the manifest: <http://stackoverflow.com/questions/1238145/how-to-run-a-jar-file> TODO: create a jar and run it.

## Manifest

Parameters:

- `Main-Class`: what to do by default on `java -jar a.jar`. Sample value: `com.mastergaurav.test.app.MainClass`
- `Class-Path`: space separated paths added to the classpath. Sample value: `lib/one.jar lib/two.jar`

## WAR

<http://en.wikipedia.org/wiki/WAR_%28file_format%29>

`Web application ARchive`.

JAR that contains stuff to deploy a web app like Java Servlets, HTML, JSP and configuration.

It is the input Servlet / JSP implementations like Tomcat and GlassFish expect in order to run a web app.

It is just a `jar` with some mandatory internal file structure.

Unpack with:

    jar -xvf a.war

Create with:

    jar -cvf a.war *

TODO where is the format specified? <http://stackoverflow.com/questions/11333208/where-are-java-archive-file-structure-specifications>

## util jar

The JCL contains classes that deal with JARs: <http://docs.oracle.com/javase/7/docs/api/java/util/jar/package-summary.html>
