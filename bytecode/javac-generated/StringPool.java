// http://stackoverflow.com/a/29978564/895245
public class StringPool {

    // ConstantValue Attribute
    // https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.2
    public static final String psfs = "abc";

    // Static initializers.
    public static String pss = "abc";
    public static final Object psfo = new String("abc");

    public static void main(String[] args) {
        // Constant Pool
        String a = "abc";
        String b = "abc";
        // Precompiled.
        String c = "ab" + "c";

        // Regular object.
        String d = new String("abc");

        System.out.println(a);
        System.out.println(b);
        // if_acmpne
        System.out.println(a == d);
    }
}
