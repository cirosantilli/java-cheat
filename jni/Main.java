import java.util.Arrays;

public class Main {
    /** Square. */
    public native int intMethod(int i);

    /** Negation. */
    public native boolean booleanMethod(boolean bool);

    /** Increment each element by 1 inline, return input array. */
    public native int[] intArrayMethod(int[] intArray);

    /** Create uppercase version. */
    public native String stringMethod(String text);

    public static void main(String[] args) {
        /*
        Looks for file: "${java.library.path}/libMain.so".
        Main class is loaded from there.
        */
        System.loadLibrary("Main");
        Main m = new Main();
        assert(m.intMethod(2) == 4);
        assert(m.booleanMethod(false));
        assert(Arrays.equals(
            m.intArrayMethod(new int[]{1, 2}),
            new int[]{2, 3}
        ));
        assert(m.stringMethod("java").equals("JAVA"));
        System.out.println("ALL ASSERTS PASSED");
    }
}
