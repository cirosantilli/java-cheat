public class StaticMethod {
    // invokestatic StaticMethod.f()
    //
    // The first argument is `this`, loaded with aload_0.
    public static int method(int i, double d, Integer I) {
        return i + ((int)d) + I.intValue();
    }
    public static void main(String[] args) {
        System.out.println(method(1, 2.0, new Integer(3)));
    }
}
