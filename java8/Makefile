OUT_EXT ?= .class
MAIN ?= Main

.PHONY: all clean

all:
	javac -g -source 1.8 *.java

clean:
	rm -f *$(OUT_EXT)

run:
	java '$(MAIN)$(OUT_EXT)'
