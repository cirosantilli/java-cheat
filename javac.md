# javac

## Basic usage

Compile `HelloWorld.java` source file into `HelloWorld.class` bytecode:

    javac HelloWorld.java

Then run it with:

    java HelloWorld

## help

    java -help

## source

## target

`-source`: specify the version of the source code input.

Can be the current version, or any major prior version.

Example:

    javac -source 1.6 A.java

`-target`: specify the version of the targeted JVM.

## d

Class files output directory.

Example:

    javac -d bin A.java

generates `bin/A.class`.

## g

Add full debug information to the class files.

By default, some debug information is already added, namely the line numbers and corresponding source code we see on exception traces.

But other things are not included, in particular local variable information that allows you to do `locals` on `jdb`.

## HotSpot extensions

All of them are prefixed by `-X`.

### int

### comp

Only compile, or only interpret bytecode without compiling. No JIT.
