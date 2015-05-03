.POSIX:

RUN ?= Main

.PHONY: all clean run

all:
	javac -XDignore.symbol.file *.java

clean:
	rm -f *.class

run: all
	java -ea '$(RUN)'
