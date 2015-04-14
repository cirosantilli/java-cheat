; What happens when we use more locals than we declare:
;
;     Illigal local variable number

.class public LocalsOverflow
.super java/lang/Object

.method public static main([Ljava/lang/String;)V
   .limit locals 1
   aload_1
   return
.end method
