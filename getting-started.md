# Getting started

Requirements for most directories:

- Java 7
- POSIX Make
- Maven 3

Each subdirectory may have its own build requirements.

Usage:

- if the directory contains a `pom.xml`, `mvn compile exec:java` to build and run. Used when all the dependencies can be installed with Maven.
- if the directory contains a `Makefile`, `make run` to build and run the main file, `make RUN=OtherClass` to run a custom file. Used when there are no dependencies that can be installed with Maven.
