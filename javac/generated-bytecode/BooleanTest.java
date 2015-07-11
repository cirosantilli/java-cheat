public class BooleanTest {
    public static void main(String[] args) {
        // iconst_1
        // Seems that there is no boolean bytecode:
        // booleans are just treated as 0 / 1 bytes.
        System.out.println(true);
    }
}
