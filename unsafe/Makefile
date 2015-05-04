.POSIX:

RUN ?= Main

.PHONY: all clean run

all:
	javac -XDignore.symbol.file *.java
	jar -cfm Sizeof.jar META-INF/MANIFEST.MF Sizeof.class

clean:
	rm -f *.class *.jar *.log

run: all
	java -ea -javaagent:Sizeof.jar '$(RUN)'
