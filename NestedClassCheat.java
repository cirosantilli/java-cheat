/*
# Nested classes

    Any class that is declared inside of another.

    JLS7 8

    Types:

    - inner classes
    - static nested classes

    # Visibility and nested classes

        Nested classes can see private members of the outer class and vice versa.

        TODO example.
*/

public class NestedClassCheat {

    public static int publicStaticInt;

    static class StaticOuter {
        static private final int i = 1;
        static class StaticNested {
            static int getI() {
                return i;
            }
        }
    }

    public static void main(String[] args) {
        /*
        # Inner class

            JLS7 8.1.3

            3 Types:

            - local
            - anonymous
            - non-static member

            TODO are inner interfaces possible? Not local.

            Those classes have in common that they can access the `this`
            of the object in which they are inserted, unlike static member classes:

            - local based on the `this` of the method where it gets defined
            - anonymous and non-static member based on the object creation
        */
        {
            /*
            # Local class

                Class defined and only visible from inside a block,
                often a method body.
            */
            {
                // Basic example.
                {
                    class Local {
                        public int i;
                        public Local(int i) {
                            this.i = i;
                        }
                    }
                    // Semicolon not needed!
                    Local l = new Local(1);
                    assert l.i == 1;
                }

                /*
                Inner classes cannot have static non-final fields or methods:
                http://stackoverflow.com/questions/1953530/why-does-java-prohibit-static-fields-in-inner-classes
                TODO rationale.
                */
                {
                    class Local {
                        // ERROR: Illegal static declaration.
                        //public static int i = 1;

                        public static final int fi = 1;

                        // ERROR
                        //public static int method(){ return 1; }
                    }
                    assert Local.fi == 1;
                }

                {
                    if (false) {
                        class Local {
                            public static final int i = 1;
                        }
                    } else {
                        class Local {
                            public static final int i = 2;
                        }
                    }
                }
            }

            /*
            # Anonymous class

                Very similar semantics to a local class, but without a name. Differences:

                -   cannot have a constructor

                -   since called on a new, does not need a constructor either,
                    not even a forwarding constructor if the base
                    does not define the default constructor

                Useful when you only need to override something once.
            */
            {
                class Base {
                    public int i;

                    public Base() {
                        this.i = 0;
                    }

                    public Base(int i) {
                        this.i = i;
                    }

                    public int inc(int i) {
                        return i + 1;
                    }
                }

                // Basic example.
                {
                    // THIS generates a new class that has no name.
                    Base b = new Base(1) {
                        int j;
                    };
                    assert b.i == 1;

                    // ERROR: cannot find symbol
                    //b.j = 2;

                    // Or we can just do
                    assert new Base(1){}.i == 1;
                }

                // The anonymous class inherits from the one on `new`.
                {
                    assert new Base(){}.getClass().getSuperclass() == Base.class;
                }

                // Only the old one field is visible from the outside.
                {
                    Base b = new Base(1) {
                        int i;
                    };
                    assert b.i == 1;
                }

                /*
                Methods can be overideen.

                This is the main application of anonymous classes:
                when a method takes the implementation of an abstract class,
                and you only need to call it once with that derived class.
                */
                {
                    Base b = new Base(1){
                        @Override
                        public int inc(int i) {
                            return i + 2;
                        }
                    };
                    assert b.inc(1) == 3;
                }

                // Cannot have constructors:
                // http://stackoverflow.com/questions/362424/accessing-constructor-of-an-anonymous-class

                /*
                # Modify non-final local variable in anonymous class

                    Cannot only access them, not modify them.

                    This is why we say that in Java 7, there were no real closures.

                    Possible in Java 8.

                    http://stackoverflow.com/questions/4732544/why-are-only-final-variables-accessible-in-anonymous-class
                */
                {
                    int local = 0;
                    final int finalLocal = 0;
                    Base b = new Base() {
                        int get() {
                            // Java 8 ERROR local variables accessed form inner class must be final or effectively final
                            // http://stackoverflow.com/questions/20938095/difference-between-final-and-effectively-final
                            //local = 1;
                            // TODO check: I think this only works in Java 8 if the above is not done:
                            //return local;
                            return 0;
                        }

                        int getLocal() {
                            return finalLocal;
                        }

                        int getOuterMember() {
                            return publicStaticInt;
                        }
                    };
                }
            }

            /*
            # Non-static nested class

                Member classes that are not declared static.

                You need an instance of the outer class in order to access the inner one.

                This inner class can always obtain the this from the outer class.
            */
            {
                // Basic example.
                {
                    class Outer {
                        private int i = 1;
                        class Inner {
                            int getI() {
                                return i;
                            }

                            int getIOuterThis() {
                                return Outer.this.i;
                            }

                            // The explicit `this` does not work,
                            // since it refers to a field of the current inner class only.
                            //int getIThis() { return this.i; }
                        }

                        // Not allowed. TODO why?
                        //public class PublicInner {}
                    }

                    Outer outer = new Outer();

                    // Note the weird object.new syntax.
                    // This is required because the inner object
                    // contains a reference to the outer.
                    Outer.Inner inner = outer.new Inner();

                    // Can access private member variables of enclosing class.
                    assert inner.getI() == 1;
                    assert inner.getIOuterThis() == 1;
                    //assert inner.getIThis() == 1;
                }

                /*
                You cannot call the constructor of the inner class before that of the outer.
                because the inner class contains a pointer to the outer.
                */
                {
                    class Outer {

                        public Outer() {
                            // ERROR: cannot reference this before supertype constructor.
                            //this(new Inner());
                        }

                        public Outer(Inner inner) {}

                        class Inner {}
                    }
                }
            }
        }

        /*
        # Static nested class
        */
        {
            // Can access private fields of the enclosing class.
            assert StaticOuter.StaticNested.getI() == 1;
        }
    }
}
