/*
# String

    https://docs.oracle.com/javase/7/docs/api/java/lang/String.html

    Slightly magic class because of:

    - literals
    - interning and efficient bytecode representation: http://stackoverflow.com/questions/2486191/what-is-the-java-string-pool-and-how-is-s-different-from-new-strings/29978564#29978564
    - `+` operator

    http://docs.oracle.com/javase/7/docs/api/java/lang/String.html

    Immutable. Therefore not a Collection, which would require `add`.
    It implements `CharSequence` instead.
*/

public class StringCheat {
    public static void main(String[] args) {
        /*
        # String literals

            String literals are dynamically allocated just like using new String.
        */
        {
            assert "ab".getClass() == String.class;

            /*
            # Heredoc

            # Multiline string

            # String with quotes without escaping

                Impossible:

                - http://stackoverflow.com/questions/878573/java-multiline-string
                - http://stackoverflow.com/questions/3034186/in-java-is-there-a-way-to-write-a-string-literal-without-having-to-escape-quote

            */
        }

        /*
        # length for String

            Is a method, and not the magic `.length` field, since String is not an Array.
        */
        {
            assert "abc".length() == 3;
        }

        /*
        # Compare strings

        # == operator for strings

        # intern

        # String pool

        # Interning

            - http://stackoverflow.com/a/29978564/895245
            - http://stackoverflow.com/a/513839/895245

            Unlike `+`, `==` is *not* magic for strings,
            and like for Object compares instances instead of content.

            What *is* magic, is that:

            -   compile time constants resolve to `String.intern()` (on the bytecode level)
                so `==` works for them.

            -   `+` for compile time constants is done at compile time.

                So `==` works in that case.

                TODO is `+` the only operator on strings?

            This behavior is specified by:
            http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.5

            This language design pattern is called "String interning",
            and it can also exist for other immutable object literals like all literals in Ruby.

            For you sanity, just don't rely on interning and always use `equals`.
        */
        {
            assert "abc" == "abc";
            assert "abc" != new String("abc");
            assert "abc" == new String("abc").intern();
        }

        /*
        # Concatenate strings

        # plus operator for strings

        # + operator for strings

            The `+` operator for strings is magic:

            - it is overloaded for an object, but Java does not have operator overload.

            - it generates compile time constants for strings like it does for primitive types

            `StringBuilder` and StringBuffer` are like a mutable version.
            Use them for multiple concatenations, otherwise a new buffer may be allocated every
            time with `+` and `concat.`

            The compiler optimizes `+` into `StringBuilder` calls, but only does so naively:
            it does not seem to work for concatenations inside a loop:
            http://stackoverflow.com/questions/14927630/java-string-concat-vs-stringbuilder-optimised-so-what-should-i-do
            So always use:

            - `StringBuilder` for computationally built computations
            - `+` for simple compile time constants and interpolation
        */
        {
            // +
            assert ("ab" + "cd").equals("abcd");
            assert ("ab" + 1).equals("ab1");
            assert (1 + "ab").equals("1ab");

            // +=
            {
                String s = "ab";
                s += "cd";
                assert s.equals("abcd");
            }

            // Guaranteed to be done at compile time,
            // and be the same object.
            assert "ab" + "cd" == "abcd";

            /*
            + vs concat:
            http://stackoverflow.com/questions/47605/string-concatenation-concat-vs-operator

            - `+` can do String convertions, concat not.
            - `+` is compiled at compile time, concat not.
            */

            // ERROR. Unlike C, does not work.
            // But `+` is guaranteed to be dealt with at compile time,
            // see the section on String equality.
            //assert "ab" "cd" == "abcd";

            // Multiline string literals. Nope:
            // http://stackoverflow.com/questions/878573/java-multiline-string
        }

        /*
        # charAt

            Get character at given position.
        */
        {
            assert "ab".charAt(0) == 'a';

            // ERROR: array required, but string found.
            // Brace magic is only for arrays.
            // Furthermore, Strings are immutable,
            // and brace notation invites mutability.
            //assert "ab"[0] == "a";

            /*
            4-byte UTF-16 characters are returned as two char...

            To deal with those properly, you need to use the codePointXXX methods.

            Just imagine how much Java code has been broken by this...

            http://docs.oracle.com/javase/7/docs/api/java/lang/String.html#codePointAt%28int%29

            This is of course like this because when Java was developed, UTF-16 was the hot encoding,
            and the 4-byte extension did not exist yet, and UTF-8 was not popular at the time.
            */
            {
                // TODO example
            }
        }

        /*
        # toCharArray

            Makes a copy.
        */

        /*
        # Iterate string characters

            http://stackoverflow.com/questions/196830/what-is-the-easiest-best-most-correct-way-to-iterate-through-the-characters-of-a

            For loop with explicit variable.
        */

        /*
        # repr like in Python

            Not by default: http://stackoverflow.com/questions/1350397/java-equivalent-of-python-repr
        */

        /*
        # split

            Split string into an array of strings.
        */

        /*
        # join

            Join an array of strings with a given separator.

            Does not exist in the stdlib of Java 7:
            http://stackoverflow.com/questions/1978933/a-quick-and-easy-way-to-join-array-elements-with-a-separator-the-opposite-of-sp

            But added to Java 8:
            http://stackoverflow.com/a/26195047/895245

            To implement, use StringBuilder, then:

            - use `sb.setLength(sb.length() - 1)` at the end. Likely the best possibility.
            - iterator hasNext check
            - use array and stop at i - 1
            - modified separator pattern: http://stackoverflow.com/a/3395345/895245
        */

        /*
        # startsWith

        # endsWith

            Boolean checks.
        */
    }
}
