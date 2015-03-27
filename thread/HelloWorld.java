/*
Minimal threads example.

Starts 10 threads that print integers between 0 and 9.

Since they are printed from threads, the output order is not predictable.
*/

public class HelloWorld {

    static class RunnableTest implements Runnable {

        private int id;

        public RunnableTest(int id) {
            this.id = id;
        }

        public void run() {
            System.out.println(id);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for ( int i = 0; i < 100; i++ ) {
            new Thread(new RunnableTest(i)).start();
        }
    }
}
