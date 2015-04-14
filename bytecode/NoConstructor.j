; TODO is this possible?
; http://stackoverflow.com/questions/29478139/is-it-valid-to-have-a-jvm-bytecode-class-without-any-constructor

.class public NoConstructor
.super java/lang/Object

.method public static main([Ljava/lang/String;)V
    .limit stack 2
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc "ALL ASSERTS PASSED"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    return
.end method
