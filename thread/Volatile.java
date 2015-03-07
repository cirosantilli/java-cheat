/*
* Volatile example.
*
* Similar to ANSI C volatile, except that it is impossible to see what it does in pure ANSI <= C99
* becaues there is no thread support there.
*
* Source: <http://java.dzone.com/articles/java-volatile-keyword-0>
*
* Outputs:
*
* With volitile:
*
*     Incrementing MY_INT to 1
*     Got Change for MY_INT : 1
*     Incrementing MY_INT to 2
*     Got Change for MY_INT : 2
*     Incrementing MY_INT to 3
*     Got Change for MY_INT : 3
*     Incrementing MY_INT to 4
*     Got Change for MY_INT : 4
*     Incrementing MY_INT to 5
*     Got Change for MY_INT : 5&nbsp;
*
* Without volitile:
*
*     Incrementing MY_INT to 1
*     Incrementing MY_INT to 2
*     Incrementing MY_INT to 3
*     Incrementing MY_INT to 4
*     Incrementing MY_INT to 5
*
* And ChangeListener may be in an infinite loop, because of the way the JVM works,
* if thinks that a thread that is running does not need to have values updated.
*
* In cases less extreme than this one, what may happens without volatile is that the listener has to run
* many useuless turns just waiting for the value to be written to memory, even if it was
* already updated by the ChangeMaker.
*/

public class Volatile {

    //private static volatile int MY_INT = 0;
    private static int MY_INT = 0;

    public static void main(String[] args) {
        new ChangeListener().start();
        new ChangeMaker().start();
    }

    static class ChangeListener extends Thread {

        private static int maxTurns = 1000000000;

        @Override
        public void run() {
            int local_value = MY_INT;
            int nTurns = 0;
            while ( local_value < 5){
                if( local_value != MY_INT) {
                    System.out.format("Got Change for MY_INT : %d%n", MY_INT);
                    local_value = MY_INT;
                }
                //nTurns++;
                //if ( nTurns == maxTurns ) {
                //    System.out.format("Reached maxTurns: %d%n", maxTurns);
                //    return;
                //}
            }
            System.out.format("Total turns / 10 ^ 6: %d%n", nTurns / ( 1000000 ));
        }
    }

    static class ChangeMaker extends Thread{
        @Override
        public void run() {

            int local_value = MY_INT;
            while (MY_INT < 5){
                System.out.format("Incrementing MY_INT to %d%n", local_value + 1);
                MY_INT = ++local_value;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }
}
