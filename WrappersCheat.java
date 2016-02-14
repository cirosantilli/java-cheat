/*
# Primitive wrappers

# Byte

# Char

# Short

# Short

# Float

# Double

    Each primitive type has an Object wrapper: Byte, Integer, Char, etc.

    Those object wrappers are necessary in situations
    where objects are required instead of primitives.

    The primitive wrappers have the following magic properties in the language:

    - boxing and unboxing conversion

# Number

    http://docs.oracle.com/javase/7/docs/api/java/lang/Number.html

    Implemented by all primitive wrappers.

    Only contains the `xValue` methods.
*/

public class WrappersCheat {
    public static void main(String[] args) {
        /*
        Primitive wrappers are immutable:
        http://stackoverflow.com/questions/3815173/increment-a-integers-int-value

        This is how you increment them:
        */
        {
            Integer i = 0;
            i = new Integer(i.intValue() + 1);
            assert i == 1;
        }

        /*
        # Add wrappers

        # Multiply wrappers

            The primitive wrappers do not have methods that can be performed directly with operators
            such as add and multiply likely because:

            - unboxing produces a nice syntax
            - the efficiency of unboxing is already optimal given immutability

            BigInteger and BigDecimal however to implement those methods
            since they cannot perform boxing conversions.
        */
        {
            assert new Integer(1) + new Integer(2) == 3;
        }

        /*
        # Compare wrappers

        # Equality wrappers

            Equality comparison `==` does *not* unbox them,
            but because of the wrapper pool it works for some values.

            < and > does.
        */
        {
            assert new Integer(1) != new Integer(1);
            assert (new Integer(1)).equals(new Integer(1));
            assert new Integer(1) < new Integer(2);
        }

        /*
        # Void

            TODO what is this used for?
            <http://stackoverflow.com/questions/643906/uses-for-the-java-void-reference-type
        */
        {
            Void v;

            // The only valid valud that can fit into a Void is null:
            v = null;
        }

        /*
        # NaN
        */
        {
            // http://stackoverflow.com/questions/8819738/why-does-double-nan-double-nan-return-false
            // Basically because many unrealted operations can generate NaN,
            // so comparing it does not make much sense.
            assert Double.NaN != Double.NaN;
            assert Double.isNaN(Double.NaN);
        }

        /*
        # Integer wrapper

            Wrapper for `int`.
        */
        {
            /*
            # parseInt
            */
            {
                assert Integer.parseInt("123") == 123;

                // Very strict. No whitespaces or other noise.
                boolean fail = false;
                try {
                    Integer.parseInt("123\n");
                } catch (NumberFormatException e) {
                    fail = true;
                }
                assert fail;
            }
        }
    }
}
