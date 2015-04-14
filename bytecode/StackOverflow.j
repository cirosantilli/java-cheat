; What happens when we use more stack than we declare.
;
;     Stack size too large

.class public StackOverflow
.super java/lang/Object

.method public static main([Ljava/lang/String;)V
   .limit stack 1
   iconst_0
   iconst_0
   return
.end method
