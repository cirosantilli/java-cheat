/*
Main Java 7 cheat.

TODO: this is being split up into multiple files to make things more searchable.

Will include every test that does not take too long or produce too much output.

Assertions will be used wherever possible, and values will only be printed if
they cannot be deterministically checked.

# Preprocessor

    Java has no preprocessor like in C.

    Unlike C, there is no non-messy way of doing the following:

    - get current line number
    - get current function name

    # Conditional compile according to Java version / OS

        - Create an interface with a builder for implementations
        - Detect OS / Java version with `System.getProperty`

        http://stackoverflow.com/questions/4526113/java-conditional-compilation-how-to-prevent-code-chunks-to-be-compiled
*/

/*
# import

    In Java, import is just a shorthand generator: after importing `a.b.C` you can use just C.

    But you can use classes without importing as long as you pass the full path

        a.b.C c = new a.b.C();

    Becaues of this, every import argument must have a `.`,
    otherwise it is an immediate compile error.

    TODO search path?

    It is not possible to import a class without package:

    # import nested classes

        The outer class names acts like a package.

        To use the shorthand form `Inner`, you need to import as:

            import Outer.Inner;

        even when in the current package.

    # import static

        `import static` imports a static method into the current namespace.

        E.g., after:

            import static Math.max;

        you can then use just:

            max(0, 1);

        instead of:

            Math.max(0, 1);

        It only works for static methods, for which the syntax `max()` makes sense:
        non-static methods need to know which object they belong to.

# package

    # Domain name convention

        It is a common convention to match package names with domain names to avoid conflicts.

        E.g., if you own `xyz.com`, then you could name your classes as `com/xyz/Class.java` of `com/xyz/app0/Class.java`.

        Perfect bijection is of course impossible, because many domain names cannot be package names.

        What to do in some of those cases:
        http://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html

        Basically:

        -   replace hyphens with underscores

        -   prefix reserved package names like `int` and `java`
            and domain names that start with numbers like `123.com` with an underscore

    # Reserved package names

        `java` and `java.lang` are reserved, and raise `SecurityException` at runtime.

        TODO what are all reserved package names?
*/

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/*
# Top level classes

    There can be only one public top-level class per Java file
    and it *must* have the same name as the file.

    The only things that can come outside of top-level classes in Java are:

    - package declaration
    - import statement

    Having multiple top-level classes is highly discouraged and almost never seen in practice:
    http://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file
*/

    // ERROR: cannot be public because does not match the file name.
    //public class Main2 {}

    // ERROR: Top level classes cannot have neither protected, private or static modifiers.
    //private class Main2 {}

    // OK.
    class Main2 {}

public class Main {

    // General purpose fields

        public static int publicStaticInt;

    // Field modifiers

        class TransientField {
            public transient int ti;
            public int i;
        }

    // Class

        public static class Empty {}

        /*
        Minimalistic integer wrapper.
        */
        public static class MyInt {
            public int i;
            public MyInt(int i) {
                this.i = i;
            }
        }

        public static class TwoInts implements Serializable {
            public int i;
            public int j;
            public TwoInts(int i, int j) {
                this.i = i;
                this.j = j;
            }
            public boolean equals(TwoInts other) {
                return this.i == other.i && this.j == other.j;
            }
        }

        // TODO break this huge test class up.
        public static class Class0 {

            // member

                private int i = 0;
                protected int member;
                public Class0 class0;

            // static

                static int staticI = 0;
                //ERROR: already defined
                // int staticI = 0;

            private final int finalMember;
            private final int finalMemberConstructor = 0;
            static private final int final_static_member = 0;
            // ERROR: static final not initialized.:
            //static private final int final_static_member_uinit;

            public Class0() {
                finalMember = 0;
            }

            public Class0(Class0 other) {
                this.class0 = other;
                this.finalMember = 0;
            }

            // Constructor
            public Class0(int i) {

                this.i = i;
                member = i;

                finalMember = i;
                // ERROR: already initialized.
                //finalMemberConstructor = 1;

                this.staticI = 0;
            }

            public int method() {
                // ERROR: cannot set final members outside constructor
                //finalMember = 0;
                return member;
            }

            public void setI(int i) {
                this.i = i;
            }

            public int getI() {
                return this.i;
            }
        }

        public static class OverrideEquals {
            public int i;

            public OverrideEquals(int i) {
                this.i = i;
            }

            @Override
            public boolean equals(Object other) {
                // TODO how to do it? OverrideEquals other does not override base.
                //return this.i == other.i;
                return true;
            }
        }

        public static class Finalize {
            public String s;

            public Finalize(String s) {
                this.s = s;
            }

            @Override
            public void finalize() {
                System.out.println("finalize " + s);
            }
        }

        public static class StaticInitializer {
            static HashMap<Integer,Integer> map;
            static {
                map = new HashMap<>();
                // It would not be possibel to do this without the static initializer.
                map.put(1, -1);
                System.out.println("StaticInitializer");
            }
        }

        static class StaticField {
            static final int i = 1;

            // ERROR: already defined
            //int i = 2;

            int getI() {
                // No need to prefix it with anything.
                return i;
            }
        }

        static class ExceptionInInitializerErrorTest {
            static {
                if (true)
                    throw null;
            }
        }

        static class ExceptionInInitializerErrorFieldTest {
            static int i = f();
            static int f() {
                if (true)
                    throw null;
                return 0;
            }
        }

        // Field hiding

            static class FieldHideBase {
                final int i = 1;
                int getI() {
                    return i;
                }
            }

            static class FieldHideFail extends FieldHideBase {
                final int i = 2;
            }

            static class FieldHide extends FieldHideBase {
                final int i = 2;
                int getI() {
                    return i;
                }
            }

        // Interface

            interface EmptyInterface {}

            interface EmptyInterfaceDerived extends EmptyInterface{}

            interface EmptyGenericInterface<T> {}

            interface EmptyGenericInterfaceDerived<T> extends EmptyGenericInterface<T> {}

            interface InterfacePrivateMethod {
                //private void method();
            }

    // Annotation

        @interface EmptyAnnotation {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.TYPE)
        @interface MyAnnotation {
            public int i();
            public String s();
        }

        @Retention(RetentionPolicy.SOURCE)
        @interface RetentionSource {}

        @Retention(RetentionPolicy.RUNTIME)
        @interface RetentionRuntime {}

        @Retention(RetentionPolicy.CLASS)
        @interface RetentionClass {}

    // Enum

        public enum SimpleEnum {
            A,
            B,
            C
        }

        //public enum EnumPublicConstructor {
            //;
            //public EnumPublicConstructor() {}
        //}

        public enum EnumPrivateConstructor {
            // TODO why is this required, but not for an enum with default constructor?
            ;
            private EnumPrivateConstructor() {}
        }

        public enum EnumImplicitPrivateConstructor {
            // Constructor is implicitly private.
            EnumImplicitPrivateConstructor() {}
        }

        public enum EnumConstructor {
            A(0),
            B(1),
            C()
            // THIS semicolon is required.
            ;
            private int i;
            EnumConstructor() { this.i = -1; }
            EnumConstructor(int i) { this.i = i; }
            public int getI() { return this.i; }
        }

    // Method

        static void returnVoidImplicit() {}

        static class IntWrapper {
            private int i;
            public IntWrapper(int i) {
                this.i = i;
            }
            public void increment() {
                this.i++;
            }
            public int intValue() {
                return this.i;
            }
        }

        static void intWrapperByRef(IntWrapper i) {
            i.increment();
        }

        static void intArrayByRef(int[] is) {
            is[0]++;
        }

        static void intClass0ByRefFail(Integer i) {
            // Only assigns the local `i` to a new object,
            // but does not change the original `i`.
            i = new Integer(i.intValue() + 1);
        }

        static int varargMethod(int... is) {
            // Vargars passes an array to the string.
            assert is.getClass() == int[].class;
            return is[0] + is[1];
        }

        // Override TODO

            //static class OverrideStatic {
                //public static int method() { return 0; }
            //}

            //static class OverrideStaticDerived extends OverrideStatic {
                //public static String method() { return null; }
            //}

        // Overload

            static int overloadMethod(int i) {
                return i;
            }

            static int overloadMethod(float f) {
                return (int)f;
            }

            // ERROR: Cannot differentiate by return value.
            //static float overloadMethod(int f) {
                //return (float)f;
            //}

        static int identity(int i) {
            return i;
        }

        static void unreachableAfterReturn() {
            return;
            // ERROR
            //assert true;
        }

        static int notAStatement() {
            return 0;
        }

    // Annotation

        //public @interface MyAnnotation {
        //}

        //MyAnnotation (doSomething="What to do")
        //public void mymethod() {
        //}

        public static class AnnotationOverrideBase {
            public int method() {
                return 0;
            }
        }

        public static class AnnotationOverride extends AnnotationOverrideBase {
            @Override
            public int method() {
                return 1;
            }

            // ERROR: does not override anything.
            //@Override
            public int method2() {
                return 1;
            }
        }

        public static class AnnotationDeprecated {
            @Deprecated
            public static int method() {
                return 0;
            }
        }

    /*
    # main

        The `main` function of the class is what is run if you run:

            javac Main.java
            java Main.class

        This works since you must have exactly one class per file.

        The only valid signatures are:

            public static void main(String [])
            public static void main(String...)

        TODO can args be null?

        - http://stackoverflow.com/questions/9605532/args-guaranteed-to-be-non-null
        - http://stackoverflow.com/questions/3868878/java-check-if-command-line-arguments-are-null
    */
    public static void main(String[] args) throws Throwable {

        /*
        # Variable scope

            Brackets are different than in C++.

            - like in C++, they prevent inner definitions from going outside
            - unlike in C++, they do *not* prevent outer definitions from going inside.
        */
        {
            int i = 0;

            {
                // ERROR: unlike C++, i already defined!
                //int i = 1;

                // ERROR: cannot find symbol i
                //i = 1;

                int i2 = 0;
            }

            // ERROR: cannot find symbol i2
            //i2 = 1;
        }

        /*
        # Identifiers
        */
        {
            /*
            # Valid identifier characters

                http://stackoverflow.com/questions/11774099/legal-identifiers-in-java

                http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.8

                The valid characters are defined by those for which:

                    Character.isJavaIdentifierStart(int)

                is `true`. http://docs.oracle.com/javase/7/docs/api/java/lang/Character.html#isJavaIdentifierPart%28int%29

                It is not clear to me if the API description there is precise or not,
                it just mentions generic character sets like "combining mark" and non-spacing mark.

                Actual JVM restrictions are much less strict: any byte sequence that does not contain
                `; [ / < > :` is valid: http://stackoverflow.com/questions/26791204/why-does-the-jvm-allow-us-to-name-a-function-starting-with-a-digit-in-bytecode
            */
            {
                /*
                # Dollar in identifiers

                # $

                    The only weird character mentioned explicitly by the JLS is `$`.
                    Sounds like... JavaScript and mercenary money makers.

                    However the JLS 7 3.8 says:

                    > The $ character should be used only in mechanically generated source code or,
                    > rarely, to access pre-existing names on legacy systems

                    and it is easy to cause name conflicts if you do that.

                    So never ever use it.
                */
                {
                    int $ = 0;
                    assert $ == 0;
                }
            }

            {
                int i = 0, j = 1;
                assert i == 0;
                assert j == 1;
            }

            /*
            # Maximum identifier length

                http://stackoverflow.com/questions/695951/max-name-length-of-variable-or-method-in-java

                None basically.
            */
        }

        /*
        # Primitive types

            JLS7 4.

            A limited number of types that are not objects.

            They make coding in Java harder, but are important for performance:

            - byte: 8 bit
            - short: 16 bit
            - int: 32 bit
            - long: 64 bit
            - float: 32 bit
            - double 64 bit
            - boolean
            - char: Unicode

            All the types have bytecode instructions that differentiate between them,
            except for boolean.

            `boolean` only matters to disambiguate method signatures,
            where it has the identifier `Z`.

            Types are also classified as:

            # IntegralType

                byte, short, int, long, char.

                All 2's complement.

            # FloatingPointType

                float, double.

                IEEE 754.

            # NumericType

                Either Integral and Floating point types.
        */
        {
            /*
            # Definite assignment

                Unlike C and C++, many types are initialized automatically
                and deterministically to simple "falseish" values like `null`, `0` and `false`.

                This type of compiler behavior is called:
                http://en.wikipedia.org/wiki/Definite_assignment_analysis

                It is mathematically not possible to do it "perfectly" TODO what does that mean exactly?
                but Java has complex and generally good heuristics that do it,
                that take up an entire chapter of the JSL:
                http://docs.oracle.com/javase/specs/jls/se7/html/jls-16.html

                The JSL specifies recusivelly and precisely what happens to unassigned values
                through every possible statment of the language.
            */
            {
                // Using an uninitialized local variable is a compile-time error:
                // int i;
                // assert i == 0;

                // Same for static final fields: TODO example

                // Non-static fields on the other hand are default initialized.
                // TODO why different from local variables and final fields
                // http://stackoverflow.com/questions/268814/uninitialized-variables-and-members-in-java
                assert publicStaticInt == 0;

                // Branching statments: variables are only considered initialized
                // if they get set on all possible branches.
            }

            /*
            # int
            */
            {
                // # int literals
                {
                    // Underscores can be used anywhere inside integers
                    // to clarify them.
                    assert 1_2 == 12;
                    assert 1__2 == 12;

                    // If they are to be used, the sanest notation is to split
                    // decimals by 3 characters, and hex by 4.
                    assert 1_234 == 1234;
                    assert 0x1234_abcd == 0x1234abcd;

                    // Binary literals exist.
                    assert 0b11 == 3;
                }

                // 32-bit 2's complement guaranteed.
                {
                    assert 0xFFFF_FFFF == -1;
                    assert Integer.SIZE == 32;
                    assert Integer.MAX_VALUE == 0x7FFF_FFFF;

                    // ERROR: integer number too large, unlike some of the more "dynamic languages"
                    // like Python that do conversion automatically. In Java, you need an explicit BigInteger.
                    //assert 0xFFFF_FFFF_F;
                }
            }

            /*
            # char

                http://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.2

                Unicode character, guaranteed 16-bit.
            */
            {
                assert 0x100 == ((int)'Ā');

                // Backslash escapes can be used.
                assert 0xFFFF == ((int)'\uFFFF');

                /*
                4 byte UTF-16 characters need 2 chars.

                \ u literals can only go up to FFFF.

                You need two \ u escapes for the 4 byte characters.

                Some string methods consider 4-byte characters, and others not. See `charAt`.
                */
                String s = "\uFFFF\uFFFF";
            }

            /*
            # byte literals

                Do not exist: http://stackoverflow.com/questions/5193883/how-do-you-specify-a-byte-literal-in-java
                Downcast `int`.
            */

            /*
            # double literals

            # float literals
            */
            {
                // Without suffix, `D` for double is assumed.
                // The `D` suffix does not exist in ANSI C,
                // but it does as a GNU extension.
                assert Double.class.isInstance(1.2);
                assert Double.class.isInstance(1.2d);
                assert Double.class.isInstance(1.2D);

                // To make floats, use `f`:
                assert Float.class.isInstance(1.2f);
                assert Float.class.isInstance(1.2F);

                // Unlike for C, you can add floating literal suffixes to otherwise integer literals
                // without the dot.
                //
                // This allows you to write floating literals
                // that are equal to integers faster: one character `D` instead of two `.0`.
                //
                // But of course, both of those don't work for hexadecimal,
                // where they could be ambiguous with the number itself.
                assert Float.class.isInstance(1f);
                assert Double.class.isInstance(1d);
                assert Integer.class.isInstance(0x1d);
            }

            /*
            # Casting convertion

                JLS7 5.5

            # Typecast

                The term is not used on the JLS, "casting conversion" is used instead.
            */
            {
                // float to int truncates.
                // Other functions can be done with Math
                {
                    assert ((int)1.9) == 1;

                    // ERROR: incompatible types.
                    //int i = 1.9;
                    int i = (int)1.9;
                }

                /*
                Object typecasts
                */
                {
                    /*
                    # Widening reference conversion

                        JLS7 5.1.5

                        Done implicitly already.

                    # Upcast

                        Informal term for widening type conversion. Not found on the JLS.
                    */
                    {
                        class Base {}
                        class Derived extends Base {}
                        { Base b = new Derived(); }
                        { Base b = (Base) new Derived(); }
                    }

                    /*
                    # Narrowing reference conversion

                        JLS7 5.1.6

                    # Downcast

                        Informal term for narrowing reference conversion. Not found on the JLS.
                    */
                    {
                        class Base {}
                        class Derived extends Base {}

                        // What can be done.
                        {
                            Base b = new Derived();
                            Derived d = (Derived)b;
                        }

                        /*
                        # ClassCastException
                        */
                        {
                            boolean fail = false;
                            try {
                                Derived d = (Derived) new Base();
                            } catch (ClassCastException e) {
                                fail = true;
                            }
                            assert fail;
                        }

                        // ERROR: Incompatible types.
                        //{ Derived d = new Base(); }

                        // ERROR: Incompatible types.
                        //{ Derived d = (Derived) new String(); }
                    }
                }
            }
        }

        /*
        # Operators
        */
        {
            /*
            # Shift operators

            # >>>

            # Signed shift operator

                Much like C, except that:

                - negative LHS shift operations have defined values
                - `>>` is a keep-sign shift, and `>>>` is a put 0 shift
            */
            {
                assert (-2  >> 1) == -1;
                assert (0x80_00_00_00  >> 1) == 0xC0_00_00_00;
                assert (0x80_00_00_00 >>> 1) == 0x40_00_00_00;
            }

            /*
            # Comma operator

                Does not exist in Java: <http://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.27>
            */

            /*
            # Unary plus

                What is it good for, since there is no operator orelvoad:
                http://stackoverflow.com/questions/2624410/what-is-the-purpose-of-javas-unary-plus-operator
            */

            /*
            # Compound operators
            */
            {
                /*
                `a += b` is not exactly the same as `a = a + b`,
                but rather `a = (typeof a)(a + b)`.

                http://stackoverflow.com/questions/8710619/java-operator
                */
                {
                    int i = 1;
                    long j = 2;

                    i += j;

                    // ERROR: possible loss of precision.
                    //i = i + j;
                }
            }

            /*
            # Increment operator

            # ++
            */
            {
                /*
                Unlike in C the following are defined:
                all side effects of the evaluated expression happen before the assignment.
                */
                {
                    {
                        int i = 0;
                        i = i++;
                        assert i == 0;
                    }

                    // Because a = b++ equals
                    //
                    //   tmp = b;
                    //   b = b + 1;
                    //   a = tmp;
                    //
                    // So:
                    //
                    //   tmp = i;
                    //   i = i + 1;
                    //   i = tmp;

                    {
                        int i = 0;
                        i = ++i;
                        assert i == 1;
                    }
                }
            }

            /*
            # Bitwise operators
            */
            {
                /*
                For booleans, the bitwise operators are a bit magic and intuitive,
                and works just like && or || but without short circuiting.

                http://stackoverflow.com/questions/9264897/reason-for-the-exsistance-of-non-short-circuit-logical-operators

                So it all behaves as if booleans were a single bit.
                */
            }

            /*
            # Widening conversions

                http://vanillajava.blogspot.fr/2015/02/inconsistent-operation-widen-rules-in.html

                They might really surprise you, so watch out.
            */

            /*
            # Assignemnt operator
            */
            {
                int i, j;
                i = j = 1;
                assert i == 1;
                assert j == 1;
            }
        }

        /*
        # final variable modifier

            Similar to C++ `&`.

            References can be assigned only once.

            If an object member variable, can only be set on constructor once.
        */
        {
            // For base types, the value cannot be changed.
            {
                final int i = 0;
                assert i == 0;
                // ERROR:
                //i = 1;
                //i++;
            }

            // The initialization does not need to be immediate at declaration.
            // This allows for example to initialize `final` fields in a constructor.
            {
                final int i;
                i = 0;
                assert i == 0;
                // ERROR:
                //i = 1;
            }

            /*
            # final objects

                You *can* change the data of final objects.

                What you *cannot* change whant a symbol points to.
            */

            {
                final Class0 c = new Class0(0);
                c.setI(1);
                // ERROR:
                //c = new Class0(0);
            }

            /*
            # Non-static final member primitive field are useless

                Are useless because you cannot change them:
                so you might as well have one per class instead of one per instance.

                Same for immutable objects.
            */

            /*
            # final instance fields

                Can only be set on the constructor.
            */
        }

        /*
        # Conversions and promotions

            JLS7 5

            The type of expressions can always be deduced,
            and Java does a number of conversions automatically
            to match the required types.
        */
        {
            /*
            # Boxing conversion

                JLS7 5.1.7
            */
            {
                // On assignment
                {
                    int i = 1;
                    Integer j;
                    // Here.
                    j = i;
                    assert j.intValue() == 1;
                }

                /*
                On operators.
                <http://stackoverflow.com/questions/9391569/operation-inside-when-we-add-two-integer-objects>

                Bytecode will be the exact same as the full `getValue` expansion.
                */
                {
                    assert new Integer(1) + new Integer(2) == 3;
                }

                // Works with upcast as well.
                {
                    Object o = 1;
                    assert o instanceof Integer;
                }

                // Method call.
                // TODO converted or not?
                {
                    // ERROR no suitable constructor
                    //new Integer(1L);

                    // ERROR lossy conversion
                    //identity(1L);

                    new Integer((short)1);
                    identity((short)1);
                }
            }

            /*
            # Unboxing conversion

                JLS7 5.1.8

                From the primtive wrapper types to the primitives.
            */
            {
                Integer i = new Integer(1);
                int j;
                // Here
                j = i;
                assert j == 1;
            }

            /*
            # String conversion

                Any type can be converted to String, but the conversion is only done for the `+` operator.

                The primitive types are first boxed, then:

                - null returns "null"
                - other classes call `toString()`
            */
            {
                assert "1" + 1 == "11";
                assert 1 + "1" == "11";

                // ERROR: incompatible types because the conversion was not done by `+`.
                //String s = 1;
            }

            /*
            # auto C++

            # Type inference

                Nope. TODO why?

                http://stackoverflow.com/questions/16132759/is-there-auto-type-infering-in-java

                But generic methods are able to determine them from arguments.
            */
        }


        /*
        # Branching statements

        # Conditional statements
        */
        {
            /*
            # goto

                Nope:

                - http://stackoverflow.com/questions/2545103/is-there-a-goto-statement-in-java
                - http://stackoverflow.com/questions/2430782/alternative-to-a-goto-statement-in-java

                Harmful C / C++ features were not included.

                But the labeled break and continue can be used to emulate it pretty well.

                bytecode does have GOTO instruction.
            */

            /*
            # if
            */
            {
                /*
                Only true and false can be used, nothing else. Sanity at last.

                ERROR: XXX cannot be converted to boolean.
                */
                //if (0) { assert false; }
                //if (null) { assert false; }
            }

            /*
            # switch

            # case
            */
            {
                {
                    int i = 0;
                    switch (i) {
                        case 0:
                            assert i == 0;
                        break;
                        case 1:
                            assert i == 1;
                        break;
                        case 2:
                            assert i == 2;
                        break;
                    }
                }

                // Only works for String objects which are magic starting in Java 7:
                // http://stackoverflow.com/questions/4354662/possible-to-have-switchjava-lang-object-in-java-updated
                // Project Coin proposal.
                {
                    String s = "s0";
                    switch (s) {
                        case "s0":
                            assert s == "s0";
                        break;
                        case "s1":
                            assert s == "s1";
                        break;
                    }

                    final Object o = new Object();
                    // ERROR: incompatible types.
                    //switch (new Object()) {
                        // ERROR: constant expression expected
                        //case o:
                        //break;
                    //}
                }

                /*
                # switch and enum

                    You must `case A`, and not `case E.A`!

                    Very practical and magic.
                */
                {
                    SimpleEnum e = SimpleEnum.A;
                    switch (e) {
                        case A:
                            assert e == SimpleEnum.A;
                        break;
                        case B:
                            assert e == SimpleEnum.B;
                        break;
                        // ERROR
                        //case SimpleEnum.C:
                            //assert e == SimpleEnum.B;
                        //break;
                    }
                }
            }

        }

        // # for loop
        {
            /*
            # for-each loop

                Informal name for the enhanced for statement.

            # Enhanced for statement

                Called `for-each` on the guide:
                http://docs.oracle.com/javase/8/docs/technotes/guides/language/foreach.html
                but only "enhanced for statement" on the JLS7 14.14.2

                Works with any class that implements Iterable,
                which is therefore a magic interface.
            */
            {
                ArrayList<Integer> a = new ArrayList<>();
                ArrayList<Integer> a2 = new ArrayList<>();
                a.add(1);
                a.add(2);

                // Why final can be used here:
                // http://stackoverflow.com/a/3911177/895245
                for (final int i : a) {
                    a2.add(i);
                }
                assert a.equals(a2);

                // Expanded equivalent.
                a2.clear();
                for (Iterator<Integer> it = a.iterator(); it.hasNext();) {
                    a2.add(it.next());
                }
                assert a.equals(a2);

                // NullPointerException
                //for (final int i : (ArrayList<Integer>)null) {}

                /*
                # Iterator

                # Iterable

                    TODO implement some examples.
                */
                {
                    /*
                    # Iterate all but last element

                        No "perfect" way: either `hasNext`, etc.

                        http://stackoverflow.com/questions/3105613/best-loop-idiom-for-special-casing-the-last-element 
                    */

                    /*
                    # Enumeration

                        http://docs.oracle.com/javase/7/docs/api/java/util/Enumeration.html

                        Deprecated for `Iterator`.
                    */

                    /*
                    # remove

                        Remove the last element that was iterated with `next()`.

                        You must:

                        - only call it at most once per next
                        - not call it before next

                        or else `IllegalStateException`.

                        This is the only way that a list may be modified while it is being iterated over.
                    */
                    {
                        Deque<Integer> l = new LinkedList<Integer>();
                        l.add(1);
                        l.add(2);
                        Iterator<Integer> it = l.iterator();
                        assert it.next() == 1;
                        it.remove();
                        assert l.pollFirst() == 2;
                        assert l.isEmpty();

                        /* TODO show Illegal state exception cases. */
                    }

                    /*
                    # Iterator insert

                    # Iterator update

                        TODO: how to insert and update an item while iterating over a list?

                        http://stackoverflow.com/questions/993025/java-adding-elements-to-a-collection-during-iteration

                        Not possible with Iterator, but is possible with ListIterator, which has add and set.

                        Like remove, those act on the last `next()`.
                    */
                }
            }

            /*
            # break statement

            # continue statement
            */
            {
                /*
                # Labeled break statement

                    Generates a new statement. The break then jumps to whatever comes after the label.

                    Don't use it as it is just a goto in desguise:
                    <http://stackoverflow.com/questions/14960419/is-using-a-labeled-break-a-good-practice-in-java>

                    In C, there there are no labeled gotos, this is considered by many style guides
                    to be one of the last valid uses of GOTO.

                    Others also consider any forward GOTOs acceptable: `break` can emulate it.
                    A labeled continue can be used to emulate a backwards GOTO jump.
                */
                {
                    int sum = 0;
                search:
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            sum += 10*i + j;
                            if (i == 2 && j == 2)
                                break search;
                        }
                    }
                    // Labeled break jumps to here.
                    assert sum == 22;
                }

                /*
                The labeled break statement can be used outside of loops and switch,
                giving us a forward-only GOTO statement.
                */
                {
                    // Basic example.
                    {
                        label: {
                            // if to avoid unreachable statement.
                            if (true)
                                break label;
                            assert false;
                        }
                    }


                    // Labels also have scope, so you can use them many times.
                    {
                        label: {
                            if (true)
                                break label;
                            assert false;
                        }
                    }

                    // ERROR: can't have unlabelled break statements outside loop or switch:
                    //break;

                    // ERROR: can't have labelled break statements
                    // that are not surrounded by the label
                    {
                        //break;
                    }

                    /*
                    # Labeled continue

                        Labelled continue must point to a label of a loop statement.
                    */
                    {
                        label: {
                            // ERROR: not a loop label
                            //continue label;
                        }

                        // But we can still emulate a backwards GOTO with a dummy while(true)
                        {
                            int i = 0;
                            label: do {
                                i++;
                                if (i == 2)
                                    break label;
                                continue label;
                            } while(true);
                            assert i == 2;
                        }
                    }

                    /*
                    And for the lolz, you can use URLs directly in your code.

                    This creates an `http:` label followed by a `//example.com` comment.
                    */
                    {
                        http://example.com
                        {}
                    }
                }
            }
        }

        /*
        # Statements
        */
        {
            /*
            # Not a statement

                Unlike in C, the following generates a compilation error in Java,
                while GCC only generates a warning and compiles to nothing.

                http://stackoverflow.com/questions/16599183/java-not-a-statement
            */
            {
                // ERROR
                //0;

                // OK
                // Although this does nothing, Java does not deeply inspect the method.
                // And method calls can have side effects.
                notAStatement();

                // && and || need an if or assignemnt to work.
                {
                    //notAStatement() && notAStatement();
                    boolean t = true && true;
                }

                /*
                # Empty statment

                    The empty statement however is valid, and serves as a good source of bugs.

                    Why is it allowed, considering that Java is picky about not a statment?
                    http://stackoverflow.com/questions/14112515/semicolon-at-end-of-if-statement
                */
                {
                    ;
                    ;

                    // WARN An empty if is the prototypical bug case.
                    // javac only warns us about it.
                    //if (true);
                }
            }

            /*
            # Unreachable statements

                Statements classified as unreachable *prevent compilation*:
                http://docs.oracle.com/javase/specs/jls/se7/html/jls-14.html#jls-14.21
                which determines for each statement what is considered unreachable or not.

                Discussions:

                - http://stackoverflow.com/questions/2280787/unreachable-code-error-or-warning
                - http://stackoverflow.com/questions/3795585/why-does-java-have-an-unreachable-statement-compiler-error
            */
            {
                // Unreachable.
                {
                    // while
                    {
                        // ERROR
                        //while (false) { assert true; }
                        //while (1 + 1 != 2) { assert true; }
                        //for (;false;) { assert true; }

                        // ERROR
                        //while(true) {}

                        // OK. Note how the rules are quite complicated,
                        // and consider if there is a break inside the infinite while.
                        while(true) { break; }

                        // OK: infinite loop.
                        // while(true) { if (false) break; }
                    }

                    // break and continue
                label:
                    {
                        break label;
                        // ERROR
                        //assert true;
                    }

                    // return
                    unreachableAfterReturn();

                    // Exception
                    {
                        try {
                            throw new Exception();
                            // ERROR
                            //assert true;
                        } catch(Exception e) {}
                        // The try catch makes what comes after it reachable as expected.
                        assert true;
                    }

                }

                // Reachable.
                {
                    /*
                    Unlike while, `if(false)` works.

                    Quoting JLS:

                    The rationale for this differing treatment is to allow programmers
                    to define "flag variables" such as:

                        static final boolean DEBUG = false;

                    and then write code such as:

                        if (DEBUG) { x=3; }
                    */
                    if (false) {
                        assert true;
                    }

                    //System.exit(0);
                    //assert true;
                }
            }

            /*
            # NOOP statement

                Statement that does nothing, usually a debugger breakpoint target:

                - http://stackoverflow.com/questions/2840941/system-diagnostics-debugger-break-like-using-java
                - http://stackoverflow.com/questions/10736275/is-there-asm-nop-equivalent-in-java
            */
            {
            }
        }

        // # Expressions
        {
            /*
            # Compile time constant expression

            # Constant expression

                Have certain implications: http://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.28
            */
        }

		/*
		# assert

			Built-in language statement.

			If fails, throws `AssertionError`.

			Only gets run if the `-ea` option is passed to `java` to run the class file.

            Vs exceptions:

            - http://programmers.stackexchange.com/questions/137158/is-it-better-to-use-assert-or-illegalargumentexception-for-required-method-param
		*/
		{
		    // Basic example
            {
                boolean fail = false;
                try {
                    assert false;
                } catch(AssertionError e) {
                    fail = true;
                }
                assert fail;
            }

            // Assert with colon.
            // Syntax that sets the error message.
            {
                String message = "";
                try {
                    assert false : "with colon";
                } catch(AssertionError e) {
                    message = e.getMessage();
                }
                System.out.println(message);
                //assert message == "with colon";
            }

            /*
            ClassLoader has a few assertion methods that can control if assert is evaluated at runtime:
            http://docs.oracle.com/javase/7/docs/api/java/lang/ClassLoader.html#setClassAssertionStatus%28java.lang.String,%20boolean%29

            Class has:
            http://docs.oracle.com/javase/7/docs/api/java/lang/Class.html#desiredAssertionStatus%28%29
            */
		}

        /*
        # Classes

            JLS 8
        */
        {
            /*
            # Access control

            # Visibility

                JLS7 6.6

                Keep the little table in mind:
                http://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html

                Java has 4 class scopes: one more than C++, which does not have package-private.

            # Public

            # Protected

            # Private

            # Package-private

                Package private is what you get when you have no scope qualifier.

                package-private can be seen from other classes on the same package.

                package-private cannot be set explicitly with some language keyword.

                In Java, being is the same package is a stronger relationship than inheritance!
            */
            {
                // TODO: is `i` is because we have package level from here?
                {
                    class Local {
                        int i;
                    }
                }
            }

            /*
            # Field
            */
            {
                /*
                # Field vs variable

                    The JLS uses *local variable* for variables defined inside functions.

                    The term variable is not very clear: it could mean either field or local variable, so avoid it.
                */

                /*
                # Override fields

                # Field hiding

                    http://stackoverflow.com/questions/685300/is-there-a-way-to-override-class-variables-in-java 

                    Fields cannot be called polymorhically like methods.

                    Fields in derived classes simply hide the field on the base.
                */
                {
                    assert new FieldHideFail().getI() == 1;
                    assert new FieldHide().getI() == 2;
                }

                /*
                # Field modifiers

                    JLS7 8.3.1

                    Some of the following modifiers have many uses in the language.

                    Only `final` can be used for local variales.
                */
                {
                    /*
                    # static field modifier

                        TODO Intuitively, one memory for the entire class, not per object.
                    */
                    {
                        // ERROR: illegal start of expression.
                        // Cannot be used for local variables.
                        //static int i;

                        // Use static field from member method.
                        {
                            assert new StaticField().getI() == 1;

                            // WARN static variable should be qualified by type name
                            assert (new StaticField()).i == 1;
                        }
                    }

                    /*
                    # volatile field modifier

                        TODO Something to do with multi-threading memory access.
                    */
                    {
                        // ERROR: illegal start of expression.
                        // Cannot be used for local variables.
                        //volaile int i;
                    }

                    /*
                    # transient field modifier

                        Fields marked as transient are not serialized.

                        The language specification does not specify any extra behavior to them,
                        except reflection detection via `getField`.

                        `java.io.Serializable` uses it, probably through `getField`,
                        and only serializes fields that don't have it.

                        Transient information is stored in a byte of the bytecode:
                        https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.5-200-A.1

                    # Externalizable

                        TODO
                    */
                    {
                        // ERROR: illegal start of expression.
                        // Cannot be used for local variables.
                        //transient int i;

                        assert  Modifier.isTransient(TransientField.class.getField("ti").getModifiers());
                        assert !Modifier.isTransient(TransientField.class.getField("i").getModifiers());
                    }

                    /*
                    # Serializable

                        http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html

                        Serialization is transforming objects into `byte[]`.

                        Built-into Java, which can do it automatically for objects with primitive fields.

                        Serializable is only a marker interface (without any methods):
                        http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
                        that tells Java: Hey, you can serialize me.

                        Only only need to implent:

                            private void writeObject(java.io.ObjectOutputStream out)
                                throws IOException
                            private void readObject(java.io.ObjectInputStream in)
                                throws IOException, ClassNotFoundException;
                            private void readObjectNoData()
                                throws ObjectStreamException;

                        if you want to customize serialization. TODO how exactly?

                        But usually the only thing we need to do is to omit redundant caches,
                        which can be done with `transient`.

                        Serialization always acts throgh `ObjectOutputStream`.

                        Serialization is specified at:
                        http://docs.oracle.com/javase/8/docs/platform/serialization/spec/serialTOC.html

                        There are alternatives to the the default serialization,
                        notably JAXB which overcome some of it's problems.

                        TODO The following may break serialized data:

                        - http://docs.oracle.com/javase/8/docs/platform/serialization/spec/version.html
                        - http://stackoverflow.com/questions/4053359/what-changes-can-make-serialized-class-versions-incompatible

                    # ObjectOutputStream

                    # ObjectInputStream

                    # serialVersionUID
                    */
                    {
                        TwoInts i = new TwoInts(1, 2);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(baos);
                        oos.writeObject(i);
                        byte[] serialized = baos.toByteArray();
                        oos.close();

                        System.out.println("# Serializable = " + DatatypeConverter.printHexBinary(serialized));

                        ByteArrayInputStream bais = new ByteArrayInputStream(serialized);
                        ObjectInputStream ois = new ObjectInputStream(bais);
                        assert ((TwoInts)ois.readObject()).equals(i);
                        ois.close();
                    }

                    /*
                    # Serializable and inheritance

                        All base classes must also be serializable, or NotSerializableException.
                    */
                }
            }

            /*
            # Class class

                http://docs.oracle.com/javase/7/docs/api/java/lang/Class.html

                `Class` is also an object of class `Class`
                which contains metainformation on a class.

                It can be used for reflection: can get information such as:
                Name, Superclass, fields, Enclosing class, etc.
                <http://docs.oracle.com/javase/tutorial/reflect/class/index.html>
            */
            {
                {
                    Class<?> cs = "".getClass();
                    // TODO why not just `String`? Why the `.class`?
                    assert cs == String.class;
                }

                {
                    // TODO why incompatible?
                    //Class<String> cs = "".getClass();
                }

                // # getSuperclass
                {
                    class Base {}
                    class Derived extends Base {}
                    assert Base.class.getSuperclass() == Object.class;
                    assert Derived.class.getSuperclass() == Base.class;
                }

                // # isLocalClass
                {
                    class Local {}
                    assert Local.class.isLocalClass();
                }

                // # isAnonymousClass
                {
                    assert (new Empty(){}).getClass().isAnonymousClass();
                }

                /*
                # isInstance

                    See also: `instanceof` operator.
                */
                {
                    assert Empty.class.isInstance(new Empty());
                }

                /*
                # getName

                # getSimpleName

                # getCanonicalName

                    TODO
                */
                {
                    class Class {}
                    // package.Main$1Class
                    System.out.println("getName() = " + Class.class.getName());
                    System.out.println("getCanonicalName() = " + Class.class.getCanonicalName());
                    assert Class.class.getSimpleName().equals("Class");
                }

                /*
                # getClassLoader

                # ClassLoader

                    TODO
                */
            }

            /*
            # instanceof operator

                Takes the name of the class instead of `Class.class`

                See also: `Class#isInstance` TODO: vs.
                <http://stackoverflow.com/questions/18575408/what-does-it-mean-isinstance-is-a-dynamic-equivalent-of-instanceof>
            */
            {
                class Local implements EmptyInterface {}
                Local l = new Local();
                assert l instanceof Local;
                assert l instanceof Object;
                assert l instanceof EmptyInterface;

                // Null is not an instance of anything.
                assert !(null instanceof Object);

                // ERROR primitives cannot be passed to `instanceof`.
                //assert !(1 instanceof Object);
            }

            /*
            # Methods

            # Function

                There are no real global functions, only methods.
            */
            {
                // # return
                {
                    /*
                    # Return multiple values

                        Not supported: <http://stackoverflow.com/questions/457629/how-to-return-multiple-objects-from-a-java-method>

                        There seems to be no concept of tuple as in python: <http://stackoverflow.com/questions/2670982/using-tuples-in-java>

                        Best solution: create a class that wraps all the return values.
                    */

                    /*
                    A JVM `return` instruction is automatically added to `void` methods
                    even if you don't add a `return` statement.
                    */
                    {
                        returnVoidImplicit();
                    }
                }

                /*
                # Pass by references

                    You can only pass classes by reference.

                    You can only base types by value.

                # Pass base types by reference

                    There is no neat way of changing a base type on a function by reference as in C++.

                    The best workarounds are:

                    - create a warpper class yourself
                    - pass an array
                */
                {
                    /*
                    Create a integer wrapper workaround.

                    Lots of boilerplate
                    */
                    {
                        int i = 0;
                        IntWrapper iw = new IntWrapper(i);
                        intWrapperByRef(iw);
                        i = iw.intValue();
                        assert i == 1;
                    }

                    /*
                    Array workaround.

                    Works but is horrible.
                    */
                    {
                        int i = 0;
                        int[] is = {i};
                        intArrayByRef(is);
                        i = is[0];
                        assert i == 1;
                    }

                    // Integer class is immutable, so this approach fails.
                    {
                        int i = 0;
                        Integer iObj = new Integer(i);
                        intClass0ByRefFail(iObj);
                        i = iObj.intValue();
                        assert i == 0;
                    }
                }

                /*
                # vararg
                */
                {
                    // Can take either separate parameters, or Arrays.
                    {
                        assert varargMethod(1, 2) == 3;
                        assert varargMethod(new int[]{1, 2}) == 3;
                    }
                }

                // # Method modifiers
                {
                    /*
                    # strictfp method

                    # FP-strict

                        Method evaluation is FP-scrict.

                        This means that implementations must follow IEEE 754.

                        Without this, what implementations could do is to allow greater precision
                        to intermediate results, thus potentially giving results more precise
                        than IEEE 754, but with undetermined value.
                    */

                    /*
                    # native method

                        Implemented as a dynamically loaded library that follows the JNI conventions,
                        often generated from a C source.
                    */

                    /*
                    # final method

                        Like for classes, means the method cannot be overriden.

                        TODO applications?
                    */
                    {
                        class Base {
                            void method() {}
                            final void finalMethod() {}
                        }

                        class Derived extends Base {
                            void method() {}
                            // ERROR
                            //void finalMethod() {}
                        }
                    }
                }

                // # Overload
                {
                    // TODO create example that overload fails with generics

                    // TODO check out overload derivation rules.
                    // Show some ambiguous references, e.g. null to objects, int to int / Integer.
                    // http://stackoverflow.com/questions/14053596/compiler-error-reference-to-call-ambiguous

                    /*
                    # Operator overloading

                        Not possible in Java.

                        <http://stackoverflow.com/questions/1686699/operator-overloading-in-java>

                        Technically speaking, the `String` class is magic and has special behavior for `+`
                        which is operator overloading, but you can't define your own.
                    */
                }

                /*
                # Argument expression order of evaluation

                    http://stackoverflow.com/questions/2201688/order-of-execution-of-parameters-guarantees-in-java

                    Always left to right. TODO example.
                */

                /*
                # Local method

                # Declare one method inside another

                    Not possible:
                    <http://stackoverflow.com/questions/4735922/in-java-can-a-method-constructor-declaration-appear-inside-another-method-const>

                    Also note that local classes cannot have static fields,
                    which would be a possible workaround.
                */

                /*
                # Getters and setters

                    Generate getters and setters automatically through reflection:
                    not possible built-in:
                    http://stackoverflow.com/questions/1907312/java-getters-and-setters
                */

                /*
                # Polymorphism

                    This word is not used in the JSL? Only overriding.

                # Method overriding

                # Override methods
                */
                {
                    /*
                    # Polymorphic static method

                        Not possible. TODO alternatives?

                        TODO why can't overriden method return a different typet
                    */
                    {
                        // OverrideStatic

                        /*
                        The return type of static methods must be the same on derived classes.

                        TODO why, considering that there is no method override, only hiding?
                        http://stackoverflow.com/questions/9439379/why-does-java-enforce-return-type-compatibility-for-overridden-static-methods
                        */
                    }

                    /*

                    # Add throws to derived method

                        http://stackoverflow.com/questions/5875414/method-overriding-and-exceptions

                        Not possible. You can only throw a class derived from the declaration of the superclass.

                        Otherwise, polymorphism wouldn't work.
                    */
                }
            }

            /*
            # Initializer

                Two types: static and non static.
            */
            {
                /*
                # Instance initializer

                    http://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.6

                    Code that gets run before any constructor runs.

                    Can access `this`.

                    TODO: vs constructors?
                    http://stackoverflow.com/questions/1355810/how-is-an-instance-initializer-different-from-a-constructor
                */
                {
                    // Basic example
                    {
                        class Class {
                            public int i;

                            // The intance initializer.
                            {
                                this.i = 1;
                            }

                            public Class() {
                                this.i *= -1;
                            }

                            public Class(int j) {
                                this.i *= j;
                            }
                        }
                        assert new Class().i == -1;
                        assert new Class(2).i == 2;
                    }

                    /*
                    Most common occurence is as syntatic sugar to iniailize Collections,
                    often called "double brace initialization".
                    */

                    /*
                    # Initializer must be able to complete normally

                        If an initializer is sure to throw, that is a compile time error.
                    */
                    {
                        class Class {
                            {
                                // ERROR
                                //throw null;
                            }
                        }
                    }
                }

                /*
                # Static block

                    The formal name is *static initializer*.

                # Static initializer

                    http://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.6

                    http://stackoverflow.com/questions/2943556/static-block-in-java

                    Thing like

                        static {
                            ...
                        }

                    and:

                        static int i = ...;

                    inside a class.

                    See also: instance initializer.

                    Basically a constructor for static methods.

                    Guaranteed to only gets run when the class is "used" as defined by 
                    http://docs.oracle.com/javase/specs/jls/se7/html/jls-12.html#jls-12.4.1
                    http://stackoverflow.com/a/3499322/895245
                */
                {
                    System.out.println("StaticInitializer before usage");
                    assert StaticInitializer.map.get(1) == -1;
                    System.out.println("StaticInitializer after usage");

                    /*
                    # ExceptionInInitializerError

                        If a static initializer throws,
                        Java automatically catches and rethrows as ExceptionInInitializerError.
                    */
                    {
                        try {
                            new ExceptionInInitializerErrorTest();
                        } catch(ExceptionInInitializerError e) {}

                        try {
                            new ExceptionInInitializerErrorFieldTest();
                        } catch(ExceptionInInitializerError e) {}
                    }
                }
            }


            /*
            # Destructor

                Nope, because Java has garbage collection:
                http://stackoverflow.com/questions/171952/is-there-a-destructor-for-java

                But there is `Object#finalize`, which is likely not what you are looking for.
            */

            // # Inheritance
            {
                /*
                # Multiple inheritance

                    Nope.

                    Only multiple interface implementation like in Ruby and C#.
                */

                /*
                # this

                    Current instance.

                    Concept exists in bytecode, which enforces passing `this`
                    as the first parameter to member methods.
                */
                {
                    /*
                    final...
                    */
                    {
                        class Class {
                            void method() {
                                // ERROR
                                // this = null;
                            }
                        }
                    }

                    /*
                    # this == null

                        Apparently cannot be null:
                        http://stackoverflow.com/questions/3789528/can-this-ever-be-null-in-java
                    */

                    /*
                    # Call one constructror from another

                    # this()
                    */
                    {
                        /*
                        `this()` for another of current class, `super()` for a constructor of a base class.

                        Both need to be the first statement in a constructor or you get a compilation error.
                        Therefore either can only be done once.

                        If the first statement of a constructor is not a call to another constructor,
                        it is as if the default constructor `Base#Base()` were called.
                        If it is not defined, an error occurs. The base class does not have the default constructor,
                        then you are forced to make an explicit `super` call on your constructor to an existing constructor:
                        a base constructor must always be called. TODO Example.
                        */
                        {
                            // TODO finish examples.

                            class Base {
                                public int i;

                                public Base() {}

                                public Base(int i) {}
                            }

                            class Class extends Base {
                                public Class() {
                                    this.i = 0;
                                }

                                public Class(int i) {
                                    this.i = 1;
                                }

                                public Class(int i, int j) {
                                    this.i = 1;
                                    // ERROR: call to this must be the first statement
                                    //this();

                                    // ERROR: call to super must be the first statement
                                }
                            }
                        }

                        /*
                        If a cross constructor call is specified,
                        then its parameters contain the only expressions that are evaluated before
                        the called constructor.

                        Therefore, those expressions cannot involve instance variables or methods,
                        or you will get an error.
                        */
                        {
                            class Class {

                                public int i;

                                public Class() {
                                    // 1+1 is evaluated *before* calling the other constructor.
                                    this(1 + 1);

                                    // Everything now is evaluated after.
                                    assert this.i == 2;
                                }

                                public Class(int i) {
                                    this.i = i;
                                }

                                public Class(int i, int j) {
                                    // ERROR: cannot reference instance methoe before supertype constructor.
                                    //this(this.method());
                                }

                                public int method() { return 0; }
                            }

                            new Class();
                        }

                        /*
                        # Recursive constructor invocation

                            Not possible as it would always lead to an infinite loop.
                        */
                        {
                            {
                                class Class {
                                    Class() {
                                        //this();
                                    }
                                }
                            }

                            // And Java checks for any circular construction as well.
                            // Not fun.
                            {
                                class Class {
                                    Class() {
                                        this(1);
                                    }

                                    Class(int i) {
                                        // ERROR
                                        //this();
                                    }
                                }
                            }
                        }
                    }
                }

                /*
                # final class modifier

                    Prevents the class from being inherited.
                */
                {
                    // Basic example
                    {
                        final class Base {}
                        // ERROR: Cannot inherit from final
                        //class Derived extends Base {}
                    }

                    /*
                    Applications:
                    http://stackoverflow.com/questions/5181578/use-of-final-class-in-java

                    Prevent API break when adding a new method.

                    If someone had derived your class and you add a new method, it braeks your API,
                    because that person might have overriden the method.
                    */
                    {
                        class Base {}
                        class Derived extends Base {
                            int method() { return 0; }
                        }

                        // But if it becomes:

                            //class Base {
                                //String method() { return null; }
                            //}

                        // Then Derived breaks.

                    }
                }
            }

            /*
            # Interface
            */
            {
                // Cannot have private methods:
                // they would be useless, except for reflection.
                {
                    InterfacePrivateMethod i;
                }

                // The protected and public method modifiers are acceptable but useless:
                // every method is meant to be overriden and thus at least protected.
                // TODO example

                // Every field is static and final. Those field modifiers can be added,
                // but they have no effect, and thus should be avoided.
                // TODO example

                // Nested interfaces are always static, since interfaces don't contain state.
                // The static modifier can be used explicitly, but it has no effect.
                // TODO example

                // Implement interface multiple times.
                {
                    // ERROR repeated interface
                    //{ class Class implements EmptyInterface, EmptyInterface {} }

                    // But this is fine:
                    { class Class implements EmptyInterface, EmptyInterfaceDerived {} }

                    // ERROR cannot be derived with different arguments
                    //{ class Class implements EmptyGenericInterface<Integer>, EmptyGenericInterfaceDerived<Double> {} }
                }
            }

        }

        /*
        # Enum

            http://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.9

            Enums in Java are very close to classes.

            They can even have methods, and occupy a file with their name like a class.

            This is in great contrast where they are just integers.
        */
        {
            /*
            Enums are implicily like static classes.

            So they can't be local or nested inside a non static class.

            http://stackoverflow.com/questions/700831/why-cant-enums-be-declared-locally-in-a-method
            http://stackoverflow.com/questions/14858036/why-cant-i-create-an-enum-in-an-inner-class-in-java
            */
            //enum Elocal { A, B };

            /*
            Enum values are not primitives but rather
            http://docs.oracle.com/javase/7/docs/api/java/lang/Enum.html
            */
            {
                assert SimpleEnum.A.getClass().getSuperclass() == Enum.class;

                /*
                But it also gets some methods automatically generated by the compiler:
                http://stackoverflow.com/questions/13659217/values-method-of-enum
                e.g. values
                */
            }

            // Guaranteed to be different objects.
            {
                assert SimpleEnum.A != SimpleEnum.B;
            }

            /*
            # values enum

                Get a list of all values.

                Guaranteed by JLS to be in the same order as declared.
            */
            {
                assert Arrays.equals(
                    SimpleEnum.values(),
                    new SimpleEnum[]{SimpleEnum.A, SimpleEnum.B, SimpleEnum.C}
                );
            }

            assert SimpleEnum.A.name().equals("A");
            assert SimpleEnum.A.ordinal() == 0;

            /*
            # Enum constructor

                Enum constructors cannot be protected or public.

                They can only be used directly to initialize the enum "fields".
            */
            {
                assert EnumConstructor.A.getI() == 0;
                assert EnumConstructor.B.getI() == 1;
            }
        }

        /*
        # Object

            JLS7 4.3.1

            Object can refer to either:

            - a language concept
            - the Object class: <http://docs.oracle.com/javase/7/docs/api/java/lang/Object.html>

            An object is a class instance or an array.

            The Object class is the superclass of all classes.

            Whenever you create a class without a base class, `Object` is automatically added as a base class.
        */
        {
            // Basic creation.
            {
                Class0 c = new Class0(0);
                assert c.method() == 0;
            }

            /*
            # clone

                protected method.

                Generally a bad idea, use copy constructors instead: TODO why
                <http://stackoverflow.com/questions/2326758/how-to-properly-override-clone-method>

                The recommended contract is that:

                    x.clone() != x
                    x.clone().getClass() == x.getClass()
                    x.clone().equals(x)

                No magic is done for descendant classes:
                you must explicitly override it,

                TODO: is there any stdlib class that implements it?

                - Collections don't, but have the copy constructor.
                - Arrays implement it, and Arrays.copyOf can also be used. Both do shallow copies.
                - Primitive wrappers don't implement it

            # clone vs copy constructor vs factory

                <http://stackoverflow.com/questions/1106102/clone-vs-copy-constructor-vs-factory-method>

            # Why clone returns Object and not <? extends Class>

                Backwards compatibility:
                http://stackoverflow.com/questions/17509659/why-standard-java-classess-clone-return-object-instead-of-actual-type
            */
            {
            }

            /*
            # getClass

                Get `Class` from object.
            */
            {
                assert (new Empty()).getClass() == Empty.class;
            }

            /*
            # Assignment operator for objects

                Assignment for classes makes the old reference point to the new class.

                Unlike C++, where assignment does not copy data.

                You can get a shallow copy with `clone()` Object method.
            */
            {
                Class0 c0 = new Class0(0);
                Class0 c1 = new Class0(1);
                c0 = c1;
                assert c0.getI() == 1;
                c0.setI(2);
                assert c1.getI() == 2;
            }

            /*
            # equals for Object

                Same behaviour as `==`: reference address comparison.

                If however classes rely on / override `equals`,
                it is generally expected to implement some sort of content comparison.

                Note that Java has no operator overloading, so `==`
                on derived classes will still always compare addresses like for object.
            */
            {
                Class0 c0 = new Class0(0);
                Class0 c1 = new Class0(0);
                assert ! c0.equals(c1);
            }

            /*
            # == for Object

            # Equality operator for objects

                The equality operator checks if two objects have the same address,
                it does *not* check member data equality.

                You can get member by member compairison by using `equals()` Object method.

                `Object` stipulates that `equals` must have the usual 3 mathematical properties (reflexive, symmetric and transitive) ,
                plus 2 programming specific ones: consistence (not random) and difference from `null`.
            */
            {
                {
                    assert new Integer(0) != new Integer(0);
                    assert (new Integer(0)).equals(new Integer(0));
                }

                // The actual class does not matter for equality.
                {
                    Integer i = 0;
                    Object j = i;
                    assert i == j;
                }

                /*
                Although `==` is generaly more specific than `equals` and generally implies it,
                the implication is not certain.
                */
                {
                    // Auto-conversion insanities:
                    // <http://stackoverflow.com/questions/1259693/why-are-these-but-not-equals>
                    {
                        short s = 1;
                        Integer I = 1;

                        // TODO check
                        // 1) == implies the automatic unboxing
                        // 2) primitive conversion then makes them equal
                        assert  I == s;

                        // Regular method call:
                        // 1) s is boxed to Short
                        // 2) Short and Long are never equal.
                        assert !I.equals(s);

                        // Same as above but expanded.
                        assert !(new Short((short)1)).equals(new Integer(1));
                    }
                }
            }

            /*
            # finalize

                Called when the object is garbage collected.

                It is not possible to force garbage collection
                <http://stackoverflow.com/questions/1481178/forcing-garbage-collection-in-java>

                This method may be useful if you are using your own custom garbage collection
                with off-heap memory.
            */
            {
                Finalize f = new Finalize("f");
                f = null;
                System.gc();

                // TODO why is it not printed without the program ends?
                Finalize f2 = new Finalize("f2");
            }

            /*
            # toString

                Default way to convert an object to a String.

                Used by `println`.

                For objects, only prints the instance ID.

                Not very helpful by default on most JCL.

                For arrays, there is `Arrays#toString`.

                For collections
                http://stackoverflow.com/questions/395401/printing-java-collections-nicely-tostring-doesnt-return-pretty-output

                Maps have pretty print from `AbstractMap` (remember that maps are not collections):
                http://docs.oracle.com/javase/7/docs/api/java/util/AbstractMap.html#toString%28%29

                # How to generate a toString automatically that shows the object's fields:

                    http://stackoverflow.com/questions/1526826/printing-all-variables-value-from-a-class

                    Either:

                    - a 15 liner
                    - big third party libraries
                    - Eclipse generate toString!
            */
        }


        /*
        # Command line arguments

        # args
        */
        {
            System.out.println("args = " + Arrays.toString(args));
        }

        /*
        # Annotations

            JLS7 9.6, 9.7.

            `@something` on top of methods.

            Represent code metadata.

            Classes, methods, fields, local variables, parameters and packages can all be annotated.

            Some are built into the language and custom ones can be defined as well:

            Annotations can also annotate other annotations.
            Those are called *meta annotations*.

            The only ways to observe effects of custom annotations are to either use:

            - reflection for runtime annotations
            - bytecode manipulation for class anotations
            - a Java parser for source annotations

            This can be controlled by the `@Retention` built-in meta annotation.

            There is no non-reflection language built-in method to view annotation information.

            TODO show on example code.

            Annotation methods can’t have parameters.

            Annotation methods return types are limited to primitives,
            String, Enums, Annotation or array of these.
        */
        {
            /*
            # Custom annotations

                Example below.

            # Annotation parameter types

                Annotation parameters correspond to the "methods" of the annotation declaration.

                Only:

                - primitive
                - String
                - Class
                - an Enum
                - another Annotation
                - an array of any of the above

                and which are compile time constants. Arbitrary objects are not allowed.

            # default

                Annotation "method" declarations can use the `default` keyword to indicate their default value.

                This has no relation to the Java 8 default interface method qualifier.

            # Annotation fields

                Annotation can have static final fields.

                They can be used as the default value of methods.

                TODO how to access them otherwise?

            # Special annotation parameters

                TODO remove from comments.

                Annotations without parameters don't need the parenthesis:

                    @Annotation
                    class Class {}

                Annotations with a single parameter, don't need the key:

                    @Annotation(1)
                    class Class {}

                In that case, if the value is an array, `{}` can be omited:

                    @Annotation(1, 2)
                    class Class {}
            */
            {
                /*
                # getAnnotations
                */
                {
                    @MyAnnotation(i = 1, s = "abc")
                    class Class {}
                    Annotation[] annotations = Class.class.getAnnotations();
                    assert ((MyAnnotation)annotations[0]).i() == 1;
                    assert ((MyAnnotation)annotations[0]).s().equals("abc");
                }

                /*
                # Annotations interface

                    http://docs.oracle.com/javase/7/docs/api/java/lang/annotation/Annotation.html
                */
                {
                    // TODO what is MyAnnotation? A class? An interface?
                    MyAnnotation a = Class.class.getAnnotation(MyAnnotation.class);

                    // TODO
                    //assert a instanceof Annotation;

                    Annotation a2 = a;
                }

                // Arguments without default must be given.
                {
                    // ERROR: annotation is missing values
                    //@MyAnnotation()
                    class Class {}
                }

                // Annotations cannot be repeated in Java 7.
                // Java 8 adds repeatable which allows it.
                {
                    // ERROR duplicate annotation
                    //@MyAnnotation(i = 1, s = "abc")
                    @MyAnnotation(i = 1, s = "abc")
                    class Class {}
                }

                /*
                # Annotation inheritance

                    No such thing: http://stackoverflow.com/questions/1624084/why-is-not-possible-to-extend-annotations-in-java
                */
            }

            /*
            # Built-in annotations

                - `Override` - Checks that the method is an override. Causes a compile error if the method is not found in one of the parent classes or implemented interfaces.
                - `Deprecated` - Marks the method as obsolete. Causes a compile warning if the method is used.
                - `SuppressWarnings`
                - `SafeVarargs` - Suppress warnings for all callers of a method or constructor with a generics varargs parameter, since Java 7.
                - `FunctionalInterface` - Specifies that the type declaration is intended to be a functional interface, since Java 8.

                Meta:

                - `Retention`
                - `Documented` - Marks another annotation for inclusion in the documentation, e.g. Javadoc.
                - `Target`
                - `Repeatable` - Java 1.8 - Specifies that the annotation can be applied more than once to the same declaration.
            */
            {
                /*
                # Override annotation

                    <http://stackoverflow.com/questions/94361/when-do-you-use-javas-override-annotation-and-why>

                    Two benefits:

                    -   compiler checks if you are actually overridding something and aborts if not

                        Mistakes are specially common when overload is mistakenly used instead of override.

                    -   self documenting code

                    But no other effects.
                */
                {
                    assert (new AnnotationOverride()).method() == 1;
                }

                /*
                # Deprecated

                    If used, the compiler generates a warning.

                    TODO compiler not generating the warning...

                    There is also the bytecode deprecated attribute,
                    but it does not seem possible to generate it from Java:
                    https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.15
                */
                {
                    assert AnnotationDeprecated.method() == 0;
                }

                /*
                # SuppressWarnings

                    Instructs the compiler to suppress the compile time warnings specified in the annotation parameters.

                    The exact list is vendor defined, only `unchecked` being mentioned in the JLS.

                    http://stackoverflow.com/questions/1205995/what-is-the-list-of-valid-suppresswarnings-warning-names-in-java
                */
                {
                    // TODO can be used to supress warning on statements without assignment?
                    {
	                    @SuppressWarnings({ "rawtypes" })
                        ArrayList names = new ArrayList();
                        @SuppressWarnings({ "unchecked" })
                        // HERE: how to remove the `boolean b`?
                        boolean b = names.add("abc");
                    }
                }

                /*
                # Retention

                # RetentionPolicy

                    Specifies how the marked annotation is stored. Whether in:

                    - the code only
                    - compiled into the class (default)
                    - available at runtime through reflection.

                    The values come from http://docs.oracle.com/javase/7/docs/api/java/lang/annotation/RetentionPolicy.html

                    To be able to use reflection from a simple Java program, you must enable `RUNTIME`.

                    TODO in the stdlib, is it possible to notice the effect of annotations otherwise
                    (besides implementing your own .java or .class parser...)

                    # CLASS retention policy

                        Application:
                        http://stackoverflow.com/questions/3849593/java-annotations-looking-for-an-example-of-retentionpolicy-class

                        Only useful for bytecode level operations, e.g. obfuscators.
                */
                {
                    {
                        @RetentionSource
                        class C {}
                        assert C.class.getAnnotations().length == 0;
                    }

                    {
                        @RetentionClass
                        class C {}
                        assert C.class.getAnnotations().length == 0;
                    }

                    {
                        @RetentionRuntime
                        class C {}
                        assert C.class.getAnnotations().length == 1;
                    }
                }

                /*
                # Target annotation

                    Limit what kinds of elements the annotation can be applied to.

                    Default: any element type.

                    Takes a list, so can have multiple values.
                */


                /*
                # Inherited annotation

                    Indicates that derived classes will inherit the annotation.

                    False by default.
                */
            }
        }

        /*
        # JCL

        # stdlib

            Java Class Library.

            Mostly base language features, only library level.

            But note that some classes have magic language behaviour.
        */
        {
            /*
            # CharSequence

                http://docs.oracle.com/javase/7/docs/api/java/lang/CharSequence.html

                Major interface implemented by String and other classes.

                Prefer to pass it around instead of strings whenever possible.
            */
            {
                // Does not specify anything about equals,
                // and major implementors don't either.
                assert !new StringBuilder("ab").equals("ab");

                // Requires `toString()`. So we can do:
                assert "ab".equals("ab".toString().toString());
            }

            /*
            # StringBuffer

                Vs `StringBuilder`: http://stackoverflow.com/questions/355089/stringbuilder-and-stringbuffer

                Main difference: synchronized, thus slower and more convenient for multi threading.
            */

            /*
            # Segment

                TODO
            */

            /*
            # Arrays class

                Convenience static utilities for Arrays.

                http://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html
            */
            {
                /*
                # Array equals

                    The `Array.equals` inherited from `Object` compares addresses and is the same as `==`.

                # memcmp

                    http://stackoverflow.com/questions/1090102/equivalent-of-memcmp-in-java

                    There is no direct way of doing subrange compare,
                    likely the best is `Arrays.copyOfRange` + `Arrays.equals` if you need subranges.

                    But that likely won't generate efficient machine code like GCC does for memcmp.

                    This is in some constrast to `memcpy`, for which Java has `System.arraycopy`,
                    which might conveivably be more efficient.
                */
                {
                    int[] is = {0, 1, 2};
                    int[] js = {0, 1, 2};
                    assert is.equals(is);
                    assert !is.equals(js);
                    assert is != js;
                    assert Arrays.equals(is, js);
                }

                /*
                # sort array inplace

                    Javadoc says it's a Dual-Pivot Quicksort.

                    Java 8 adds a *parallel* version as well... beautiful.

                    In early 2015, a bug was found on it:
                    http://envisage-project.eu/proving-android-java-and-python-sorting-algorithm-is-broken-and-how-to-fix-it/
                */
                {
                    int[] is = {2, 0, 1};
                    int[] js = {0, 1, 2};
                    // Returns void
                    Arrays.sort(is);
                    assert Arrays.equals(is, js);

                    /*
                    # Permutation array for a sort

                        http://stackoverflow.com/questions/11997326/how-to-find-the-permutation-of-a-sort-in-java

                        Useful when you have multiple input arrays,
                        typicially as the input of a numerical problem,
                        and you need to sort all of them based on a calculated array, e.g., input:

                        - int[] cost
                        - int[] benefit

                        In your heuristic, you calculate:

                        - int[] ratioCostBenefit

                        and now you want to sort by the ratio,
                        but you don't want to put them all into a new object since that would pollute the original data.

                        Permutations can also be done with matrices,
                        and that is the more convenient mathematical model,
                        but arrays are more efficient of course.

                        TODO give example, move to algorithms.
                    */
                }

                /*
                # Maximum value in array of primitives

                    No good way:
                    http://stackoverflow.com/questions/1484347/finding-the-max-min-value-in-an-array-of-primitives-using-java

                    If non-primitives, just use `Collections.min(Arrays.asList())`.
                */

                /*
                # Array of wrappers to array of primitives

                    http://stackoverflow.com/questions/564392/converting-an-array-of-objects-to-an-array-of-their-primitive-types

                    No good way without loop.

                # Array of primitives to array of wrappers

                    http://stackoverflow.com/questions/3770289/converting-array-of-primitives-to-array-of-containers-in-java

                    No good way without loop.
                */

                /*
                # asList

                    Converts an `Array` to a `List` with fixed size.

                    Good possibility to create `Collections` in one line, but note the fixed size requirement.
                */
                {
                    List<Integer> l = Arrays.asList(0, 1, 2);
                    List<Integer> m = new ArrayList<>();
                    m.add(0);
                    m.add(1);
                    m.add(2);
                    assert l.equals(m);

                    /*
                    They're not kidding about the fixed-size part.

                    See also `Collections#unmodifiableList`.
                    */
                    boolean fail = false;
                    try {
                        l.add(3);
                    } catch(UnsupportedOperationException e) {
                        fail = true;
                    }
                    assert fail;
                }

                /*
                # copyOf

                    Shallow copy.

                # copyOf vs clone vs System#arraycopy

                    Performance-wise, all seem to be the same. I use the following rule:
                    use the one that takes the least arguments.

                    -   `clone()` for a direct copy

                    -   `Arrays.copyOf` for a copy up to given length

                    -   `System.arraycopy` for a copy inside a single array,
                        or with starting position offsets

                */
                {
                    int[] is = new int[]{0, 1};
                    int[] js = Arrays.copyOf(is, is.length);
                    assert(is != js);
                    assert(Arrays.equals(is, js));
                }

                /*
                # copyOfRange

                # slice array

                    http://stackoverflow.com/questions/4439595/how-to-create-a-sub-array-from-another-array-in-java

                    Without reallocating:
                    http://stackoverflow.com/questions/1100371/grab-a-segment-of-an-array-in-java-without-creating-a-new-array-on-heap
                */
                {
                    // No reallocation. TODO random access guaranteed?
                    //Arrays.asList(array).subList(x, y).
                }

                /*
                # toString

                    Get a String representation of an array.

                    Same as: `Arrays.asList(a).toString()`.

                    Great way to visualize it.

                    Calls `toString` on each element.

                    For arrays of arrays, consider `deepToString`.
                */
                {
                    assert Arrays.toString(new int[]{0, 1}).equals("[0, 1]");

                    /*
                    # Print byte[] as hex

                        http://stackoverflow.com/questions/9655181/convert-from-byte-array-to-hex-string-in-java

                        Best options: `DatatypeConverter.printHexBinary()`
                    */
                }

                /*
                # indexOf for arrays

                    http://stackoverflow.com/questions/4962361/where-is-javas-array-indexof

                    Nope, first `Arrays.toList`.
                */
            }

            /*
            # Set

                Interface. Major implementations include `HashSet`, `TreeSet`, `EnumSet`.

                http://docs.oracle.com/javase/7/docs/api/java/util/Set.html
            */
            {
            }

            /*
            # Graph

                Not in the stdlib.
            */

            /*
            # Heap data structure

                Not in the stdlib.
            */

            /*
            # System-ish classes

                My own invented arbitray classification.

                Classes which do magic things, like IO and VM internal actions:

                - System
                - Runtime
                - management package
                - CallSite
            */

            /*
            # System

                http://docs.oracle.com/javase/7/docs/api/java/lang/System.html

                Cannot be instanciated since no public constructor.
            */
            {
                /*
                # stdout

                # stderr

                    `PrintStream` objects for the standard streams.

                # stdin

                    `InputStream` object for stdin.

                    Note that `InputStream` is lower level than `PrintStream`,
                    and can only do byte operations.

                    You need a decorator like a Scanner to read formatted data like integers.

                    Read a line from it:
                    http://stackoverflow.com/questions/5287538/how-can-i-get-the-user-input-in-java
                */
                {
                    System.out.println("stdout");
                    System.err.println("stderr");
                }

                /*
                # arraycopy

                # memcpy

                # memmove

                    Faster array copy? Apparently same as `Arrays.copyOf`
                    which uses it internally, and is the neater interface.

                    <http://stackoverflow.com/questions/18638743/is-it-better-to-use-system-arraycopy-than-a-fast-for-loop-for-copying-array>

                    Array copy does take more arguments however,
                    and allows to copy into any position of the target array.

                    It can deal with overlaps on a single array operation,
                    making it the closes analogue to both `memcpy` and `memmove`.
                */
                {
                    int[] is = {0, 1, 2, 3, 4, 5};
                    System.arraycopy(is, 1, is, 2, 3);
                    assert Arrays.equals(is, new int[] {0, 1, 1, 2, 3, 5});
                }

                /*
                # Properties

                    http://docs.oracle.com/javase/7/docs/api/java/util/Properties.html

                    Extends Hashtable.

                    Notably returned by `System.getProperties()`,
                    which seems to be the main design choice force behind it.

                    But you can use it however you want, even though it's kind of ugly.

                    Vs hashmap:
                    http://stackoverflow.com/questions/2977125/when-to-use-properties-and-when-map-in-java
                */
                {
                    // Only String keys and values are accepted.

                    // Has file IO methods built-in!

                    // Has a per-key defaults system:
                    Properties p0 = new Properties();
                    p0.setProperty("a", "0");
                    p0.setProperty("b", "1");
                    Properties p1 = new Properties(p0);
                    p1.setProperty("a", "10");
                    assert p1.getProperty("a").equals("10");
                    assert p1.getProperty("b").equals("1");
                    assert p1.getProperty("c") == null;
                }

                /*
                # getProperty

                    Same as `getProperties().getProperty(name)`.

                # getProperties

                    Returns an a system configuration value stored inside a `Properties` object,
                    which may be used to configure and query JVM parameters.

                    Used instead of command line arguments because they are more flexible. TODO how?
                */
                {
                    /*
                    # getProperties

                        Get all properties at once in a `Properties` object:
                        http://docs.oracle.com/javase/8/docs/api/java/util/Properties.html
                    */
                    {
                        assert System.getProperties().getClass() == Properties.class;
                    }

                    /*
                    # Predefiened properties

                        List of all predefined properties
                        <http://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html>

                        # os.name

                            May include OS version, and upper case letters. Sample values:

                            - Ubuntu 14.04: "Linux"
                            - Windows XXX

                            The humanish nature of the output makes it harde to detect the OS:

                            http://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java

                        # version

                            http://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java
                    */
                    {
                        System.out.println("System.getProperty()");
                        // TODO `file.separator` property vs `File.separator`?
                        System.out.println("  file.separator = " + System.getProperty("file.separator"));
                        System.out.println("  java.io.tmpdir = " + System.getProperty("java.io.tmpdir"));
                        System.out.println("  java.library.path = " + System.getProperty("java.library.path"));
                        System.out.println("  os.arch = " + System.getProperty("os.arch"));
                        System.out.println("  os.name = " + System.getProperty("os.name"));
                    }

                    /*
                    # Version of JVM

                        http://stackoverflow.com/questions/2591083/getting-version-of-java-in-runtime

                        `java.version` property seems to be the way to go.

                    # Conditional compilation

                    # IFDEF

                        You can't do conditional compilation at all in Java,
                        including based on the version:

                        - http://stackoverflow.com/questions/11288083/javaconditional-imports
                    */
                    {
                        System.out.println("  java.version = " + System.getProperty("java.version"));
                    }

                    // Custom properties can be passed to the JVM with `java -D'custom.property=value'
                    {
                        //System.out.println("System.getProperty(\"custom.property\") = " +
                                            //System.getProperty("custom.property"));
                    }
                }

                /*
                # nanoTime

                # currentTimeMIllis
                */

                /*
                # gc

                    Suggest garbage collection to the VM, increasing the probability that it will get run.

                    Does not guarantee that it will be done.
                */
                {
                    /*
                    # Force garbage collection

                        TODO seems not possible.
                    */
                }

                /*
                # console
                */
                {
                }
            }

            /*
            # Runtime

                <http://docs.oracle.com/javase/7/docs/api/java/lang/Runtime.html>

                VM information.

                Some of it's methods exist in System as well,
                and the Javadoc says that it is preferred to run them from there
            */
            {
                // Singleton that can be obtained with `getRuntime`.
                Runtime runtime = Runtime.getRuntime();
                System.out.println("Runtime:");
                System.out.println("  availableProcessors() = " + runtime.availableProcessors());
                System.out.println("  freeMemory() = (MiB) " + runtime.freeMemory() / (1024*1024));
                System.out.println("  totalMemory() = (MiB) " + runtime.totalMemory() / (1024*1024));
                System.out.println("  maxMemory() = (MiB) " + runtime.maxMemory() / (1024*1024));

                /*
                # traceInstructions

                # traceMethodCalls

                    TODO where does it go to in Ubuntu?
                */
                {
                    //runtime.traceMethodCalls(true);
                    //runtime.traceInstructions(true);
                }

                /*
                # load

                    TODO
                */
            }

            /*
            # management

                `java.lang.management`
                <http://docs.oracle.com/javase/7/docs/api/java/lang/management/package-summary.html>

                View and edit JVM information.
            */
            {
            }

            /*
            # CallSite

                Name of a VM concept related to polymorphism.

                TODO do some fun stuff with it.
            */

            /*
            # Random

                http://docs.oracle.com/javase/7/docs/api/java/util/Random.html

                For multi-threaded operations, prefer `ThreadLocalRandom`.

                `Math.random()` is a convenient front-end for this.
            */
            {
                Random rand = new Random();
                int min = 5;
                int max = 10;
                int r = rand.nextInt((max - min) + 1) + min;
                assert(r >= min);
                assert(r <= max);
                System.out.println("Random#nextInt() = " + r);

                /*
                Positive random int.
                */
                {
                    /*
                    Math.abs is not correct because 2^32 == Integer.MIN_VALUE
                    does not have an encodable -1

                    `Random.nextInt(Integer.MAX_VALUE)` does not work because it leaves out the largets number.

                    http://stackoverflow.com/questions/5827023/java-random-giving-negative-numbers

                    Solution: expose the protected next method on a base class.
                    */
                    {
                        //assert new Random().next(Integer.SIZE - 1) > 0;
                    }
                }

                /*
                # SecureRandom

                    On Linux, uses the Kernel random number generator for the seed.

                    TODO is the algorithm itself also different?

                    http://security.stackexchange.com/questions/40633/java-securerandom-doesnt-block-how
                */
            }

            /*
            # math package

                Does not contain much: mostly BigInteger and BigDecimal.
            */
            {
                /*
                # BigInteger

                    Arbitrary precision integer.

                    Immutable.

                    No boxing conversions: you must use methods for everything.
                */
                {
                    BigInteger i = BigInteger.TEN;
                    assert i.equals(BigInteger.valueOf(10));

                    // TODO initialize a `BigInteger` with a value larger than a long?
                    // Necessary to do power operations?
                }
            }

            /*
            File and directory operations
            */
            {
                /*
                # Path

                # Paths

                    <http://docs.oracle.com/javase/7/docs/api/java/nio/file/Paths.html>
                */
                {
                    /*
                    Used to create Path objects, which have no constructor. TODO why?

                    Another common way of creating Path objects is through `File#toPath`.
                    */

                    /*
                    # cwd

                        Get current working directory.
                    */
                    {
                        System.out.println("cwd = " + Paths.get("").toAbsolutePath().toString());
                    }

                    /*
                    # join paths

                        http://stackoverflow.com/questions/412380/combine-paths-in-java
                    */
                    {
                        // Paths.get("foo", "bar", "baz.txt");
                        // new File(String parent, String child)
                        // path.resolve(Paths.get("child));
                    }
                }

                /*
                # File

                    http://docs.oracle.com/javase/7/docs/api/java/io/File.html#pathSeparatorChar

                    Many file and path related operations: existence, join,
                    permissions,remove, list, transversal convenience.
                */
                {
                    /*
                    # delete

                        Remove file or empty directory

                        `Files` has a static version.

                    # rmdir

                        See `delete`.
                    */

                    /*
                    # rmrf

                        http://stackoverflow.com/questions/779519/delete-files-recursively-in-java

                        No JDK way to do it in one line: must use large transversal loop.
                    */

                    /*
                    # Get file size

                        There are a multiple methods:
                        http://stackoverflow.com/questions/116574/java-get-file-size-efficiently

                        - File#length
                        - FileChannel#size

                        TODO vs.
                    */

                    /*
                    # length
                    */
                    {
                        File f = File.createTempFile("aaa", null);
                        byte[] out = new byte[]{0, 1, 2};
                        FileOutputStream o = new FileOutputStream(f);
                        o.write(out);
                        o.close();
                        assert f.length() == 3;
                        f.delete();
                    }

                    /*
                    # createTempFile
                    */
                    {
                        File f = File.createTempFile("aaa", null);
                    }

                    /*
                    # createTempDirectory
                    */
                    {
                        //Path directory = Files.createTempDirectory("prefix");
                        //Files.createFile(directory.resolve(Paths.get("basename")));
                    }
                }

                /*
                # FileSystems

                # FileSystem
                */
                {
                    FileSystem fs = FileSystems.getDefault();
                    System.out.println("FileSystems.getDefault()");
                    Iterator<Path> it = fs.getRootDirectories().iterator();
                    System.out.println("  getRootDirectories()[0] = " + it.next().toString());
                    if (it.hasNext())
                        System.out.println("  getRootDirectories()[1] = " + it.next().toString());
                    System.out.println("  getSeparator() = " + fs.getSeparator());
                    System.out.println("  isOpen() = " + fs.isOpen());
                    System.out.println("  isReadOnly() = " + fs.isReadOnly());
                }
            }

            /*
            # IO

            # File IO

                Write human readable data to file, buffered: `PrintWriter`.

                Hierarchy of most useful classes:

                Stream family:

                -   OutputStream
                    -   FileOutputStream
                    -   PrintStream
                    -   ByteArrayOutputStream
                    -   DataOutputStream
                -   InputStream
                    - FileInputStream

                Reader / writer family:

                -   Writer
                    -   PrintWritter
                    -   StringWritter
                -   Reader
                    -   BufferedReader
                    -   InputStreamReader
                        -   FileReader
                    -   StringReader

                Other families:

                -   RandomAccessFile
                -   FileChannel

                Examples here will not deal with exceptions.
                In real applications, you might want to do that with the "try with resources" syntax.
            */
            {
                /*
                # OutputStream

                    http://docs.oracle.com/javase/7/docs/api/java/io/OutputStream.html

                    Abstract class.

                    Base class of all stream output classes.

                    Binary IO only through `write`.

                    Unbuffered.

                    Major concrete implementations:

                    - `ByteArrayOutputStream`: to arrays in memory. Can be then converted to String.
                    - `FileOutputStream`: low level to files
                    - `PrintStream`: high level to files, buffered, formatted

                # InputStream

                    http://docs.oracle.com/javase/7/docs/api/java/io/InputStream.html
                */

                /*
                # ByteArrayOutputStream

                    http://docs.oracle.com/javase/7/docs/api/java/io/ByteArrayOutputStream.html 

                    Stream that outputs to memory.

                    Grows automatically as bytes are added to it.

                    # OutputStream to String

                        <http://stackoverflow.com/questions/216894/get-an-outputstream-into-a-string>

                        The dominating method is to use `ByteArrayOutputStream`.

                # ByteArrayInputStream

                    Serves as an `InputStream` from memory.

                    Initialize with a `byte[]`, and the data will be that of the `byte[]`.
                */
                {
                    ByteArrayOutputStream o = new ByteArrayOutputStream();
                    byte[] out = new byte[]{0, 1, 2};
                    o.write(out);
                    o.close();
                    assert Arrays.equals(out, o.toByteArray());
                }

                /*
                # FileOutputStream

                    Low level file output.

                    No buffering, no formatting: just hard to the metal byte outputing.

                # FileInputStream

                    `read` counterpart.
                */
                {
                    /* Basic byte[] IO example. */
                    {
                        File f = File.createTempFile("aaa", null);
                        byte[] out = new byte[]{0, 1, 2};
                        byte[] in = new byte[out.length];

                        FileOutputStream o = new FileOutputStream(f);
                        // Write `out.length` bytes.
                        o.write(out);
                        o.close();

                        FileInputStream i = new FileInputStream(f);
                        // Read up to `in.length` bytes, and return the number read.
                        assert i.read(in) == out.length;
                        // -1 for EOF.
                        assert i.read() == -1;
                        i.close();

                        assert Arrays.equals(in, out);

                        f.delete();
                    }


                    /*
                    # getChannel
                    */
                    {
                        /*
                        # seek with getChannel

                        # position
                        */
                        {
                            File f = File.createTempFile("aaa", null);
                            byte[] out = new byte[]{0, 1, 2};

                            FileOutputStream o = new FileOutputStream(f);
                            o.write(out);
                            o.close();

                            FileInputStream i = new FileInputStream(f);
                            i.getChannel().position(1);
                            assert i.read() == out[1];
                            i.close();
                            f.delete();
                        }
                    }
                }

                /*
                # FileChannel

                    http://docs.oracle.com/javase/7/docs/api/java/nio/channels/FileChannel.html

                    Can be obtained with `FileInputStream.getChannel()`.

                    TODO vs `FileInputStream`?
                    http://stackoverflow.com/questions/1605332/java-nio-filechannel-versus-fileoutputstream-performance-usefulness
                */

                /*
                # PrintStream

                    http://docs.oracle.com/javase/7/docs/api/java/io/PrintStream.html

                    Wraps `OutputStream` with a lot of convenience:

                    -   print primitive data types directly to files in a human readable form

                        The corresponding reader for those methods is Scanner.

                    -   line flushshin

                    -   not throw exceptions: sets an internal flag instead,
                        which you can access with check / setError methods.

                    -   buffered

                    -   create file streams without instanciating `FileOutputStream` directly
                        through the `String path` constructors.

                        But you can still wrap any `OutputStream` with the `OutputStream` constructor.

                    `System.out` and `err` are notable instances.

                    # Line flushing

                        Stream is flushed automatically whenever a newline is writen to it,
                        either via `println` methods or through `"\n"` literals.

                        Manual flushing can be also done with `flush()`.

                    # print vs write methods

                        The API convention is that:

                        -   `write` does binary IO: it only takes `byte[]`, and does no magic convertions.

                        -   `print` does text IO: it takes high level objects like
                            primitives and does magic convertions to `byte[]`

                    # println

                        print and add newline.

                        Contains convenience overloads for all base types.

                        Can also be empty `println()` for a single newline.

                        TODO: appends correct plaform newline or not? Probably.

                    # print

                        Same as println but no newline added.

                        Also contains base type overloads.

                    # format

                        printf C format string.

                        Differences:

                        - `%b`: boolean
                        - `%n`: system independent newline. `println` uses it already.

                */
                {
                    // TODO convert those to use `ByteArrayOutputStream`,
                    // and assert'em all.
                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    PrintStream p = new PrintStream(
                        b,
                        true,
                        "UTF-8"
                    );

                    p.println("abc");
                    assert Arrays.equals(b.toByteArray(), new byte[]{'a', 'b', 'c', '\n'});
                    b.reset();

                    p.println(1);
                    assert Arrays.equals(b.toByteArray(), new byte[]{'1', '\n'});
                    b.reset();

                    p.format("%.2f\n", 1.23);
                    assert Arrays.equals(b.toByteArray(), new byte[]{'1', '.', '2', '3', '\n'});
                    b.reset();

                    p.println(true);
                    assert Arrays.equals(b.toByteArray(), new byte[]{'t', 'r', 'u', 'e', '\n'});
                    b.reset();

                    p.close();
                }

                /*
                # DataOutputStream

                # fwrite

                    http://docs.oracle.com/javase/7/docs/api/java/io/DataOutputStream.html

                    Print binary, non-human readable, representations of primitives.

                # DataInputStream

                # fread

                    http://docs.oracle.com/javase/7/docs/api/java/io/DataInputStream.html

                    Implements DataInput http://docs.oracle.com/javase/7/docs/api/java/io/DataInput.html
                */
                {
                    int in = 0x00_01_02_03;

                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    DataOutputStream o = new DataOutputStream(b);
                    o.writeInt(in);
                    o.close();

                    byte[] out = b.toByteArray();
                    /*
                    Unlike C's fwrite, the representation format, and in particular endianess
                    is specified in the Javadoc, so we can assert it.

                    This is therefore a valid serialization method.

                    But you will likely only use this format if you are going
                    to read the data back with `DataInputStream#readXXX` later on.
                    */
                    assert Arrays.equals(out, new byte[]{0, 1, 2, 3});

                    DataInputStream i = new DataInputStream(new ByteArrayInputStream(out));
                    assert i.readInt() == in;
                    i.close();
                }

                /*
                # Writter

                    Similar to `OutputStream`, but focuses on streams of characters instead of bytes. 

                    This is the older API, and it was probably not deprecated because it is so widely used.

                    Furthermore, this API is not as low level as `OutputStream`, and not as high level as `InputStream`.

                    It is better to use `OutputStream` methods whenever you can.

                # PrintWriter

                    Analogous to `PrintStream`, but uses a `Writer` instead of `OutputStream`.

                    <http://docs.oracle.com/javase/7/docs/api/java/io/PrintWriter.html>

                    # Vs FileWriter

                        <http://stackoverflow.com/questions/5759925/printwriter-vs-filewriter-in-java>

                        Both use `FileOutputStream` internally, but `PrintWriter` is more magic.

                        PrintWriter is more useful for simple applications.

                        If you want to write `byte[]` binary IO, go for `FileOutputWriter`.
                */
                {
                    // TODO convert to use `ByteArrayOutputStream`.
                    File f = File.createTempFile("aaa", null);
                    PrintWriter p = new PrintWriter(f, "UTF-8");
                    p.print("ab");
                    p.close();
                    assert Arrays.equals(
                        Files.readAllBytes(f.toPath()),
                        new byte[]{'a', 'b'}
                    );
                    f.delete();
                }

                /*
                # StringWritter

                    http://docs.oracle.com/javase/7/docs/api/java/io/StringWriter.html

                    String backed writter.
                */
                {
                }
            }

            /*
            # Scanner

                <http://docs.oracle.com/javase/7/docs/api/java/util/Scanner.html>

                Simple string parser to get primitives out of strings or streams.

                Can use complex separators like regular expressions,
                which makes this a powerful tokenizer.
            */
            {
                Scanner s = new Scanner("12 3.4");
                assert s.hasNextInt();
                assert s.nextInt() == 12;

                assert s.hasNextDouble();
                assert s.nextDouble() == 3.4;

                // May block. But since this is a string of fixed size, EOF.
                assert !s.hasNext();
            }

            /*
            # Files

                <http://docs.oracle.com/javase/7/docs/api/java/nio/file/Files.html>
            */
            {
                /*
                # readAllBytes

                    Read entire file to a `byte[]`.

                # Read entire file to a String at once

                    http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file/326440#326440

                    The most convenient way seems to be to use this method,
                    and then convert it to a String with `new String(bytes, encoding)`;
                */

                /*
                # walkFileTree

                # SimpleFileVisitor

                # FileVisitResult

                    Operate on all files under directory recursively.
                */

                /*
                # isDirectory
                */

                /*
                # PosixFilePermissions

                    http://docs.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermission.html
                */
            }

            /*
            # Charset

                To change the default, use:

                    java -D'file.encoding=UTF-8' com.x.Main

                No constructor: you must generate them with `forName`,
                or better, with `StandardCharsets` if possible.

                Java makes it possible to create custom charsets.
            */
            {
                System.out.println("Charset.defaultCharset() = " + Charset.defaultCharset());

                /*
                #StandardCharsets

                    http://docs.oracle.com/javase/7/docs/api/java/nio/charset/StandardCharsets.html

                    Contains a few static fields with charsets guaranteed to be present.

                    Preferred way of getting charsets.
                */
                {
                    assert Charset.forName("UTF-8") == StandardCharsets.UTF_8;
                }
            }

            /*
            # JAXB

                Convert objects to XML and vice versa.
            */
            {
                // TODO finish example.
                /*
                class Class {
                    int i;
                }

                Class object = new Class();

                JAXBContext jaxbContext = JAXBContext.newInstance(Class.class);
                Marshaller marshaller = jaxbContext.createMarshaller();
                // Pretty print.
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(customer, System.out);
                */
            }
        }

        /*
        # java.lang

            <http://docs.oracle.com/javase/7/docs/api/java/lang/package-summary.html>
        */
        {
            /*
            `java.lang.X` classes contents are magic and get automatically imported.

            They contain basic utilities.

            Autoimport not happen for packages inside Java, e.g., `java.lang.reflect.Field`
            needs to be explicitly imported by the programmer.
            */
            {
                assert java.lang.Integer.class == Integer.class;

                /*
                It is possible to create classes with the same name as those in `java.lang`.

                However you cannot give them  a `main` method an run them with `java`,
                which will first load the `java.lang` class in that case, and fail.
                TODO check. Why?
                */
                {
                    class String {}
                }
            }

            /*
            # Math

                http://docs.oracle.com/javase/7/docs/api/java/lang/Math.html
            */
            {
                /*
                # random

                    Same as `Random#nextDouble()`: Javadoc says that it creates a Random object, and caches it.

                    Apparently the seed is taken from `currentTimeMillis()`.
                    http://stackoverflow.com/questions/3535574/getting-current-date-time-for-a-random-number-generators-seed
                    but this is only an implementation detail.

                    So you should not rely on that for cryptography:
                    http://crypto.stackexchange.com/questions/18207/is-it-safe-to-seed-a-random-number-generator-from-system-time

                    There seems to be no way to customize the seed: you need to use `Random` for that.

                    And `Random` is also more flexible as it can generate integers,
                    and a Gaussian distribution.
                */
                {
                    System.out.println("Math.random() = " + Math.random());
                    System.out.println("Math.random() = " + Math.random());
                    assert Math.random() >= 0.0;
                    assert Math.random() <= 1.0;
                }

                /*
                # sqrt

                    NaN if negative.
                */
                {
                    Math.sqrt(-1.0);
                }
            }

            /*
            # instrument
            */
            {
                /*
                # sizeof

                    http://stackoverflow.com/questions/52353/in-java-what-is-the-best-way-to-determine-the-size-of-an-object

                    There are other methods with Unsafe.

                # getObjectSize
                */
                {
                    // TODO does not seems to be instantiable without a Jar?
                }
            }
        }

        /*
        # javax vs java

            http://stackoverflow.com/questions/727844/javax-vs-java-package
        */

        /*
        # Off-heap memory

            By default all Java memory allocated through `new`
            is left on the heap and managed by the garbage collector.

            The upside is that this prevents memory leaks.

            The downside is that you have the heap management overhead,
            which you might be able to do more efficiently given your extra
            application specific knowledge.

            There are however a few interfaces that allow off-heap allocation:

            - `direct ByteBuffer`
            - `sun.misc.Unsafe`

            You should only use those when performance is critical.

            TODO when should either one be used instead of the other?
        */
        {
            /*
            # Buffer

                http://docs.oracle.com/javase/7/docs/api/java/nio/Buffer.html

                Interface. Implementations: `ByteBuffer` +
                one for each primitive: `IntBuffer`, etc.

                Tutorials:

                - http://tutorials.jenkov.com/java-nio/buffers.html
                - http://www.kdgregory.com/index.php?page=java.byteBuffer

                General operation:

                - Write data into the Buffer
                - Call `buffer.flip()` to make it ready for reading.
                - Read data out of the Buffer
                - Call `buffer.clear()` or `buffer.compact()`. This makes the buffer ready for writting.

                Inner state:

                - position: where the read has stopped
                - limit: how much is currently read
                - capacity: up to where the buffer could still read
            */
            {
                /*
                # ByteBuffer

                    http://docs.oracle.com/javase/7/docs/api/java/nio/ByteBuffer.html

                    There are two types of ByteBuffer: direct and indirect.

                    The direct ones may not be stored on the heap, but there is no guarantee:

                    - http://stackoverflow.com/questions/5670862/bytebuffer-allocate-vs-bytebuffer-allocatedirect
                    - http://examples.javacodegeeks.com/core-java/nio/bytebuffer/java-direct-bytebuffer-example

                    In particular, it does not get moved around, so you can use it with JNI.
                */
                {
                    /*
                    # allocateDirect

                        http://stackoverflow.com/questions/5670862/bytebuffer-allocate-vs-bytebuffer-allocatedirect
                    */
                    {
                        // Primitives
                        {
                            ByteBuffer bb = ByteBuffer.allocateDirect(8);

                            bb.putInt(0, 1);
                            bb.putInt(4, 2);
                            assert bb.getInt(0) == 1;
                            assert bb.getInt(4) == 2;

                            // Bound chekcs are done.
                            boolean fail = false;
                            try {
                                bb.getInt(8);
                            } catch(IndexOutOfBoundsException e) {
                                fail = true;
                            }
                            assert fail;

                            assert bb.isDirect();
                        }

                        /*
                        Objects: you have to serialize and deserialize them,
                        and then use the bulk `get` and `set` methods

                        You can't get individual fields, or call methods:
                        before you do anything you must deserialize, so it is really inneficient.

                        http://stackoverflow.com/questions/7071167/putting-an-object-into-a-bytebuffer
                        */
                    }

                    /*
                    # allocate
                    */
                    {
                        ByteBuffer bb = ByteBuffer.allocate(2);
                        assert !bb.isDirect();
                        // TODO actually use it, and show that direct is slower than direct.
                    }

                    // TODO
                    //assert !byteBuffer.hasArray();
                }
            }
        }

        /*
        # macros

            Java has no built-in macro pre-processor:
            <http://stackoverflow.com/questions/6525059/can-i-have-macros-in-java-source-files>

            Consequence: no conditional imports:
            <http://stackoverflow.com/questions/11288083/javaconditional-imports>
        */

        /*
        # SecurityManager

            http://docs.oracle.com/javase/7/docs/api/java/lang/SecurityManager.html
        */
        {
            // TODO
        }

        /*
        # Signal handling

            Apparently you cannot prevent POSIX signals from killing the JVM,
            only run some code before it closes with a shutdown hook:
            http://stackoverflow.com/questions/2541475/capture-sigint-in-java

            The rationale is that signals don't exist in Windows.
        */

        /*
        # System.exit

        # exit status
        */
        {
            System.exit(0);
            System.exit(1);
        }
    }
}
