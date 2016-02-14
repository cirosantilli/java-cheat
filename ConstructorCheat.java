/*
# Constructor

    JLS7 8.8

    Cannot be `abstract`, `static`, `final`, `native`,
    `strictfp`, or `synchronized`.

    Can however be `private` to prevent object construction,
    e.g. in static utility classes.
*/

public class ConstructorCheat {
    public static void main(String[] args) {
        /*
        # Default constructor

            Constructor automatically defined if you don't define any other constructor.

            Sets all fields to their default values.
        */
        {
            class Class {
                Class(int i) {}
            }
            // ERROR: we did define a constructor Class(int),
            // so the default constructor was not defined.
            //new Class();

            // TODO: is an empty constructor equivalent to the default constructor?
        }

        /*
        # Method with the same name as the constructor

            Horrendous, but valid.

            A constructor is only considered if there is no return value.
        */
        {
            class Class {
                int i;
                Class(int i) { this.i = i; }
                int Class() { return this.i; }
            }
            assert new Class(1).Class() == 1;
        }

        /*
        # Constructor inheritance

            Does not exist. You have to duplicate code:
            http://stackoverflow.com/questions/1644317/java-constructor-inheritance

            Likely rationale: otherwise all classes would have an empty constructor
            derived from `Object`, and it does not make much sense to many classes.
        */
        {
            class Base {
                public int i;
                Base(int i) { this.i = i; }
            }

            // ERROR: would generate only the default constructor,
            // which calls super(), which does not exist.
            //class Class extends Base {}

            // OK.
            class Class extends Base {
                Class(int i) {
                    super(i);
                }
            }

            assert new Class(1).i == 1;
        }
    }
}
