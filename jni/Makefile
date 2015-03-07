.PHONY: all clean

all:
	javac Main.java
	javah -jni Main
	gcc -shared -fpic -o libfoo.so -I$${JAVA_HOME}/include -I$${JAVA_HOME}/include/linux foo.c
	java -Djava.library.path=. Main

clean:
	rm -f *.class *.so Main.h

install-deps-ubuntu:
	sudo apt-get install gcc
