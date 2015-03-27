# javap

WIP

Disassembles `.class` compiled files so you can see their internal structure.

TODO mnemonic for the `p` in the name? Because it Prints to stdout?

Print a human readable version of the JVM instructions:

    javap -c -constants -private -verbose HelloWorld

Sample output for a hello world program:

    Classfile /home/ciro/bak/git/java-cheat/target/HelloWorld.class
    Last modified Mar 18, 2015; size 426 bytes
    MD5 checksum 7a3ee81e13319873b7ac05812c3e5571
    Compiled from "HelloWorld.java"
    public class HelloWorld
    minor version: 0
    major version: 52
    flags: ACC_PUBLIC, ACC_SUPER
    Constant pool:
    #1 = Methodref          #6.#15         // java/lang/Object."<init>":()V
    #2 = Fieldref           #16.#17        // java/lang/System.out:Ljava/io/PrintStream;
    #3 = String             #18            // Hello world!
    #4 = Methodref          #19.#20        // java/io/PrintStream.println:(Ljava/lang/String;)V
    #5 = Class              #21            // HelloWorld
    #6 = Class              #22            // java/lang/Object
    #7 = Utf8               <init>
    #8 = Utf8               ()V
    #9 = Utf8               Code
    #10 = Utf8               LineNumberTable
    #11 = Utf8               main
    #12 = Utf8               ([Ljava/lang/String;)V
    #13 = Utf8               SourceFile
    #14 = Utf8               HelloWorld.java
    #15 = NameAndType        #7:#8          // "<init>":()V
    #16 = Class              #23            // java/lang/System
    #17 = NameAndType        #24:#25        // out:Ljava/io/PrintStream;
    #18 = Utf8               Hello world!
    #19 = Class              #26            // java/io/PrintStream
    #20 = NameAndType        #27:#28        // println:(Ljava/lang/String;)V
    #21 = Utf8               HelloWorld
    #22 = Utf8               java/lang/Object
    #23 = Utf8               java/lang/System
    #24 = Utf8               out
    #25 = Utf8               Ljava/io/PrintStream;
    #26 = Utf8               java/io/PrintStream
    #27 = Utf8               println
    #28 = Utf8               (Ljava/lang/String;)V
    {
    public HelloWorld();
        descriptor: ()V
        flags: ACC_PUBLIC
        Code:
        stack=1, locals=1, args_size=1
            0: aload_0
            1: invokespecial #1                  // Method java/lang/Object."<init>":()V
            4: return
        LineNumberTable:
            line 1: 0

    public static void main(java.lang.String[]);
        descriptor: ([Ljava/lang/String;)V
        flags: ACC_PUBLIC, ACC_STATIC
        Code:
        stack=2, locals=1, args_size=1
            0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
            3: ldc           #3                  // String Hello world!
            5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
            8: return
        LineNumberTable:
            line 3: 0
            line 4: 8
    }
    SourceFile: "HelloWorld.java"

TODO understand.
