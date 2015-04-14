# Threads

Example here have to be split one per file, because they are either non-deterministic or take a perceptible amount of time to run.

-   [HelloWorld.java](HelloWorld.java)
-   [Main.java](Main.java)
-   [ExceptionTest.java](ExceptionTest.java)
    - [UncauthExceptionHandlerTest.java](UncauthExceptionHandlerTest.java)
-   [Deadlock.java](Deadlock.java)
-   [Volatile.java](Volatile.java)

TODO:

-   `ConcurrentMap` and other concurrent collections

    - `replace`: replaces only if the value exists / equals an older value. Does the check and insert atomically.

-   `AtomicInteger` and family.

    Vs `volatile boolean`: <http://stackoverflow.com/questions/3786825/volatile-boolean-vs-atomicboolean> Same as `volatile int`, but with less compound operations.

-   `CountDownLatch`

-   `ThreadLocal`

-   `ThreadLocalRandom`

- `Executor`

    -   `ExecutorService`

        - `ForkJoinPool`
        - <http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ThreadPoolExecutor.html>

-   `ForkJoinTask`

    -   `RecursiveAction`

Sources:

- <http://tutorials.jenkov.com/java-util-concurrent/index.html>

## Synchronization primitives

The synchronization primitives provided by the Java *language* are:

- `volatile` field modifier keyword
- `synchronized` method modifier or (TODO block modifier?) keyword
- `Object.wait()`, `Object.notify()` and `Object.notifyAll()` magic class methods

Besides those, there are many more convenient higher level interfaces provided by the JCL under [java.util.concurrent](http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/package-summary.html):

- synchronized containers like `LinkedBlockingQueue`
- atomic types under [java.util.concurrent.atomic](http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/atomic/package-summary.html): `AtomicInteger`, etc.
- locks under [java.util.concurrent.atomic](http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/package-summary.html)
