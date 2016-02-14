/*
# super
*/

public class SuperCheat {
    public static void main(String[] args) {
        /*
        Call overriden constructor of superclass:

            super()

        This is already implicily added if you don't have a super call.
        */

        /*
        Call overriden method of superclass:

            super.method()

        TODO possible without repeating the method name?
        */

        /*
        # super.super

            Not possible: http://stackoverflow.com/questions/586363/why-is-super-super-method-not-allowed-in-java
        */

        /*
        # this() and super() on a single constructor

            Not possible:

            - http://stackoverflow.com/questions/10381244/why-cant-this-and-super-both-be-used-together-in-a-constructor
            - http://stackoverflow.com/questions/6965561/how-to-call-both-super-and-this-in-case-of-overloaded-constructors
        */
        {
            class Class {
                Class() {
                    super();
                    //this(1);
                }

                Class(int i) {}
            }
        }

        /*
        Must be the very first statement of the constructor if present.

        Consequence: only one super call per class.
        */
        {
            class Class {
                int i;
                Class() {
                    i = 1;
                    // ERROR: call to super must be the first thing in a constructor.
                    //super();
                }
            }
        }
    }
}
