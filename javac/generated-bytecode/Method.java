/** Simple method with multiple inputs and one output. */
public class Method {
    public static int method(int i, double d, Integer I) {
        return i + ((int)d) + I.intValue();
    }
    public static void main(String[] args) {
        System.out.println(method(1, 2.0, new Integer(3)));
    }
}
