/**
 * Main threads cheat.
 */
public class Main {

    static int i = 0;

    /**
     * # Runnable
     *
     * The main way to create a thread is to make a class that implements Runnable.
     */
    static class RunnableTest implements Runnable {

        private int id;

        public RunnableTest(int id) {
            this.id = id;
        }

        /**
         * This is what will be run when the thread is started.
         */
        public void run() {
            System.out.print(id + " ");

            // Exits all threads of the program. TODO example program.
            //System.exit(0);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        /*
        # Daemon

        # getDaemon

        # setDaemon

            There are 2 types of thread: daemon and user.

            When there are no user threads left,
            the JVM kills all daemon threads and ends the program.
        */
        {
            // By default, the main thread is user.
            assert !Thread.currentThread().isDaemon();

            // You can only setDaemon on a thread before you start it
            boolean throwed = false;
            try {
                Thread.currentThread().setDaemon(true);
            } catch(IllegalThreadStateException e) {
                throwed = true;
            }
            assert throwed;
        }

        /*
        # Priority

        # getPriority

            Each thread has an associated priority between 1 and 10.

            TODO what is the exact effect?
        */
        {
            /*
            # NORM_PRIORITY

                Default priority
            */
            {
                assert Thread.currentThread().getPriority() == Thread.NORM_PRIORITY;
                // Unlike in Linux, you can increase your own priority by default.
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                // IllegalArgumentException
                //Thread.currentThread().setPriority(Thread.MAX_PRIORITY + 1);
            }
        }

        /*
        # Name

        # getName

        # setName

            Each thread has a name.

            Giving meaningful names to your threads can greatly help during debugging,
            as the name shows on stack traces and on the Eclipse debugger.

        # Get thread by name

            http://stackoverflow.com/questions/15370120/get-thread-by-name

            No direct way to do it.

            http://stackoverflow.com/questions/15370120/get-thread-by-name
        */
        {
            /*
            The initial thread is called `main`. TODO specified?

            Further threads are called TODO
            */
            assert Thread.currentThread().getName().equals("main");

            // By default, new threads are user.
            // TODO example
        }

        /*
        # Id

        # getId

            Each thread has an ID.

            It is generated at creation time, and cannot be modified.
        */
        {
            System.out.println("getId() = " + Thread.currentThread().getId());
        }

        /*
        # state

        # getState

            Threads can be in the following states:

            http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.State.html

            State transition diagram:

            http://www.uml-diagrams.org/examples/java-6-thread-state-machine-diagram-example.html
        */
        {
            // TODO examples
        }

        /*
        # start

            Start running a given Runnable as a new thread.

        # Anonymous runnable

            For short examples, it is common to use anonymous inner class runners,
            but you cannot pass non-final parameters to them:

            - nested classes cannot use non-final variables from the outside
            - anonymous classes cannot have constructors
        */
        {
        }

        /*
        # ThreadGroup

        # getThreadGroup

            TODO understand

            Defines a tree of threads:
            http://docs.oracle.com/javase/7/docs/api/java/lang/ThreadGroup.html
        */

        /*
        # Get all threads

        # List all threads

            http://stackoverflow.com/questions/1323408/get-a-list-of-all-threads-currently-running-in-java
        */

        {
            Thread[] threads = new Thread[100];

            /*
            # start

                Start running the runnable run method on a new thread.
            */
            for ( int i = 0; i < threads.length; i++ ) {
                threads[i] = new Thread(new RunnableTest(i));
                threads[i].start();
            }

            /*
            # join

                Wait for thread to finish running.
            */
            for ( int i = 0; i < threads.length; i++ ) {
                threads[i].join();
            }
            System.out.println();

            /*
            # yield

                Suggest OS that he take another thread to run.

                OS may return to current thread at any time.

                Use this when you have to wait for a contidion which you don't know
                how much time will take to happen.
            */
            {
                Thread.yield();
            }
        }

        /*
        # interrupt

            Tells a thread to stop that it is doing.

            The only effect of this is to set the isInterrupted flag, which can be querried with
            `Thread.currentThread().isInterrupted()`.

            Threads that take long actins can check from time to time if they have been interrupted,
            and if so, cleanup and end operation.

        # InterruptedException

            Thrown when a thread is sleeping or waiting and another thread calls interrupt on it.

            Rationale: a thread who is sleeping or waiting may take too much time to check isInterrupted,
            so it gets notified with an exception.

            When this is raised on a thread, isInterrupted flag is cleared!
            For this reason, you should not simply ignore an InterruptedException.
            If you don't know what to do with it, do `Thread.currentThread().interrupt();`,
            which plays nicelly and does what the other thread kindly asked you to do.
            <http://michaelscharf.blogspot.fr/2006/09/dont-swallow-interruptedexception-call.html>
        */
        {
            System.out.format(
                    "isInterrupted = %b%n",
                    Thread.currentThread().isInterrupted());
            Thread.currentThread().interrupt();
            assert Thread.currentThread().isInterrupted() == true;
        }

        /*
        # wait

        # nofity

        # nofityAll

            Sleep until another thread calls `notify` on us.

            TODO get working
        */
        {
            /*
            i = 0;
            Thread thread = new Thread( new Runnable(){
                public void run() {
                    try {
                        assert i == 0;
                        this.wait();
                        assert i == 1;
                    } catch ( InterruptedException e ) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            thread.start();
            i = 1;
            thread.notify();
            thread.join();
            */
        }


    }
}
