/**
* Main threads cheat.
*/

public class Main {

    static int i = 0;

    /**
    *The main way to create a thread is to make a class that implements Runnable.
    */
    static class RunnableTest implements Runnable {

        private int id;

        public RunnableTest(int id) {
            this.id = id;
        }

        /**
        *This is what will be run when the thread is started.
        */
        public void run() {
            System.out.println(id);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        /*
        #sleep

            Make current thread sleep for the given number of mili seconds.
        */
        {
            System.out.println("before sleep");
            //Thread.sleep(2000);
            System.out.println("after sleep");
        }

        {
            Thread[] threads = new Thread[100];

            /*
            #start

                Start running the runnable run method on a new thread.
            */
            for ( int i = 0; i < threads.length; i++ ) {
                threads[i] = new Thread(new RunnableTest(i));
                threads[i].start();
            }

            /*
            #join

                Wait for thread to finish running.
            */
            for ( int i = 0; i < threads.length; i++ ) {
                threads[i].join();
            }

            /*
            #yield

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
        #exception in a thread

            Only the thread gets killed.
        */
        {
            new Thread( new Runnable(){
                public void run() {
                    assert false;
                }
            }).start();
        }

        /*
        #interrupt

            Tells a thread to stop that it is doing.

            The only effect of this is to set the isInterrupted flag, which can be querried with
            `Thread.currentThread().isInterrupted()`.

            Threads that take long actins can check from time to time if they have been interrupted,
            and if so, cleanup and end operation.

        #InterruptedException

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
            System.out.format("isInterrupted = %b%n", Thread.currentThread().isInterrupted());
            Thread.currentThread().interrupt();
            assert Thread.currentThread().isInterrupted() == true;
        }

        /*
        #wait

            Sleep until another thread calls `notify` on us.

            TODO get working
        */
        {
            //i = 0;
            //Thread thread = new Thread( new Runnable(){
            //    public void run() {
            //        try {
            //            assert i == 0;
            //            this.wait();
            //            assert i == 1;
            //        } catch ( InterruptedException e ) {
            //            Thread.currentThread().interrupt();
            //        }
            //    }
            //});
            //thread.start();
            //i = 1;
            //thread.notify();
            //thread.join();
        }


    }
}
