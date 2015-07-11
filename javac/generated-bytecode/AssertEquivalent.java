/*
Generates the exact same bytecode as Assert, except that:

- the field is not synthetic
- the line table debug information is larger :-)
*/
public class AssertEquivalent {
    static final boolean $assertionsDisabled = !AssertEquivalent.class.desiredAssertionStatus();
    public static void main(String[] args) {
        if (!$assertionsDisabled) {
            if (System.currentTimeMillis() != 0L) {
                throw new AssertionError();
            }
        }
    }
}
