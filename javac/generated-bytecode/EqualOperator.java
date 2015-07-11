public class EqualOperator {
    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        // Precompiled
        System.out.println(1L == 1L);
        // lcmp + ifne
        System.out.println(1L == l);
        // Precompiled
        System.out.println("abc" == "abc");
        // if_acmpeq
        System.out.println("123" == new String("123"));
        // if_acmpeq
        System.out.println(new Object() == new Object());
    }
}
