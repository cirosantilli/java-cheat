# JNI

TODO not working.

Java Native Interface.

Implement methods in C or C++ and call them from Java, allowing you to write code that:

- is faster
- can do system calls directly

Touches internals of how Java works. TODO how stable is this? Across minors (Java 7 to 8), or even less stable?

## JNA

Popular alternative, that does not require you to write any C code: <https://github.com/twall/jna>

Vs: <http://stackoverflow.com/questions/1556421/use-jni-instead-of-jna-to-call-native-code>

## Requirements

- Linux
- Java 7
- `gcc`
- `JAVA_HOME` environment variable set
