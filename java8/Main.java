import java.util.function.IntFunction;

public class Main {

    //@Repeatable
    //@interface RepeatableAnnotation {}
    @interface NotRepeatableAnnotation {}

    public static void main(String[] args) {

        /*
        # Lambda

            Define a function without name.
        */
        IntFunction<Integer> f = (int x) -> { return x + 1; };
        assert f.apply(1) == 2;

        /*
        # Repeatable
        */
        {
            // TODO how are all visible at once?
        }
    }
}
