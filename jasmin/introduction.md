# Introduction

Java bytecode assembler, likely the most popular one.

The original Jasmin project seems to be dead: CVS on <http://jasmin.sourceforge.net/>. But the Sable project maintains an active fork: <https://github.com/Sable/jasmin>

On Ubuntu, you can install Jasmin with:

    sudo apt-get install jasmin-sable

Good introduction tutorial: <http://web.mit.edu/javadev/packages/jasmin/doc/>

## Alternatives

There is no official assembler from Oracle or assembly language. This is in contrast with .NET which came immediately with an assembler.

There is an official disassembler: `javap`, but Jasmin author says that its output format is not suitable for computer consumption, only human.

## Source code

    git clone https://github.com/Sable/jasmin
    cd jasmin
    cp ant.settings.template ant.settings
    ant
    java -cp classes:libs/java_cup.jar jasmin.Main examples/HelloWorld.j
    java examples.HelloWorld

The main dependency is the CUP <http://www2.cs.tum.edu/projects/cup/> LALR parser generator, which has been embedded in the source.

## directives

### limit

`locals` defaults to 1 if not present, `stack` TODO
