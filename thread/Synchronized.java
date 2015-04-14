/*
Examples of various synchronization methods.

Methods which do not require imports such as synchronoized, wait or volatile
are lower level and used to implement methods on the standard libraries
such as semaphores or locks.

# synchronized

    aka intrinscic locks.

    Present on the bytecode level through `ACC_SYNCHRONIZED`.
    TODO could it not be done with monitor instructions?

    There are two contexts for the synchronized keyword: methods and blocks.

    The keyword has the following effects on methods.

    1. Synchronized methods (possibly different methods!)
    of a single object cannot run at the same time on different threads

    2. TODO understand

    Synchronized blocks are more flexible versions os synchronized methods.
    <a href="http://stackoverflow.com/questions/574240/synchronized-block-vs-synchronized-method">Source</a>.

    - accept any object instead of just `this`
    - can cover arbitray regions instead of just entire methods.

    On a static method, the lock is on `Class.class` instead of `this`.
    Therefore static and non static synchronized methods *can* run at the same time.

# semaphore

    Semaphores are more flexible than synchronized because:

    - they can be used across methods
    - fairness can be controlled
    - they have a tryAquire method, which returns immediatelly if not possible to enter.
    - the maximum number of simultaneous threads in a region can be greater than 1

    TODO difference from lock, except that locks lock only once?

# lock


*/

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock; //this is abstract
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;

public class Synchronized {

    /**
    * The potential problem with this counter is *atomicity*, and not ordering.
    *
    * Increments can happen in any order, and the end result is still correct.
    *
    * The problem is when increments interleave, and the end result gets corrupted.
    */
    static class Counter {

        public int total = 0;
        public AtomicInteger atomicTotal = new AtomicInteger(0);
        private static final Semaphore mutex = new Semaphore(1);
        private static final Lock lock = new ReentrantLock();

        public void incrementTwoStatements() {
            int newValue = this.total + 1;
            //the problem is when the context change is exactly here!
            //if this happens, the old new value is simply restored,
            //even if other threads decreased it many times
            this.total = newValue;
        }

        /**
        * This can also fail. Even if `++` is a single symbol,
        * it still may expand to something that is not atomic such as:
        *
        * - read value of increment from RAM into cpu
        * - read value of total from RAM into cpu
        * - subtract
        * - write result to RAM
        */
        public void incrementOneStatement() {
            this.total++;
        }

        /**
        * Cannot fail, since this method can only be run once at a time.
        *
        * This is the second best method in this case after AtomicInteger,
        * because it is the least verbose and very lightweight.
        */
        public synchronized void incrementSynchronizedMethod() {
            this.total++;
        }

        /**
        * Cannot fail because of the synchronized block.
        */
        public synchronized void incrementSynchronizedBlock() {
            synchronized (this) {
                this.total++;
            }
        }

        /**
        * Cannot fail because of the semaphore
        */
        public synchronized void incrementSemaphore() {
            try {
                mutex.acquire();
                this.total++;
            } catch ( InterruptedException e ) {
                Thread.currentThread().interrupt();
            } finally {
                mutex.release();
            }
        }

        /**
        * Semaphores can detect if there are permits available or not with tryAcquire.
        */
        public synchronized void incrementSemaphoreTry() {
            boolean acquired = mutex.tryAcquire();
            if ( ! acquired ) {
                System.out.println("tryAcquire failed");
                try {
                    mutex.acquire();
                    this.total++;
                } catch ( InterruptedException e ) {
                    Thread.currentThread().interrupt();
                } finally {
                    mutex.release();
                }
            } else {
                this.total++;
                mutex.release();
            }
        }

        /**
        * Cannot fail because of the lock
        */
        public synchronized void incrementLock() {
            lock.lock();
            try {
                this.total++;
            } finally {
                lock.unlock();
            }
        }

        /**
        * Cannot fail because it uses atomic integer.
        *
        * This is the best option in this case because it incurs the least overhead.
        *
        * It is however the least general synchronization method.
        */
        public synchronized void incrementAtomicInteger() {
            this.atomicTotal.incrementAndGet();
        }

        /**
        * synchronized methods are reentrant:
        * a thread that owns the lock can enter the same lock again.
        */

        public static synchronized void reentrantOuter(){
            reentrantInner();
        }

        public static synchronized void reentrantInner(){
        }

        public static synchronized void reentrantOuterBlock(){
            synchronized (Counter.class) {
                reentrantInnerBlock();
            }
        }

        public static synchronized void reentrantInnerBlock(){
            synchronized (Counter.class) {}
        }

        /**
        * Unlike synchronized, semaphores are *not* reentrant.
        */
        public static synchronized void reentrantOuterSemaphore(){
            try {
                mutex.acquire();
                reentrantInnerSemaphore();
            } catch ( InterruptedException e ) {
                Thread.currentThread().interrupt();
            } finally {
                mutex.release();
            }
        }

        public static synchronized void reentrantInnerSemaphore(){
            System.out.println("before inner acquire");
            try {
                mutex.acquire();
                System.out.println("after inner acquire");
            } catch ( InterruptedException e ) {
                Thread.currentThread().interrupt();
            } finally {
                mutex.release();
            }
        }

        /**
        * As their names suggest, ReentrantLock can reenter!
        */
        public static synchronized void reentrantOuterLock(){
            lock.lock();
            try {
                reentrantInnerLock();
            } finally {
                lock.unlock();
            }
        }

        public static synchronized void reentrantInnerLock(){
            lock.lock();
            lock.unlock();
        }

    }

    public enum Method {
        TwoStatements,
        OneStatement,
        SynchronizedMethod,
        SynchronizedBlock,
        Semaphore,
        SemaphoreTry,
        Lock,
        AtomicInteger
    }

    /**
    * Runnable that increments a counter once and exits.
    */
    static class Incrementer implements Runnable {

        public Counter counter;
        private Method method;

        /**
        * @param counter    counter to increment from
        * @param method     which increment method to use
        */
        public Incrementer(Counter counter, Method method) {
            this.counter = counter;
            this.method = method;
        }

        public void run() {
            switch( method ) {
                case TwoStatements:
                    this.counter.incrementTwoStatements();
                break;
                case OneStatement:
                    this.counter.incrementOneStatement();
                break;
                case SynchronizedMethod:
                    this.counter.incrementSynchronizedMethod();
                break;
                case SynchronizedBlock:
                    this.counter.incrementSynchronizedBlock();
                break;
                case Semaphore:
                    this.counter.incrementSemaphore();
                break;
                case SemaphoreTry:
                    this.counter.incrementSemaphoreTry();
                break;
                case Lock:
                    this.counter.incrementLock();
                break;
                case AtomicInteger:
                    this.counter.incrementAtomicInteger();
                break;
            }
        }
    }

    /**
    * Increments a counter `total` times with different methods in multiple threads and checks if the
    * result is correct (shoul equal `total`).
    *
    * @param args dummy
    * @throws java.lang.InterruptedException dummy
    */
    public static void main(String[] args) throws InterruptedException {

        Counter counter;
        final int total = 10000;
        Thread[] threads = new Thread[total];

        for ( Method method : Method.values() )
        {
            counter = new Counter();

            for ( int i = 0; i < threads.length; i++ ) {
                threads[i] = new Thread(new Incrementer(counter, method));
                threads[i].start();
            }

            //make sure all threads ended
            for ( int i = 0; i < threads.length; i++ ) {
                threads[i].join();
            }

            switch (method) {

                //may be incorrect

                    case TwoStatements:
                    case OneStatement:
                        if ( counter.total >= total ) {
                            System.out.format( "unlikely event: method %s: total: %d%n", method, counter.total);
                        }
                    break;

                //always correct

                    case SynchronizedMethod:
                    case SynchronizedBlock:
                    case Semaphore:
                    case SemaphoreTry:
                    case Lock:
                        assert counter.total == total;
                    break;

                    case AtomicInteger:
                        assert counter.atomicTotal.get() == total;
                    break;
            }
        }

        Counter.reentrantOuter();
        Counter.reentrantOuterBlock();
        //Counter.reentrantOuterSemaphore();
        Counter.reentrantOuterLock();
    }
}
