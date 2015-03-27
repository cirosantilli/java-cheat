JARPATH := /usr/share/java/junit4.jar

RUN ?= Main

.PHONY: all clean install-ubuntu

# TODO how to fix -source 1.7?
# http://stackoverflow.com/questions/7816423/warning-options-bootstrap-class-path-not-set-in-conjunction-with-source-1-5
# I get the warning.
all:
	javac -cp .:$(JARPATH) *.java

run: all
	java -cp .:$(JARPATH) -ea org.junit.runner.JUnitCore '$(RUN)'

clean:
	rm *.class

install-ubuntu:
	sudo apt-get install junit
