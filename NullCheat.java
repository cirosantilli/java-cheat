/*
# null

    http://stackoverflow.com/questions/2707322/what-is-null-in-java

    Has explicit bytecode support via the `aconst_null` and `ifnull` instructions.
*/

public class NullCheat {

    public static String publicStaticString;

    public static void main(String[] args) {
        // Objects that are not explicitly initialized are initialized to `null`.
        assert publicStaticString == null;

        // Initialized objects are never null
        assert new Object() != null;

        /*
        # NullPointerException

            What you get for attempting to dereference a `null`.

            Exists at the bytecode level: `invoke` instructions are documented to raise it.
        */
        {
            String s = null;
            boolean fail = false;
            try {
                s.toLowerCase();
            } catch (NullPointerException e) {
                assert e.getClass() == NullPointerException.class;
                fail = true;
            }
            assert fail;
        }
    }
}
