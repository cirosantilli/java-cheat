public class Main {
    public native String nativeMethod();

    static {
        System.loadLibrary("foo");
    }

    public void print () {
        System.out.println(nativeMethod());
    }

    public static void main(String[] args) {
        (new Main()).print();
    }
}
