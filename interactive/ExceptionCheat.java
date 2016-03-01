/*
What happens when an exception blows up
and gets forwarded across a method chain.

Too long to assert.
*/
public class ExceptionCheat {
    static void f5() { throw null; }
    static void f4() {
        try {
            f5();
        } finally {
            /*
            If you throw from either finally or catch, it stops propagating the current exception 
            and starts a new one.

            From the catch, you can keep the stack trace via the exception argument,
            but from finally, it's lost for good.
            */
            throw null;
        }
    }
    static void f3() { f4(); }
    static void f2() throws Exception {
        try {
            f3();
        } catch (Throwable e) {
            // You must pass `e` here to pass the stack trace forward.
            throw new Exception("Custom Message", e);
        }
    }
    static void f1() throws Exception { f2(); }

    public static void main(String[] args) throws Exception {
        f1();
    }
}
