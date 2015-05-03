# JAR

A zip with standardized metadata files, often used to distribute multiple Java `.class` files, i.e. libraries, in one file.

Specification: <http://docs.oracle.com/javase/7/docs/technotes/guides/jar/index.html>

## Create

With the default manifest fields only:

    jar cf Out.jar Main.class Lib.class

Add custom manifest fields:

    jar cfm Out.jar META-INF/MANIFEST.MF Main.class Lib.class

## Open

A JAR is just a ZIP with a standardized file structure inside: you can open it up directly with:

    unzip a.jar

But you can also use:

    jar xvf a.jar

List file in JAR without opening it:

    jar tf a.jar

## Run

Run a Jar with `java`: you must define the `Main` class in the manifest: <http://stackoverflow.com/questions/1238145/how-to-run-a-jar-file>

## META-INF directory

Contains the Jar metadata in multiple files.

### MANIFEST.MF

Parameters:

- `Manifest-Version`: jar format version, 1.0 for JDK 7. Automatically added by `jar`.
- `Created-By`: Automatically added by Jar. E.g.: `1.8.0_40 (Oracle Corporation)`.
- `Main-Class`: what to do by default on `java -jar a.jar`. Sample value: `com.mastergaurav.test.app.MainClass`
- `Class-Path`: space separated paths added to the classpath. Sample value: `lib/one.jar lib/two.jar`

#### Preamsin-Class

Can be used to get the instrumentation: <http://stackoverflow.com/questions/52353/in-java-what-is-the-best-way-to-determine-the-size-of-an-object>

Runs the: `public static void premain(String args, Instrumentation inst)` method of the given class before the main.

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
