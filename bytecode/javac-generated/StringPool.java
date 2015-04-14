public class StringPool {
    public static void main(String[] args) {
        String a = "abc";
        String b = "abc";
        String c = "a" + "b" + "c";
        String d = new String("abc");
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
    }
}
