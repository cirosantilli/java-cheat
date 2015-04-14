.PHONY: all clean

all:
	javac Main.java
	javah -jni Main
	gcc -shared -fpic -o libMain.so -I$${JAVA_HOME}/include -I$${JAVA_HOME}/include/linux Main.c
	java -ea -Djava.library.path=. Main

clean:
	rm -f *.class *.so Main.h
