public class CompileTimeConstant {
    public static void main(String[] args) {
        // Is 1 + 1 inlined or not?
        // Yes, to iconst_2
        System.out.println(1 + 1);
    }
}
