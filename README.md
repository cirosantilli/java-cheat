# Java Cheat

Java information, cheatsheets and mini-projects.

- [Main.java](Main.java): main Java 7 cheat
- [JUnit](junit/)
- [Maven](maven/)
- [JVM](jvm.md)
- [Javadoc](javadoc/)
- [javac](javac.md)

WIP:

- [JDB](jdb.md)

## Install

Ubuntu 14.04 install:

    make install-deps-ubuntu

## Standards

The language and the JVM standards can be found at: <http://docs.oracle.com/javase/specs/>
Those only contain the language specification, not the Java Class Library.

Note that they are two separate standards:

- the Java language specification (JLS), which determines on a high level what the language does
- the JVM specification specifies (JVMS), which determines on a lower level how the bytecode is interpreted

### Java Community Process

### JCP

<https://jcp.org>

The Java standard is not completely controlled by Oracle,
but maintained by a committee composed of many large organizations:
<http://en.wikipedia.org/wiki/Java_Community_Process>

### JSR

### Java Specification Requests

RFC of the JCP. E.g., Servlets are specified in JSR 315:
<https://jcp.org/aboutJava/communityprocess/final/jsr315/>

## Documentation

Tutorials: <http://docs.oracle.com/javase/tutorial/>
Download them under the downloads section.

Docs: <http://docs.oracle.com/javase/7/docs/>
see under the downloads section.

JCL reference:
<http://docs.oracle.com/javase/7/docs/api/>
TODO: is that official?

## JRE vs JDK

The JVM is included in the Java Runtime Environment (JRE),
which also contains things like all the compiled standard library `.class` files.
JRE does not contain for example `javac` which is only needed by developers:
it is an end user package.

For development, one must have the Java Developer Kit (JDK),
which includes things like:

- `javac` which transforms `.java` files into `.class` files,
- `javadoc` which generates HTML documentation from comments. JDK also includes the JRE.

Java was present in LSB, but got removed from LSB 4.1 because of licensing issues.
[Source](http://www.h-online.com/open/news/item/Java-removed-from-Linux-Standard-Base-4-1-1205368.html).

### JCL

Java Class Library.

<http://en.wikipedia.org/wiki/Java_Class_Library>

Name of the stdlib `.class` implementation. Present in the JRE.

## Editions

Java comes different forms for different applications.

Both the libraries and the JVM change between those implementations.

- SE: standard edition. Intended for for personal desktops / laptops.
- EE
- ME: micro edition. For portable devices such as cell phones.

Each of those has their own implementations. Oracle implements all.

Source: <http://stackoverflow.com/questions/2857376/difference-between-java-se-ee-me>

### EE

Contains "Enterprise" APIs, which is a generic term for APIs mostly useful for large enterprises.

<http://stackoverflow.com/questions/106820/what-is-java-ee>

Sources:

- <http://stackoverflow.com/questions/3821640/what-is-difference-between-tomcat-and-jboss-and-glassfish>
- <http://stackoverflow.com/questions/23563340/glassfish-vs-tomcat>
- <http://stackoverflow.com/questions/327793/how-would-you-compare-apache-tomcat-glassfish-as-production-servers>

Implementations include:

- GlassFish
- TomEE
- WildFly (old JBoss)
- IBM Websphere
- Oracle Weblogic

#### Tomcat

Apache Tomcat implements only JavaServer Pages and Servlets,
which are only a part of the full EE.

#### Jetty

<http://eclipse.org/jetty/>

Like Tomcat, an implementation of Servlets.

Seems to be easier to deploy than Tomcat, and specially good as a development server.

<http://stackoverflow.com/questions/302977/tomcat-vs-jetty>

#### GlassFish

GlassFish is the reference implementation of many parts of the API, but not all.
[This table](https://java.net/projects/javaee-spec/pages/Specifications)
summarizes reference implementations.

For example, [GlassFish is the reference for Servlets](https://jcp.org/aboutJava/communityprocess/final/jsr315/),
but not for JSONP.

It is open source, started by Sun, and sponsored by Oracle.

Oracle has dropped support for it in 2013 in favor of it's Oracle Weblogic,
but it still is the reference implementation. TODO does it still support?

#### WildFly

#### JBoss

JBoss, now renamed as WildFly is another open source EE implementation
<http://en.wikipedia.org/wiki/WildFly> TODO why another?
Maintained by RedHat.

## Versions

1.7 is commonly referred to as Java 7, 1.8 as Java 8 and so on.

You can get version with:

    java -version
    javac -version

Sample output:

    1.8.0-ea

More verbose version:

    java -fullversion
    javac -fullversion

Sample output:

    1.8.0-ea-b97

### J2SE

Old name used for Java between versions 1.2 and 1.5.

The 2 was from the 1.2 which introduced major changes.

### Java 8

Released in March 2014.

## Implementations

### SE implementations

Java specifies interfaces for Java Bytecode and the standard library,
but not the implementation.

There are two main implementations of of the Java Virtual Machine:

-   Oracle: Closed source.

    Some companies will recommend that you use this proprietary implementation,
    and might even rely on proprietary extensions, meaning that the other implementations
    won't work...

-   OpenJDK

It seems that in 2006 all Java code was opened under a modified GNU license
and that OpenJDK and Oracle JDK share most of their source as of 2013.

To run Java apps on your browser you also need the Java plugin for your browser.
IcedTea is a popular open source project that implements that integration:
<http://en.wikipedia.org/wiki/IcedTea>

### OpenJDK

<http://openjdk.java.net/>

Open source, reference implementation.
Resulted from a partial open sourcing of Oracle's Java implementation.

Source browser and mercurial URLs: <http://hg.openjdk.java.net/>. Mercurial.

Get sources:

    hg clone http://hg.openjdk.java.net/jdk7/jdk7
    hg clone http://hg.openjdk.java.net/jdk8/jdk8
    cd jdk?
    sh get_source.sh

The `get_source.sh` is needed since the source is split across many HG repositories.

JDK 8 takes up 900M.

<http://stackoverflow.com/questions/4121922/how-can-i-checkout-the-openjdk-from-the-mercurial-repository>

Sample output of `java -version` on Ubuntu 14.04:

    java version "1.7.0_65"
    OpenJDK Runtime Environment (IcedTea 2.5.3) (7u71-2.5.3-0ubuntu0.14.04.1)
    OpenJDK 64-Bit Server VM (build 24.65-b04, mixed mode)

#### Source tree

- `jdk/src/share/classes/java`: JCL

### Oracle implementations

Oracle has bought at HotSpot and JRockit with other companies.
<http://stackoverflow.com/questions/8068717/jrockit-jvm-versus-hotspot-jvm>

Oracle said that it is making an effort to make them converge.

Sample output of `java -version` on Ubuntu 14.04:

    java version "1.8.0_25"
    Java(TM) SE Runtime Environment (build 1.8.0_25-b17)
    Java HotSpot(TM) 64-Bit Server VM (build 25.25-b02, mixed mode)

Note how HotSpot is mentioned on the output.

#### HotSpot

<http://en.wikipedia.org/wiki/HotSpot>

Oracle VM implementation. Started at Sun, which Oracle bought.

#### JRockit

Started in Appeal and BEA Systems, which Oracle bought.

## Utilities

Java comes with several CLI utilities.

### update-java-alternatives

If you are maintaining two Java version on a single Linux machine, you can run `update-alternatives` for executables in one go.

List available alternatives:

    update-java-alternatives -l

Sample output:

    java-7-oracle 1 /usr/lib/jvm/java-7-oracle
    java-8-oracle 2 /usr/lib/jvm/java-8-oracle

Choose one of the alternatives:

    sudo update-java-alternatives -s java-7-oracle

### javap

Disassembles `.class` compiled files so you can see their internal structure.

TODO why the `p` in the name? Because it Prints to stdout?

### java_vm

TODO

### jcontrol

General set on the fly JVM options.

TODO

### jconsole

GUI JVM monitor. Finds all running Java apps and allows you to view them.

Does it use JMX, or is JMX just a part of what is uses? Vs `jvisualvm`?

### jvisualvm

TODO vs the `jconsole`?

## JAR

A file that encapsulates multiple Java `.class` files.

It is the standard way to distribute Java libraries.

A JAR is just a ZIP with a standardized file structure inside:
you can open it up directly with:

    unzip a.jar

But you can also use:

    jar -xvf a.jar

List file in JAR without opening it:

    jar -tf a.jar

TODO what is the structure of a Jar?

Run a Jar with `java`: you must define the `Main` class in the manifest:
<http://stackoverflow.com/questions/1238145/how-to-run-a-jar-file>
TODO: create a jar and run it.

### Manifest (JAR)

Parameters:

- `Main-Class`: what to do by default on `java -jar a.jar`. Sample value: `com.mastergaurav.test.app.MainClass`
- `Class-Path`: space separated paths added to the classpath. Sample value: `lib/one.jar lib/two.jar`

### WAR

<http://en.wikipedia.org/wiki/WAR_%28file_format%29>

`Web application ARchive`.

JAR that contains stuff to deploy a web app like Java Servlets, HTML, JSP and configuration.

It is the input Servlet / JSP implementations like Tomcat and GlassFish expect in order to run a web app.

It is just a `jar` with some mandatory internal file structure.

Unpack with:

    jar -xvf a.war

Create with:

    jar -cvf a.war *

TODO where is the format specified?
<http://stackoverflow.com/questions/11333208/where-are-java-archive-file-structure-specifications>

## JAVA_HOME

TODO what is it? Is it necessary even if the Java executable is already selected?
<http://stackoverflow.com/questions/5102022/what-does-the-java-home-environment-variable-do>

If:

    readlink -f `which java`

says:

    /usr/lib/jvm/java-7-oracle/jre/bin/java

then you could set:

    export JAVA_HOME='/usr/lib/jvm/java-7-oracle'

## Classpath

<http://en.wikipedia.org/wiki/Classpath_%28Java%29>

Where Java will look for user defined classes.

Command line option:

    javac -classpath /some/path

Environment variable:

    export CLASSPATH=/some/path

## Style guides

- <https://google-styleguide.googlecode.com/svn/trunk/javaguide.html>
