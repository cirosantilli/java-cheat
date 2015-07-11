# Bytecode

Learn Java bytecode.

## Bibliography

Official source: <https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html>

Practical huge instruction table: <http://en.wikipedia.org/wiki/Java_bytecode_instruction_listings>

Lots of common usage for the instructions: <http://cs.au.dk/~mis/dOvs/jvmspec/ref-Java.html>

## How to learn

Rotate between:

- read the specs: <https://docs.oracle.com/javase/specs/jvms/se7/html/index.html>. Chapters 2, 4, and 6 are the meat.
- `javac` compile small Java examples, then understand every byte of `hd Class.class`. Then `javap Class.class` in every language construct.
- make your own bytecode with an assembler like Jasmin

## Global structure of the class file

The class file contains:

- a header, which specifies the number of entries for all further parts
- a constant table, which contains everything else, including the instructions

The header makes top-level references into the constant table.

Entries in the constant table can then reference other entries in the constant table.

## Types

<https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-2.html#jvms-2.11.1>

`long` and `double` take up two stack entries.

### returnAddress

<https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-2.html#jvms-2.3.3>

### Field descriptors

<https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.3.2>:

- `B`: byte: unsigned byte
- `C`: char: UTF-16 encoded in BMP
- `D`: double
- `F`: float
- `I`: int
- `J`: long
- `LClassName;`: instance of `ClassName`
- `S`: short
- `Z`: boolean
- `[`: array

For the return type, there is also void:

- `V`: void

## Methods

### Method Signature

### Method Descriptors

    void method(int i, float f, String s, Integer i)

gives:

    (IFLjava/lang/String;java/lang/Integer)V

Breakdown:

    ( I F Ljava/lang/String; java/lang/Integer ) V
      ^ ^ ^                  ^                   ^
      1 2 3                  4                   5

1. `int`
1. `float`
1. `String`
1. `Integer`
1. `void` return type

### Method names

Any byte sequence that does not contain `. ; [ / < > :`, thus much less restricted than the Java rules.

### Bridge flag

<https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.6>

## Data regions

There are 3 main data regions you have to consider:

- constant pool
- stack
- locals

### Constant pool

Holds all constants to be used by the program.

This includes: method names and signatures as modified UTF8 strings <http://docs.oracle.com/javase/6/docs/api/java/io/DataInput.html#modified-utf-8>

<https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.4>

Constant pool entries do not have identical sizes: e.g. UTF-8 entries have variable length. Nonetheless, instructions refer to them by table entry number, e.g.:

    invokestatic 00 08

uses entry number 8 of the table.

There is no constant table index built-in to the `.class`.

So:

- when Java loads those files, it must build a index on the fly by parsing the entries
- you can modify constant table entries manually without changing anything else

### Stack and local variables

<https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-2.html#jvms-2.6.1>

Both exist per stack frame (method call).

Each method declares the maximum numbers of both it will need on the code attribute: <https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.3>

#### Stack

The stack is analogous to the CPU registers: they are where operators take input, and save output to.

The main difference between the stack and CPU registers is that the size of the stack is not limited.

Operations can only affect the top of the stack.

A prototypical operation is `iadd`, which adds two integers. Before `iadd`, the stack looks like:

    1 <-- top
    2
    ... <-- more entries

After `iadd` it becomes:

    3 <-- top
    ... <-- more entries

#### Local variables

Local variables are just a random access array accessed by index, analogous to RAM memory allocated per function call in x86 programming.

Arguments are passed as local variables: so you need at least as many local variables as you have arguments.

Extra ones can be used.

`this` is always the first argument for member methods.

## Instructions

TODO move all of this to a Jasmin cheat.

### Stack operations

#### dup and family

Duplicate the top of the stack.

#### pop

#### pop2

Discard one or two values from the top of the stack.

#### swap

Swap top two values of the stack.

### Primitives

#### aconst_null

Push `null` into stack.

#### iconst_0

#### iconst_1

#### iconst_5

Push constants to the stack.

Why only up to 5? Why do they exist at all, given `bipush`? To generate shorter code, and because there was instruction space left?

#### bipush

Push `byte` to stack.

#### sipush

Push `short` to stack.

### Array instructions

#### newarray

    new int[]

compiles to:

    iconst_2
    newarray int

#### iastore

    array[0] = 1

compiles to:

    iconst_0
    iconst_1
    iastore

#### iaload

    array[0]

(array access) compiles to:

    iconst_0
    iaload

#### arraylength

    array.length

compiles to:

    arraylength

### Methods instructions

#### invokedynamic

#### invokeinterface

#### invokespecial

#### invokestatic

#### invokevirtual

TODO call methods

#### return family

#### areturn

#### ireturn

#### freturn

#### dreturn

Return from method

### instanceof

TODO

### athrow

TODO

### branching

#### goto

#### goto_w

#### jsr

#### jsr_w

#### ret

<http://stackoverflow.com/questions/5871190/why-are-jsr-ret-deprecated-java-bytecode>

Not used in Oracle's Java compiler since Java 6.

### Synthetic class

<http://stackoverflow.com/questions/399546/synthetic-class-in-java>

### nop

Do nothing.

Does not get normally generated by `javac`.

- <http://stackoverflow.com/questions/10422086/what-is-the-nop-in-jvm-bytecode-used-for>
- <http://stackoverflow.com/questions/10422086/what-is-the-nop-in-jvm-bytecode-used-for>

### breakpoint

TODO reserved for debuggers.

### Concurrency

### monitorenter

### monitorexit

TODO check that these are the concurrency instructions.

<http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.monitorenter>

There are no thread creation instructions on Java bytecode. The key Java `Thread` methods like creation and shutdown are `native`.
