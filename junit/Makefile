JARPATH := /usr/share/java/junit4.jar

RUN ?= Main

.PHONY: all clean install-ubuntu

all:
	javac -cp .:$(JARPATH) *.java

run: all
	java -cp .:$(JARPATH) -ea org.junit.runner.JUnitCore '$(RUN)'

clean:
	rm Main.class

install-ubuntu:
	sudo apt-get install junit
