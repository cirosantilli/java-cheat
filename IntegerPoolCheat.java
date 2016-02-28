/*
# Integer pool

# Wrapper pool

    Each wrapper has a cache pool that makes `==` equals on a range:

    - http://stackoverflow.com/questions/1700081/why-does-128-128-return-false-but-127-127-return-true-in-this-code
    - http://docs.oracle.com/javase/7/docs/api/java/lang/Integer.html#valueOf%28int%29
    - http://stackoverflow.com/questions/20877086/confusion-in-method-integer-valueofstring

    Boxing resolves to `Integer.valueOf()`,
    which like Strings has a pool mechanism.

    This is documented both on the JLS and on the `valueOf()` wrapper methods.

    But unlike strings, the pool mechanism is only
    for a limited range of integers
    between -128 and 127, and for true or false.

    The JSL itself states that ideally the range would be all integers,
    and that this is just an implementation limitation.

    It is also possible to configure the limit with:
    -Djava.lang.Integer.IntegerCache.high

    Summary: **always** use `equals()` to compare wrappers!
*/

public class IntegerPoolCheat {
    public static void main(String[] args) {
        assert Boolean.valueOf(true) == Boolean.valueOf(true);
        assert Boolean.valueOf(false) == Boolean.valueOf(false);

        // Byte: all values cached.
        assert Byte.valueOf((byte)127) == Byte.valueOf((byte)127);
        assert Byte.valueOf((byte)-128) == Byte.valueOf((byte)-128);

        // Char: from 0 to `\u007F`, the ASCII range

        // Integer
        {
            assert new Integer(1) != new Integer(1);

            assert Integer.valueOf(127) == Integer.valueOf(127);
            assert Integer.valueOf(128) != Integer.valueOf(128);

            // Same as above. Compiled bytecode uses `Integer.valueOf`.
            {
                Integer i1 = 127;
                Integer i2 = 127;
                assert i1 == i2;
            }
            {
                Integer i1 = 128;
                Integer i2 = 128;
                assert i1 != i2;
            }

            // New raw objects however are different as usual.
            assert new Integer(127) != new Integer(127);

            // There is a documentation bug, but `valueOf(String)` also uses the Integer pool, not new:
            //http://stackoverflow.com/questions/508665/difference-between-parseint-and-valueof-in-java
            assert Integer.valueOf("127") == Integer.valueOf("127");
            assert Integer.valueOf("128") != Integer.valueOf("128");
        }
    }
}
