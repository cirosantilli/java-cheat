/**
What happens when an exception throws from a thread.

Outcome:

-   the stack trace gets printed to stdout as something like:

    Exception in thread "Thread-0" java.lang.RuntimeException
            at ThreadException$1.run(ThreadException.java:11)
            at java.lang.Thread.run(Thread.java:745)

-   it has no effect to the main thread

    To observe the exception from another thread: http://stackoverflow.com/questions/6546193/how-to-catch-an-exception-from-a-thread

    - `UncaughtExceptionHandler`:
    - `ExecutorService`
*/
public class ExceptionTest {
    public static void main(String[] args) throws Throwable {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // Harder for checked exceptions because `run` does not throw any exception: TODO
                // http://stackoverflow.com/questions/1369204/how-to-throw-a-checked-exception-from-a-java-thread
                throw new RuntimeException();
            }
        });
        t.start();
        t.join();
    }
}
