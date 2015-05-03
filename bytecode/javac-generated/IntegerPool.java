public class IntegerPool {
    public static void main(String[] args) {
        // Same in both cases.
        // Not a bytecode feature, but Java only.
        System.out.println(Integer.valueOf(127) == Integer.valueOf(127));
        System.out.println(Integer.valueOf(128) != Integer.valueOf(128));
    }
}
