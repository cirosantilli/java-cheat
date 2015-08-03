public class StaticField {
    // Static initializer.
    static int i = 1;
    public static void main(String[] args) {
        // getstatic
        System.out.println(i);
        // putstatic
        i++;
        System.out.println(i);
    }
}
