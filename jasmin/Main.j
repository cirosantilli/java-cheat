.class public Main
.super java/lang/Object

.method public static main([Ljava/lang/String;)V

    ; getstatic

        .limit stack 2
        getstatic java/lang/System/out Ljava/io/PrintStream;
        getstatic java/lang/Integer/SIZE I
        invokevirtual java/io/PrintStream/println(I)V

    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc "ALL ASSERTS PASSED"
    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
    return
.end method
