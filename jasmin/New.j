; # new
;
; # invokespecial
;
; Must initialize with invokespecial before usage, or else `VerifyError`:
; https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.10.2.4

.class public New
.super java/lang/Object

.method public static main([Ljava/lang/String;)V
    .limit stack 4
    getstatic java/lang/System/out Ljava/io/PrintStream;
    new java/lang/Integer
    dup
    iconst_1
    invokespecial java/lang/Integer/<init>(I)V
    invokevirtual java/io/PrintStream/println(Ljava/lang/Object;)V
    return
.end method
