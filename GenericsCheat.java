/*
# Generics

    Introduced in Java 5 to allow more compile time checks.

    Incurs not runtime cost because of type erasure.

    # Templates

        Superficially similar concept in C++, but in C++ templates compile exactly to code,
        while Java also relies on polymorphism TODO check.

    Both types and methods can be generic. both will be commented here.

    # Vs polymorphism

        <http://programmers.stackexchange.com/questions/227918/java-use-polymorphism-or-bounded-type-parameters>

        In many cases, are the same.

        In a few cases, generics are more powerful.

        Only use them if you need the extra capabilities,
        as they make the code harder to read.

        Cases where they are needed:

        - the type of a function parameter is parametrized by the type
        - return value

        TODO understand why in those cases are necessary.

    # Parametrized types

        Types that depend on other types, e.g. `LinkedList<String>`.

    # Type arguments

        Types passed to parametrized types. E.g. `String` in `LinkedList<String>`.
*/

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GenericsCheat {

    public static <T> T genericMethod(T i) {
        return i;
    }

    public static <T> String genericGetClass(T i) {
        return i.getClass().getSimpleName();
    }

    //public static <T> T genericGetInstance() {
        //return what?
    //}

    //public static <T> Class<T> genericGetClassDotClass(T t) {
        //return T.class;
    //}

    public static <T> String genericMethodGenericArgument(List<T> l) {
        return l.getClass().getSimpleName();
    }

    public static class GenericStatic<T> {
        static int i = 0;
    }

    public static void main(String[] args) {
        /*
        # Raw types

            Generic types but without passing the generic arguments.

            This is how Java was before 1.5: the syntax is only kept for backwards compatibility.

            Never use this on new code, as you lose compile time checks.

            The main reason that generics are useful is that most collections contain a single type of objects.
            Generics then add typecasts and typechecks automatically for us.
        */
        {
            // The pre 1.5 raw types way.
            {
                @SuppressWarnings({ "rawtypes" })
                ArrayList names = new ArrayList();
                @SuppressWarnings({ "unchecked" })
                boolean b = names.add("abc");
                @SuppressWarnings({ "unchecked" })
                boolean b2 = names.add(1);

                // Object returned by default.
                Object name = names.get(0);
                // We've done this cast correctly.
                name = (String)name;
                assert(name.equals("abc"));

                boolean fail = false;
                try {
                    name = (String) names.get(1);
                } catch (ClassCastException e) {
                    fail = true;
                }
                assert fail;
            }

            // After generics: see how much better this is than with raw types
            // if our container is supposed to contain only a single data type.
            {
                ArrayList<String> names = new ArrayList<>();
                names.add("abc");
                // No cast needed on the output!
                assert(names.get(0).equals("abc"));

                // Fail on compile time.
                //names.add(1);
                //names.add((String)1);

                boolean fail = false;
                try {
                    // Compiles, but we've asked for trouble with a double cast.
                    // And now the exception happens before insertion, not after retrival.
                    names.add((String)(Object)1);
                } catch (ClassCastException e) {
                    fail = true;
                }
                assert fail;
            }
        }

        /*
        Primitive types cannot be passed as type arguments.

        What happens in most cases is that primitive wrappers are used instead,
        and boxing and unboxing conversions produce the eye candy.
        */
        {
            // ERROR: unexpected type.
            //LinkedList<int> l;

            LinkedList<Integer> m;
        }

        /*
        # Generic method
        */
        {
            Object o = new Object();
            assert GenericsCheat.<Object>genericMethod(o) == o;

            // In this case however the type can be inferred from the argument.
            assert genericMethod(o) == o;

            /*
            If you want to pass the type parameter explicitly,
            you need to add explicitly:

            - the name of the class for static methods
            - `this.<T>method()` for instance methods
            */
            {
                // ERROR
                //.<Object>genericMethod(o);
                //<Object>genericMethod(o);
            }
        }

        // Impossible stuff wigh generics.
        {
            /*
            # Call constructor of generic type

            # Instantiate generic type

                Not possible without reflection:
                http://stackoverflow.com/questions/75175/create-instance-of-generic-type-in-java
            */
            {
                //Integer i = genericGetInstance();
            }

            /*
            # Use .class on generic

                Not possible because of type erasure.

                http://stackoverflow.com/questions/18255117/how-do-i-get-the-class-attribute-from-a-generic-type-parameter
            */
            {
                //assert Main.<Object>genericGetClassDotClass() == Object.class;
            }

            // Use generics with static members.
            {
                // As a consequence, you must access static members without the generic parameters:

                // ERROR: Not a statement.
                //assert GenericStatic<Integer>.i == 0;

                assert GenericStatic.i == 0;
            }
        }

        /*
        # Type inference for generics

        # <>

        # Diamond

            Java 7 Coin Project feature.

            Java can sometimes infer types, and in that case you can simply write TODO check:

            - `<>` for the type parameters of constructors
            - nothing for method calls

            The rules of type inference are complex and described at:
            http://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.12

            There have been considerable changes in Java 8.
        */
        {
            // Generic class.
            {
                // Infer type from argument.
                assert genericGetClass(new Object()).equals("Object");

                // Infer type from return value. TODO
                genericMethodGenericArgument(new ArrayList<>());

                /*
                Return value to method call inference did not work in Java 7,
                likely because of the complication that methods can be overloaded.
                This has changed in Java 8.
                http://stackoverflow.com/questions/15435747/java-7-generics-type-inference
                */

                // Does not work with anonymous subclasses.
                //http://stackoverflow.com/questions/9773733/double-brace-initialisation-anonymous-inner-class-with-diamond-operator
            }
        }

        /*
        # Type erasure

            Compiled bytecode does not contain any special instructions for it.

            Wildcards are simply compiled to the most base possible type:
            e.g. `List<? extends Number>#get()` compiles to `(Number)List#get`.

            http://docs.oracle.com/javase/tutorial/java/generics/erasure.html

            TODO understand this for real.

        # Reified generics

            http://stackoverflow.com/questions/879855/what-are-reified-generics-how-do-they-solve-the-type-erasure-problem-and-why-ca

            A feature which is not part of the language, but would allow for more flexible generics.
        */

        /*
        Generics and instanceof
        */
        {
            ArrayList<String> s = new ArrayList<>();

            assert s instanceof ArrayList;

            // ERROR. Makes no sense: generics don't generate actual new types:
            // only a bunch of automatic typechecks on `.add()`, .get()`, etc.
            //assert s instanceof ArrayList<String>;
        }

        /*
        # Unbounded wildcard

        # <?>

            http://docs.oracle.com/javase/tutorial/java/generics/unboundedWildcards.html

            Applications:

            - ignore the type
            - something that extends from Object, since very class does so.

            TODO examples.
        */
        {
        }

        /*
        Generics and typecasts
        */
        {
            ArrayList<String> s = new ArrayList<>();
            ArrayList<Integer> i = new ArrayList<>();
            ArrayList<Number> n = new ArrayList<>();
            //s = i;
            //i = n;
            //n = i;
            //n = (ArrayList<Number>)i;
        }
    }
}
