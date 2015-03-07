# JDB

WIP

Java debugger, part of OpenJDK.

Start:

    javac -g Main.java
    jdb Main

Using `-g` on `javac` will give you extra debugging capabilities, but the default command will also add some debugging information.

## Question mark

## help

List available commands:

    ?

or:

    help

## run

Run `Main` that was loaded at the initial invocation:

    run

If an exception is thrown, it will stop at that point. Otherwise, it will go right to the end of the program.

This does not happen automatically because you will likely want to setup some breakpoints before running the program.

## list

Print source code around current line. Sample output:

    51       if (b[i] == SEP) {
    52
    53           // Print new segment.
    54           if (i + 1 < BUFLEN) {
    55 =>            System.out.write(b, i + i, lastNewline - i);
    56           }
    57
    58           // Print old segments.
    59           if (!found) {
    60               // TODO optimization: assume that the old ends in newlin

## locals

Print the value of local variables. Sample output:

    Method arguments:
    readWidth = 8
    left = 1
    right = 7
    oldLeft = 0
    oldRight = 0
    Local variables:
    i = 5
    lastNewline = 7

## where

Show where in the stack trace we currently are. Sample output:

    [1] java.io.PrintStream.write (PrintStream.java:483)
    [2] Main.search (Main.java:55)
    [3] Main.main (Main.java:114)

indicates we are at `PrintsStream#write`, which was called from `Main.search`.
