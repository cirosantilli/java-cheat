; Main Java bytecode cheatsheet in Jasmin.
;
; We will use other tools for stuff that Jasmin cannot do.
;
; This will not discuss the Jasmin syntax itself.

.class public Main
.super java/lang/Object

.method public <init>()V
    aload_0
    invokenonvirtual java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 1024

    ; # new

    ; # invokespecial

        ; Must initialize before usage, or `VerifyError`:
        ; https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.10.2.4

        getstatic java/lang/System/out Ljava/io/PrintStream;
        new java/lang/Integer
        dup
        iconst_1
        invokespecial java/lang/Integer/<init>(I)V
        invokevirtual java/io/PrintStream/println(Ljava/lang/Object;)V

    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc "ALL ASSERTS PASSED"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    return
.end method
