public class Main {
    public native int intMethod(int i);
    public static void main(String[] args) {
        System.loadLibrary("Main");
        assert(new Main().intMethod(2) == 4);
    }
}
