.PHONY: all

all:
	# 1
	javac 1/*.java
	mv 1/*.class .
	javac Main.java
	java Main
	# 2
	# TODO rm all but Main.class
	rm Lib.class
	javac 2/*.java
	mv 2/*.class .
	# We don't recompile!
	#javac Main.java
	java Main

clean:
	rm -f *.class
