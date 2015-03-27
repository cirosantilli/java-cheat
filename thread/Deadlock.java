/**
* Sample code that very often leads ot a deadlock.
*
* When one bows, he cannot get up before the other bows back.
*
* The problem is when both bow at the same time: the cannot get up and bow
* back because the other hasn't bowed back yet! The result is that both threads to nothing.
*
* Furthermore, no one can bow and bow back at the same!
* This is all implied by the synchronized keyword:
* only one of the synchronized methods of an *OBJECT* can take place at a time.
*/

public class Deadlock {

    static class Friend {

        private final String name;

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public synchronized void bow(Friend bower) {
            System.out.format("%s: %s has bowed to me!%n",
                this.name, bower.getName());
            bower.bowBack(this);
        }


        /**
        * This fails!
        *
        * Reason:
        *
        *     void m() {
        *         //
        *     }
        *
        * is different than:
        *
        *     void m() {
        *         synchronized (this) {
        *             //
        *         }
        *     }
        *
        *  Because on the second, multiple methods on the same data *can* enter method `m()`,
        *  while on the first they cannot.
        *
        *  This makes a big difference here, because if the method enters bowSyncFail(),
        *  it blocks other synchronized methods (`bowBack`), causing the deadlock.
        */
        public synchronized void bowSyncFail(Friend bower) {
            //System.out.println("before other bowed to me");
            synchronized (BowLock.class) {
                System.out.format("%s: %s has bowed to me!%n",
                    this.name, bower.getName());
                bower.bowBack(this);
            }
        }

        public synchronized void bowBack(Friend bower) {
            System.out.format("%s: %s"
                + " has bowed back to me!%n",
                this.name, bower.getName());
        }
    }

    public static class BowLock {}

    public static void main(String[] args) {

        final Friend a = new Friend("a");
        final Friend b = new Friend("b");
        Thread ta;
        Thread tb;

        switch ( 0 ) {

            case 0:
                //bow

                ta = new Thread(new Runnable() {
                    public void run() { a.bow(b); }
                });
                tb = new Thread(new Runnable() {
                    public void run() { b.bow(a); }
                });
                ta.start();
                tb.start();

            break;

            case 1:

                //bowSyncFail

                ta = new Thread(new Runnable() {
                    public void run() { a.bowSyncFail(b); }
                });
                tb = new Thread(new Runnable() {
                    public void run() { b.bowSyncFail(a); }
                });
                ta.start();
                tb.start();

            break;

            case 2:

                //Here the only solution is for the first bow finish before the second thread enters it.

                //But if the first thread finishes bow, it also finishes its operation!

                //Therefore, the best solution in this case is to simply not paralellize.

            break;
        }
    }
}
