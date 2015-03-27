public class NullCheck {
    public static void method(Object o) {
        if (o != null) o.hashCode();
    }
}
