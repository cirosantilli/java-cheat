# OpenJDK

Open source, reference implementation. Resulted from a partial open sourcing of Oracle's Java implementation.

<http://openjdk.java.net/>

Sample output of `java -version` on Ubuntu 14.04:

    java version "1.7.0_65"
    OpenJDK Runtime Environment (IcedTea 2.5.3) (7u71-2.5.3-0ubuntu0.14.04.1)
    OpenJDK 64-Bit Server VM (build 24.65-b04, mixed mode)

Source browser and mercurial URLs: <http://hg.openjdk.java.net/>. Mercurial.

## Versioning

TODO how is it versioned?

It seems to use:

    jdk8u60-b12

to mean:

- `8`: major
- `60`: minor, `u` for Update
- `12`: patch, `b` for Build

TODO: `jdk8u60-b26` vs `jdk8-b27`? AKA http://hg.openjdk.java.net/jdk8u vs http://hg.openjdk.java.net/jdk8

## OpenJDK 8 source code

### Clone and build

Get sources:

    hg clone http://hg.openjdk.java.net/jdk8/jdk8
    cd jdk8
    sh get_source.sh

The `get_source.sh` is needed since the source is split across many HG repositories.

The entire JDK 8 takes up 900M.

Ubuntu 14.04 build dependencies (my printer stopped working afterwards...):

    sudo aptitude install libcups2-dev libfreetype6-dev libasound2-dev ccache

Build everything with:

    bash configure
    make

Took 25 minutes on a mid range computer from 2012.

`make help` teaches you more, e.g. how to build only certain parts of the JDK:

    make hotspot
    make jdk

Finally:

    cd build/linux-x86_64-normal-server-release/jdk/bin
    ./java -version

Sample output:

    openjdk version "1.8.0-internal"
    OpenJDK Runtime Environment (build 1.8.0-internal-ciro_2015_03_10_22_39-b00)
    OpenJDK 64-Bit Server VM (build 25.0-b70, mixed mode)

### Source tree

Some interesting files and directories:

-   `jdk/src/share/classes/java`: JCL

-   `hotspot`: the VM, including many native methods. There are also some native methods under `jdk/src`.

    -   `hotspot/src/share/vm`: platform agnostic VM code, mainly C++.

    -   `hotspot/src/share/os`: OS specific code, contains `bsd`, `linux`, `posix` (used for all others but Windows), `solaris`, `windows`

        Contains the definition of several native methods.

    -   `hotspot/src/share/cpu`: OS specific code

        -   `hotspot/src/share/cpu/zero`: CPU agnostic interpreter-only implementation when speed is not important.

            Started by IcedTea.

            <http://openjdk.java.net/projects/zero/>

-   `langtools/src/share/classes/com/sun/tools/javadoc/Main.java`: Javadoc

-   `langtools/src/share/classes/com/sun/tools/javac/Main.java`: Javac

## Contribution process

## Issue tracker

The Java process is very closed, probably because of Oracle?

I can't even manage to post an issue, so I wonder if it is even possible to have patches merged.

Create a new issue or feature request: <http://bugreport.java.com/submit_intro.do> Bugs only appear after review. Bulls***.

TODO how to browse the bugs? More bull****?

TODO vs <https://bugs.openjdk.java.net>? On that site you need a login, and to get it you need Author status...
