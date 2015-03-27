public class Main {
    public native int intMethod(int i);
    public static void main(String[] args) {
        System.loadLibrary("Main");
        System.out.println(new Main().intMethod(2));
    }
}
