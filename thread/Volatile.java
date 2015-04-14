/*
volatile keyword example.

`volatile` has a direct JVM bytecode representation through `ACC_VOLATILE`:
https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.5

# Outputs

Possible output with volatile:

    ChangeMaker: Increment sharedInt to 1
    ChangeListener: Detected sharedInt change to 1
    ChangeMaker: Increment sharedInt to 2
    ChangeListener: Detected sharedInt change to 2
    ChangeMaker: Increment sharedInt to 3
    ChangeListener: Detected sharedInt change to 3
    ChangeMaker: Increment sharedInt to 4
    ChangeListener: Detected sharedInt change to 4
    ChangeMaker: Increment sharedInt to 5
    ChangeListener: Detected sharedInt change to 5

Without volatile:

    ChangeMaker: Increment sharedInt to 1
    ChangeMaker: Increment sharedInt to 2
    ChangeMaker: Increment sharedInt to 3
    ChangeMaker: Increment sharedInt to 4
    ChangeMaker: Increment sharedInt to 5

## TODO

Convert into a test with an assertion of type:

- T1: write shared value
- T1: wake T2
- T1: sleep
- T2: read shared value. Assert here.
- T2: wake T1
- T2: return
- T1: return

## Effects of volatile

Volatile has the following effects on variables:

-   force writes from any thread
    to be made visible by all threads. Without it,
    writes may never become visible to other threads
    TODO even after `join`.

-   force double and long reads to be atomic.

    This means that if:

        x = 0b00

    and a thread does:

        x = 0b11

    then the value visible on another thread can never be:

        x == 0b01
        x == 0b10

    it must be either `0b00` or `0b11`.

    Other primitives and object reference reads / writes are always atomic.
    (even though object references may be 64 bits in x86_64).

    TODO. Example program where this happens. Not possible in x86_64?

    The non atomicity of `long` and `double` maps to bytecode,
    where `long` and `double` both take up two stack spaces.

Not being volatile, allows several optimizations when write
does not need to be made visible:

- can be left in a processor's register
- can be left in a processor's cache

`volatile` does `not` guarantee atomiticy of increment `i++`!
An increment is made up of 3 operations:

- memory read
- processor increment
- memory write

and not even the individual read and write are atomic!

`volatile` in Java looks a bit like C++, but is very different: C++11's memory model
does not guarantee volatile writes to be visible across threads!

ChangeListener may be left in an infinite loop as
it never sees the variable update.

## Volatile objects

Only the marked reference is volatile.

Non-volatile fields are not volatile.

TODO example

## Array of volatiles

http://stackoverflow.com/questions/2236184/how-to-declare-array-elements-volatile-in-java

Not possible. Use `AtomicIntegerArray` and family instead.
http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/atomic/AtomicIntegerArray.html

TODO example
*/
public class Volatile {

    private static volatile int sharedVolatileInt = 0;
    private static int sharedInt = 0;

    public static void main(String[] args) {
        new ChangeListener().start();
        new ChangeMaker().start();
    }

    static class ChangeListener extends Thread {

        private static final int MAX_TURNS = 1_000_000_000;

        @Override
        public void run() {
            int local_value = sharedInt;
            int nTurns = 0;
            while (local_value < 5){
                if(local_value != sharedInt) {
                    System.out.format("ChangeListener: Detected sharedInt change to %d%n", sharedInt);
                    local_value = sharedInt;
                }
                //nTurns++;
                //if (nTurns == MAX_TURNS) {
                    //System.out.format("ChangeListener: Reached maxTurns: %d%n", maxTurns);
                    //return;
                //}
            }
            System.out.format("Total turns / 10 ^ 6: %d%n", nTurns / (1000000));
        }
    }

    static class ChangeMaker extends Thread {
        @Override
        public void run() {

            int local_value = sharedInt;
            while (sharedInt < 5){
                System.out.format("ChangeMaker: Increment sharedInt to %d%n", local_value + 1);
                sharedInt = ++local_value;
                // TODO do better than this and force the other thread to wake up.
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }
}
