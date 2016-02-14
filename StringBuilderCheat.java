/*
# StringBuilder

    http://docs.oracle.com/javase/7/docs/api/java/lang/StringBuffer.html

    Mutable string. Implements `CharSequence` which is the main interface of String.

    May be more efficient than `+` on String as it may avoid string object creation.
    Smart compilers can optimize this out for simple `+`: http://stackoverflow.com/a/8595943/895245
    but not necessarily inside loops: http://stackoverflow.com/a/30451908/895245
    So just use it yourself.
*/

public class StringBuilderCheat {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("ab");
        sb.append("cd");
        sb.append("ef");
        assert sb.toString().equals("abcdef");

        // ERROR: + does not work: the magic is just for String
        //sb = sb + sb;
    }
}
