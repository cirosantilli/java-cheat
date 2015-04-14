; VerifyError Expecting to find unitialized object on stack
; https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.10.2.4

.class public NewInvokespecialTwice
.super java/lang/Object

.method public static main([Ljava/lang/String;)V
    .limit stack 5
    getstatic java/lang/System/out Ljava/io/PrintStream;
    new java/lang/Integer
    dup
    dup
    iconst_1
    invokespecial java/lang/Integer/<init>(I)V
    iconst_1
    invokespecial java/lang/Integer/<init>(I)V
    return
.end method
