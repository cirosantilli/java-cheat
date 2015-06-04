# JVM

# Java Virtual machine

# Bytecode

# Compiled vs interpreted

Java runs on a virtual machine.

First, the text `.java` files are transformed into a machine readable format called Java Bytecode.

This format is standardized and portable across systems. It is not native assembly code.

The utility that does this step is called `javac`. Java Bytecode is specified [here](http://docs.oracle.com/javase/specs/).

Having a standardized bytecode has the following advantages:

-   any language that implements bytecode compilation can run on any platform that has a JVM.

    Besides Java, the reference language, there are other important languages that target the JVM:

    -   Clojure, a functional Lisp dialect. Started by a single guy on personal funds.

    -   Groovy, programming and scripting language. Dynamically typed, and similar to Python and Ruby. Sponsored by EMC through acquisition of creators. Grails web framework.

    -   Scala, an object-oriented and functional programming language. Multi paradigm. By EPFL.

    -   JRuby. Started by two guys in 2001, one was later hired by Oracle, the other by ThoughtWorks

    -   Jython. Developed by many people since 1997. Sun hired two of them.

-   you can distribute `.class` files to end users on any platform. This saves them from compiling, and allows you to distribute obfuscated code.

-   it is faster for the JVM to understand than source code since it is more machine readable.

Java Bytecode is run by the Java Virtual Machine (JVM). Each system has a different implementation of JVM which takes care of system specifics. Moder JVMs can do two things:

-   interpret bytecode. No overhead, but runs slower.

-   compile bytecode. This is known as just in time (JIT) compilation. Has a compilation overhead, but runs faster.

Modern JVMs start compiling and interpreting at the same time. When JIT is done they switch from interpretation to to the compiled code.

The `.NET` platform is somewhat equivalent to the Java platform as it also supports bytecode and several languages. `.NET` bytecode is known as Common Language Runtime (CLR).

A good way to learn bytecode is to generate it from assemblers: programs that take a human readable language that maps 1-to-1 to bytecode instructions. The most popular assembler seem to be [Jasmin](jasmin/): we will make the main bytecode cheat there.

## Implementations

There have been hardware implementations of the JVM:

- <https://en.wikipedia.org/wiki/Java_processor>
- <http://stackoverflow.com/questions/1383947/can-we-implement-a-java-interpreter-in-hardware-that-executes-java-bytecodes-nat>

but they were not very successful.

## Virtual machine vs interpreter

<http://stackoverflow.com/questions/441824/java-virtual-machine-vs-python-interpreter-parlance>

## Compile bytecode

<http://programmers.stackexchange.com/questions/132993/is-it-possible-to-compile-java-into-machine-code-not-bytecode>

No simple solution seems to exist.

## Class file format

<http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html>

-   4 byte magic signature: `CA FE BA BE`.

    History behind it:
    <http://stackoverflow.com/questions/2808646/why-is-the-first-four-bytes-of-java-class-file-format-is-cafebabe>

## Shebang for Java

## Run Java classes without explicitly typing java on the CLI

<http://stackoverflow.com/questions/1667830/running-a-jar-file-without-directly-calling-java>

Since Java is not directly interpreted from source, there no simple, standard and good way.

`binfmt_misc` is the best method on Linux to run a `.class`.

## Class data sharing

## CDS

TODO

Way to share data between VMs.

<http://www-01.ibm.com/support/knowledgecenter/SSYKE2_7.0.0/com.ibm.java.aix.70.doc/diag/understanding/shared_classes.html>

<http://en.wikipedia.org/wiki/Java_performance#Class_data_sharing>

## Call Site

<https://blogs.oracle.com/jrose/entry/anatomy_of_a_call_site>

## JPDA

<http://docs.oracle.com/javase/7/docs/technotes/guides/jpda/>

Interface for debuggers to interact with the VM. All IDEs and JDB use it to implement debugging.

C# has stdlib methods that put you into debug mode like `System.Diagnostics.Debugger.Launch()`, but Java does not seem to have an analogous method: <http://stackoverflow.com/questions/2840941/system-diagnostics-debugger-break-like-using-java>

## How to crash the JVM

- <http://stackoverflow.com/questions/6470651/creating-a-memory-leak-with-java>
- <http://stackoverflow.com/questions/65200/how-do-you-crash-a-jvm>

Java does a lot of safety checks for us, and the JVM does not usually get killed by the OS like a C program can easily to, typically coredump by reading memory form some other program.

The best ways to crash the JVM are then:

- reflection if the security manager allows you
- JNI, which is a method to add C extensions to Java
- `Unsafe`, which is an unstable API
- JVM bugs...

## Internals

Implementation details which are not specified in the language or JVM specification.

### Garbage collector

### Memory

-   Heap:

    - eden
    - survivor
    - tenured

-   Non-heap:

    - permanent generation
    - code cache

TODO standardized or not?

<http://stackoverflow.com/questions/1262328/how-is-the-java-memory-pool-divided>

In Hotspot It is possible to set which garbage collector should be used:

- `-XX:+UseG1GC`
- `-XX:+UseParallelGC`
- `-XX:+UseConcMarkSweepGC`
- `-XX:+UseSerialGC`

#### NUMA

Only possible automatically for the young generation through `-XX+UseNuma`.

Otherwise, you have to use `Unsafe` (TODO direct `ByteBuffer`?)

#### G1

Garbage collector by oracle.

Used on NUMA.

### JIT

<http://insightfullogic.com/blog/2014/may/12/fast-and-megamorphic-what-influences-method-invoca/>

Not a part of the JVM standard: only an implementation detail optimization.

Observe the JIT output:
<https://techblug.wordpress.com/2013/08/19/java-jit-compiler-inlining/>

#### Hotspot JIT

##### Monomorphic

##### Bimorphic

##### Megamorphic

<http://insightfullogic.com/blog/2014/may/12/fast-and-megamorphic-what-influences-method-invoca/>

Hotspot implements different polymorphism cases differently, depending on how many implementations exist.
