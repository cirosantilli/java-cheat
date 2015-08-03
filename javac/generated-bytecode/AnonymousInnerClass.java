public class AnonymousInnerClass {
    public static void main(String[] args) {
        new AnonymousInnerClass() {
            void m() { System.out.println("hello world"); }
        }.m();
    }
}
