public class Method {
    int i;
    public int method(int i, double d, Integer I) {
        return this.i + i;
    }
    public static void main(String[] args) {
        Method o = new Method();
        System.out.println(o.method(1, 2.0, new Integer(3)));
    }
}
