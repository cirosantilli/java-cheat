.class public StaticField
.super java/lang/Object

.field static f I

; Static initializer.
.method static public <clinit>()V
   .limit stack 1
   ldc 1
   putstatic StaticField/f I
   return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 2
    ldc 2
    putstatic StaticField/f I
    getstatic java/lang/System/out Ljava/io/PrintStream;
    getstatic StaticField/f I
    invokevirtual java/io/PrintStream/println(I)V
    return
.end method
