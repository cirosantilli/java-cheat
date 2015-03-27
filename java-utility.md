# java utility

This article is about the `java` command line utility.

Front-end for the JVM.

Run a `.class` file with a `public static void main` method:

    javac Main.java
    java Main

Useful options:

-   `-version`

-   `-fullversion`

-   `-ea`: enable assertions. Without this the `assert` statement is not run.

-   `-cp`: classpath: colon separated list of where to look for classes.

    Can take either directories or `.class` / `.jar` files.

    The `CLASSPATH` environment variable set, that gives the default path.

    Otherwise, the path is `.`

    Example:

        javac Main.java
        java Main

    works because `Main` is in the default path.

        javac -cp .:/usr/share/java/junit4.jar Main.java
        java -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore Main

    finds JUnit by the `.jar` path. `Main` is just a command line argument on the invocation TODO check.

-   `-Da.b=c`: passes a custom property to the program. It can be retrieved with `System.getProperty("a.b")`.

-   `-server` and `-client`.

    <http://stackoverflow.com/questions/198577/real-differences-between-java-server-and-java-client>

    Switch between different HotSpot optimization modes.

    `-server` spends more time to optimize JIT as is often desired for server code.

## verbose

Print runtime information to stdout:

    java -verbose -verbose:gc -verbose:jni Main

## X

`-X` non-standard options (TODO what does that mean exactly? Oracle only?):

-   `-X`: list extended options

-   `-Xms256M`: initial heap size

-   `-Xmx256M`: maximum heap size

-   `-XX:MaxPermSize=256M`

    Maximum PermGen size. Deprecated in JDK 8 in favor of `-XX:MaxMetaspaceSize`.

-   `-XX:PermSize=size`

    If exceeded, trigger garbage collection. Deprecated in JDK 8, in favor of `-XX:MetaspaceSize`.

-   `-XX:+UseCompressedOops`

    <https://wikis.oracle.com/display/HotSpotInternals/CompressedOops>

    Use object addresses that are 32 bits long even in a 64 bit architecture.

    TODO

## JAVA_TOOL_OPTIONS

Can be used to pass certain command line options to `java`, e.g.:

    JAVA_TOOL_OPTIONS='-Da=b' java Main

TODO which options exactly can be passed in that way? `-version` did not work.

