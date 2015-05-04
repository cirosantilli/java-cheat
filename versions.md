# Versions

1.7 is commonly referred to as Java 7, 1.8 as Java 8 and so on.

Java 8 was released in March 2014.

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

You can switch between multiple Java versions on a single machine with `update-java-alternatives`.

## Backwards compatibility

Java is highly binary backwards compatible, with some source breaks along the way.

- <http://stackoverflow.com/questions/1654923/are-there-any-specific-examples-of-backward-incompatibilities-between-java-versi>
- <http://stackoverflow.com/questions/4692626/is-jdk-upward-or-backward-compatible>
- <http://stackoverflow.com/questions/16143684/can-java-8-code-be-compiled-to-run-on-java-7-jvm>

Oracle mentions three types of compatibility:

- Source: Source compatibility concerns translating Java source code into class files including whether or not code still compiles at all.
- Binary: Binary compatibility is defined in The Java Language Specification as preserving the ability to link without error.
- Behavioral: Behavioral compatibility includes the semantics of the code that is executed at runtime.

## Project coin

Project that decided which proposed language extensions should or not be added to Java 7: <https://blogs.oracle.com/darcy/entry/project_coin_final_five>

## J2SE

Old name used for Java between versions 1.2 and 1.5.

The 2 was from the 1.2 which introduced major changes.
