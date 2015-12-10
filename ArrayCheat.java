/*
# Array

    JLS7 10

    Arrays are built-in into the language.
    Therefore, there is no Javadoc which documents them:
    you have to read the JLS.

    Arrays do however have class-like syntax semantics.

    Arrays are not Generics nor do they implement Collection.

    Arrays have fixed size. Use `ArrayList` for variable size arrays.

    Do not confuse arrays with the Array class:
    http://docs.oracle.com/javase/7/docs/api/java/lang/reflect/Array.html
    which just contains some convenience methods to work with arrays.

    Java bytecode has various array-specific instructions such as
    `newarray`, `arraylength`, `iastore`, etc.
*/

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayCheat {

    static int arrayParam(int[] is) {
        return is[0] + is[1];
    }

    public static void main(String[] args) {
        // # Declare arrays
        {
            /*
            Putting `[]` after `is` exactly the same as before.

            I find after bad style since it splits type infomation appart:
            type is `int[]`, not `int`.

            Can even use both?
            */
            {
                int[] is;
                int js[];
                int[] ks[];
            }
        }

        // # Create arrays
        {
            // Create and declare at once vs separately
            {
                // At once
                {
                    int[] is = new int[3];
                    assert is[0] == 0;
                    assert is[1] == 0;
                    assert is[2] == 0;
                }

                // Separately
                {
                    int[] is;
                    is = new int[3];
                    assert is[0] == 0;
                    assert is[1] == 0;
                    assert is[2] == 0;
                }
            }

            /*
            # Maximum Array size

                MAX_INT: <http://stackoverflow.com/questions/3038392/do-java-arrays-have-a-maximum-size>
            */
            {
                // ERROR: Incompatible types: conversion from long to int.
                //byte[] bytes = new byte[0xFFFF_FFFF_1L];
            }

            // Primitives vs objects
            {
                // Primitives
                {
                    int[] is = new int[2];
                    is[0] = 0;
                    is[1] = 1;
                    assert is[0] == 0;
                    assert is[1] == 1;
                }

                // Objects
                {
                    Integer[] is = new Integer[2];
                    is[0] = 0;
                    is[1] = 1;
                    assert is[0] == 0;
                    assert is[1] == 1;
                }
            }

            // Initialization values
            {
                // Default values if none given.
                {
                    // Primitives
                    {
                        int[] is = new int[2];
                        assert is[0] == 0;
                        assert is[1] == 0;
                    }

                    // Objects
                    {
                        String[] ss = new String[2];
                        assert ss[0] == null;
                        assert ss[1] == null;
                    }
                }

                // Custom values
                {
                    // Long syntax
                    {
                        // Good
                        {
                            int[] is = new int[]{0, 1};
                            assert is[0] == 0;
                            assert is[1] == 1;
                        }

                        // This is how you create arrays on the fly for functions.
                        {
                            assert arrayParam(new int[]{1, 2}) == 3;
                        }

                        // Bad: cannot have the size and be inialized at once.
                        //int[] is = new int[2]{0, 1};
                    }

                    // Shorthand syntax
                    {
                        // Good
                        {
                            int[] is = {0, 1};
                            assert is[0] == 0;
                            assert is[1] == 1;
                        }

                        // Bad: must be used for explicit declaration / initialization.
                        //assert {0, 1}[1] == 1;
                    }

                    // Array with n equal values
                    {
                        // Objects: Collections.nCopies().toArray();

                        // Primitives: don't know.
                    }
                }
            }
        }

        /*
        # Arrays look like classes

            But they are not classes. It's just JLS magic.

            http://docs.oracle.com/javase/7/docs/api/java/lang/reflect/Array.html
        */
        {
            // .class works on them
            {
                int[] is = new int[3];

                assert is.getClass() == int[].class;
                assert int[].class.getSuperclass() == Object.class;
            }

            /*
            JLS7 5.1.6 says that arrays can be converted to Serializable and Cloneable.

            This means that arrays behave as if they implement those interfaces,
            which are therefore magic.
            */

            /*
            # Iterate array

                Arrays don't implement iterable since they are not regular objects,
                but the enthanced for loop (`:`) works magically for them as well.

                http://docs.oracle.com/javase/specs/jls/se7/html/jls-14.html#jls-14.14.2
                says that the "Expression must be Iterable or an array type".
            */
            {
                ArrayList<Integer> a = new ArrayList<>();
                ArrayList<Integer> a2 = new ArrayList<>();
                a.add(1);
                a.add(2);
                for (int i : a)
                    a2.add(i);
                assert a.equals(a2);
            }
        }

        /*
        # length of Array

            Unlike C, arrays are classes and have a length field.

            This field is not a regular field access:
            it generates a special `arraylength` JVM instruction.
        */
        {
            int[] is = new int[3];
            assert is.length == 3;
        }

        /*
        # ArrayIndexOutOfBoundsException

        # Bound checking

            Unlike in C, Java checks bounds and raises nice exceptions
            instead of allowing arbitray code execution vulnerabilities :)
        */
        {
            int[] is = new int[3];
            try {
                assert is[3] == 0;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(
                    "ArrayIndexOutOfBoundsException.getMessage() = "
                    + e.getMessage()
                );
            }
        }

        /*
        # range

            No built-in equivalent of python `range()` in Java 7:
            http://stackoverflow.com/questions/3790142/java-equivalent-of-pythons-rangeint-int

            Just use a for loop instead.

            Possible in Guava, and with Java 8 `IntStream.range`.
        */
        {
            Integer[] ints = new Integer[10];
            for (int i = 0; i < 10; i++) {
                ints[i] = new Integer(i);
            }
        }

        /*
        # clone arrays

            On arrays, `.clone()`, `Arrays.copyOf` and
            `System.arrayCopy` are all the same, shallow, copies.

            Arrays are the only place where `.clone()` works well.
            So better avoid it and go ti `Arrays.copyOf`.

            The main problem with `Arrays.copyOf` is that its clumsy interface
            takes the length as a parameter requiring extra typing for
            full array copies.

            Arrays does not provide a multi-dimensional deep copy, only shallow:
            <http://stackoverflow.com/questions/1564832/how-do-i-do-a-deep-copy-of-a-2d-array-in-java>
            This could be expected since it does have some deep array-only methods like `deepEquals`.
        */
        {
            int[] is = new int[]{0, 1};
            int[] js = is.clone();
            assert(is != js);
            assert(Arrays.equals(is, js));
        }
    }
}
