# Jasmin

Java bytecode assembler.

This is the main cheat on Java bytecode, besides just Jasmin syntax.

Tested with versions: 2.4.3 (Ubuntu 14.04).

<http://jasmin.sourceforge.net/>

There is not official assembler from Oracle or assembly language. This is in contrast with .NET which came immediately with an assembler.

There is an official disassembler: `javap`, but Jasmin author says that it is not suitable for computer consumption, only human.  

## Java bytecode

Inner classes are named as: `ClassName$InnerClassName`, with a dollar sign separating them from the parent.

### Synthetic

<http://stackoverflow.com/questions/399546/synthetic-class-in-java>
