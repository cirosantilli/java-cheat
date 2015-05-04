.POSIX:
.PHONY: all clean

all:
	javac *.java
	jar -cfm Sizeof.jar META-INF/MANIFEST.MF Sizeof.class
	java -ea -javaagent:Sizeof.jar Main

clean:
	rm -f *.class *.jar
