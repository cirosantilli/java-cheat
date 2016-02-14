# JNI

Java Native Interface.

## Requirements

- Linux
- Java 7
- `gcc`
- `JAVA_HOME` environment variable set

## Introduction

Implement methods in C or C++ and call them from Java, allowing you to write code that:

- is faster
- can do system calls directly

Touches internals of how Java works. TODO how stable is this? Across minors (Java 7 to 8), or even less stable?

## UnsatisfiedLinkError

<http://docs.oracle.com/javase/7/docs/api/java/lang/UnsatisfiedLinkError.html>

What happens if the JVM cannot find the definition of a `native` method.

## Underscore in package name

Should be translated to `_1` on C method: <http://stackoverflow.com/a/35015592/895245>

## Alternatives

### JNA

Popular alternative, that does not require you to write any C code: <https://github.com/twall/jna>

Vs: <http://stackoverflow.com/questions/1556421/use-jni-instead-of-jna-to-call-native-code>

### SWIG

<http://www.swig.org/>

Also supports many other languages.
