import java.lang.instrument.Instrumentation;

final public class Sizeof {
    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long sizeof(Object o) {
        return instrumentation.getObjectSize(o);
    }
}
