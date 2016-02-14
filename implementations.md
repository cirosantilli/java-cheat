# Implementations

## SE implementations

Java specifies interfaces for Java Bytecode and the standard library, but not the implementation.

There are two main implementations of of the Java Virtual Machine:

-   Oracle: Closed source.

    Some companies will recommend that you use this proprietary implementation, and might even rely on proprietary extensions, meaning that the other implementations won't work...

-   OpenJDK

It seems that in 2006 all Java code was opened under a modified GNU license and that OpenJDK and Oracle JDK share most of their source as of 2013.

To run Java apps on your browser you also need the Java plugin for your browser. IcedTea is a popular open source project that implements that integration: <http://en.wikipedia.org/wiki/IcedTea>

## Harmony

<https://en.wikipedia.org/wiki/Apache_Harmony>

Retired in 2011.

Google used it in Android until 4.4, when it moved to OpenJDK.

## Oracle implementations

Oracle has bought at HotSpot and JRockit with other companies. <http://stackoverflow.com/questions/8068717/jrockit-jvm-versus-hotspot-jvm>

Oracle said that it is making an effort to make them converge.

Sample output of `java -version` on Ubuntu 14.04:

    java version "1.8.0_25"
    Java(TM) SE Runtime Environment (build 1.8.0_25-b17)
    Java HotSpot(TM) 64-Bit Server VM (build 25.25-b02, mixed mode)

Note how HotSpot is mentioned on the output.

### HotSpot

<http://en.wikipedia.org/wiki/HotSpot>

Oracle VM implementation. Started at Sun, which Oracle bought.

### JRockit

Started in Appeal and BEA Systems, which Oracle bought.
