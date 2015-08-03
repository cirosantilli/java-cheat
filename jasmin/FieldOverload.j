.class public FieldOverload
.super java/lang/Object

.field static f I
.field static f F

.method public static main([Ljava/lang/String;)V
    .limit stack 2

    ldc 1
    putstatic FieldOverload/f I
    ldc 1.5
    putstatic FieldOverload/f F

    getstatic java/lang/System/out Ljava/io/PrintStream;
    getstatic FieldOverload/f I
    invokevirtual java/io/PrintStream/println(I)V

    getstatic java/lang/System/out Ljava/io/PrintStream;
    getstatic FieldOverload/f F
    invokevirtual java/io/PrintStream/println(F)V

    return
.end method
