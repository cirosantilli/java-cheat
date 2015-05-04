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

-   `-Da.b=c`: passes a custom property to the program. It can be retrieved with `System.getProperty("a.b")`.

-   `-server` and `-client`.

    <http://stackoverflow.com/questions/198577/real-differences-between-java-server-and-java-client>

    Switch between different HotSpot optimization modes.

    `-server` spends more time to optimize JIT as is often desired for server code.

## cp

## classpath

Colon separated list of where to look for classes.

Can take either directories or `.class` / `.jar` files.

The `CLASSPATH` environment variable set, that gives the default path.

Otherwise, the path is `.`

Example:

    javac Main.java
    java Main

works because `Main` is in the default path.

Jars can be passed to `-cp` and are treated like directories:

    javac -cp .:/usr/share/java/junit4.jar Main.java
    # Main is a command line argument to JUnitCore's main method.
    java -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore Main

Java 6 allows `-cp` to contain `*` wildcards, which expands to all `.jar` in that directory:

    java -cp '.:lib/*' Main

Don't forget to quote `'.:lib/*'`, or else bash will expand it.

## jar

Run the main class inside a `.jar` as configured by its metadata.

Cannot be used together with `-cp`. In that case you need to pass the `.jar` to `-cp` and call it's main class with the full path.

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

-   `-XX:+PrintOptoAssembly`

    View JIT compiled code: <http://stackoverflow.com/questions/1503479/how-to-see-jit-compiled-code-in-jvm>

### UserCompressedOops

`-XX:+UseCompressedOops`

<https://wikis.oracle.com/display/HotSpotInternals/CompressedOops>

Turned on by default, since 32 bits are usually enough for all memory.

Turned off automatically is you set `-Xmx` greater than 32GiB, since that in that case you might need even more objects.

Use object addresses that are 32 bits long even in a 64 bit architecture.

## JAVA_TOOL_OPTIONS

Can be used to pass certain command line options to `java`, e.g.:

    JAVA_TOOL_OPTIONS='-Da=b' java Main

TODO which options exactly can be passed in that way? `-version` did not work.

