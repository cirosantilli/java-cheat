public class Main {
    public static void main(String[] args) {
        // IncompatibleClassChangeError
        // Bytecode: getstatic vs getfield
        //if ((new StaticField()).i != 1) throw null;

        class Public extends {}

        // Null pointer exception.
        // API only change.
        //NullCheck.method(null);
    }
}
