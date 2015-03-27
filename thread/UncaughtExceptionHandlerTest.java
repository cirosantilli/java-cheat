public class UncaughtExceptionHandlerTest {
    static volatile boolean b = false;
    public static void main(String[] args) throws Throwable {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // Harder for checked exceptions because `run` does not throw any exception: TODO
                // http://stackoverflow.com/questions/1369204/how-to-throw-a-checked-exception-from-a-java-thread
                throw new RuntimeException();
            }
        });
        // Once you set this, the stack trace won't
        // show anymore from the main thread.
        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread th, Throwable ex) {
                b = true;

                // This still happens in the thread that threw,
                // so the exception won't blow up outside.
                //throw new RuntimeException(ex);
            }
        });
        t.start();
        t.join();
        assert b;
    }
}
