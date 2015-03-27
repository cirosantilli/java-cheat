/*
Main Java 7 cheat.

Will include every test that does not take too long or produce too much output.

Assertions will be used wherever possible, and values will only be printed if
they cannot be deterministically checked.

# Preprocessor

    Java has no preprocessor like in C.

    Unlike C, there is no non-messy way of doing the following:

    - get current line number
    - get current function name

    #conditional compile according to Java version / OS

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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
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
        public static String publicStaticString;

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

        static class StaticOuter {
            static private final int i = 1;
            static class StaticNested {
                static int getI() {
                    return i;
                }
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

        // Generics

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

        // Interface

            interface EmptyInterface {}

            interface EmptyInterfaceDerived extends EmptyInterface{}

            interface EmptyGenericInterface<T> {}

            interface EmptyGenericInterfaceDerived<T> extends EmptyGenericInterface<T> {}

            interface InterfacePrivateMethod {
                //private void method();
            }

    // Annotation

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.TYPE)
        @interface MyAnnotation {
            public int i();
            public String s();
        }

    // Enum

        public enum E {
            A,
            B,
            C
        }

    // Method

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

        static int arrayParam(int[] is) {
            return is[0] + is[1];
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

        static int finallyAndReturn() {
        label:
            try {
                return 1;
            } finally {
                break label;
            }
            return -1;
        }

        static int notAStatement() {
            return 0;
        }

    // Exceptions

        public static void throwError() {
            throw new Error();
        }

        public static void throwRuntimeException() {
            throw new RuntimeException();
        }

        //public static void throwNoSuchFieldExceptionFail() {
            //throw new NoSuchFieldException();
        //}

        public static void throwNoSuchFieldException() throws NoSuchFieldException {
            throw new NoSuchFieldException();
        }

        public static void throwNoSuchFieldExceptionBase() throws Throwable {
            throw new NoSuchFieldException();
        }

        // ERROR: incompatible types.
        //public static void throwNoSuchFieldExceptionObject() throws Object {
            //throw new NoSuchFieldException();
        //}

        public static int declaresExceptionNotThrown() throws Exception {
            return 1;
        }

        public static class CloseableClass implements Closeable {
            public static int i;

            @Override
            public void close() {
                CloseableClass.i += 1;
            }
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

                The only weird character mentioned explicitly by the JLS is `$`.
                Sounds like... JavaScript and mercenary money makers.

                is `true`. http://docs.oracle.com/javase/7/docs/api/java/lang/Character.html#isJavaIdentifierPart%28int%29

                It is not clear to me if the API description there is precise or not,
                it just mentions generic character sets like "combining mark" and non-spacing mark.
            */
            {
                int $ = 0;
                assert $ == 0;
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

                Unicode character.

                UTF-16 representation guaranteed, thus 2 to 4 bytes each, unsigned.

                <http://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.2>

                Backslash escapes can be used.
            */
            {
                assert 0x100 == ((int)'Ā');
                assert 0x100 == ((int)'\u0100');
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
            # Primitive wrappers

            # Byte

            # Char

            # Short

            # Short

            # Float

            # Double

                Each primitive type has an Object wrapper: Byte, Integer, Char, etc.

                Those object wrappers are necessary in situations
                where objects are required instead of primitives.

                The primitive wrappers have the following magic properties in the language:

                - boxing and unboxing conversion

            # Number

                <http://docs.oracle.com/javase/7/docs/api/java/lang/Number.html>

                Implemented by all primitive wrappers.

                Only contains the `xValue` methods.
            */
            {
                /*
                Primitive wrappers are immutable:
                <http://stackoverflow.com/questions/3815173/increment-a-integers-int-value>

                This is how you increment them:
                */
                {
                    Integer i = 0;
                    i = new Integer(i.intValue() + 1);
                    assert i == 1;
                }

                /*
                # Add wrappers

                # Multiply wrappers

                    The primitive wrappers do not have methods that can be performed directly with operators
                    such as add and multiply likely because:

                    - unboxing produces a nice syntax
                    - the efficiency of unboxing is already optimal given immutability

                    BigInteger and BigDecimal however to implement those methods
                    since they cannot perform boxing conversions.
                */
                {
                    assert new Integer(1) + new Integer(2) == 3;
                }

                /*
                # Compare wrappers

                # Equality wrappers

                    Equality comparison `==` does *not* unbox them,
                    but because of the wrapper pool it works for some values.

                    < and > does.
                */
                {
                    assert new Integer(1) != new Integer(1);
                    assert (new Integer(1)).equals(new Integer(1));
                    assert new Integer(1) < new Integer(2);

                    /*
                    # Integer pool

                    # Wrapper pool

                        http://stackoverflow.com/questions/1700081/why-does-128-128-return-false-but-127-127-return-true-in-this-code

                        Boxing resolves to `Integer.valueOf()`,
                        which like Strings has a pool mechanism.

                        But unlike strings, the pool mechanism is only
                        for a limited range of integers
                        between -128 and 127, and for true or false.

                        The JSL itself states that ideally the range would be all integers,
                        and that this is just an implementation limitation.

                        It is also possible to configure the limit with:
                        -Djava.lang.Integer.IntegerCache.high

                        Summary: **always use `equals()` to compare wrappers!
                    */
                    {
                        assert Integer.valueOf(127) == Integer.valueOf(127);
                        assert Integer.valueOf(128) != Integer.valueOf(128);

                        // Same as above.
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

                        assert Boolean.valueOf(true) == Boolean.valueOf(true);
                        assert Boolean.valueOf(false) == Boolean.valueOf(false);

                        // New raw objects however are different as usual.
                        assert new Integer(127) != new Integer(127);
                    }
                }

                /*
                # Void

                    TODO what is this used for?
                    <http://stackoverflow.com/questions/643906/uses-for-the-java-void-reference-type
                */
                {
                    Void v;

                    // The only valid valud that can fit into a Void is null:
                    v = null;
                }

                /*
                # NaN
                */
                {
                    // http://stackoverflow.com/questions/8819738/why-does-double-nan-double-nan-return-false
                    // Basically because many unrealted operations can generate NaN,
                    // so comparing it does not make much sense.
                    assert Double.NaN != Double.NaN;
                    assert Double.isNaN(Double.NaN);
                }

                /*
                # Integer

                    Wrapper for `int`.
                */
                {
                    /*
                    # parseInt
                    */
                    {
                        assert Integer.parseInt("123") == 123;

                        // Very strict. No whitespaces or other noise.
                        boolean fail = false;
                        try {
                            Integer.parseInt("123\n");
                        } catch (NumberFormatException e) {
                            fail = true;
                        }
                        assert fail;
                    }
                }
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
                `a += b` is not exactly the same as `a = a + b`, but rather `a = (typeof a)(a + b)`.

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

                Nope: http://stackoverflow.com/questions/2545103/is-there-a-goto-statement-in-java

                Harmful C / C++ features were not included.
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

                    Very practical!
                */
                {
                    E e = E.A;
                    switch (e) {
                        case A:
                            assert e == E.A;
                        break;
                        case B:
                            assert e == E.B;
                        break;
                    }
                }
            }

            /*
            # Exceptions
            */
            {
                /*
                # try

                    Catches exceptions.
                */
                {
                    /*
                    # Closeable

                    # try with resources

                        Java 7 Project Coin feature.

                        <http://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html>

                        Try with parenthesis.

                        Automatically calls `close()` on variables defined there,
                        which must implement Closeable, which thus has a magic language function.

                        Major use case: file IO, to ensure that the file gets closed.

                        Similar to the with construct in Python which is often used for the same use case.

                        A similar effect can be achieved with a `finally` block,
                        although handling exceptions in the file opening may be trickier.
                    */
                    {
                        try(
                            CloseableClass c = new CloseableClass();
                            CloseableClass c2 = new CloseableClass()
                        ) {
                            assert c.i == 0;
                            assert c2.i == 0;
                        }
                        assert CloseableClass.i == 2;
                    }
                }

                /*
                # catch
                */
                {
                    /*
                    # catch multiple types of exceptions

                        Java 7 Coin Project.

                        `|` operator... ugly.

                        http://stackoverflow.com/questions/3495926/can-i-catch-multiple-java-exceptions-in-the-same-catch-clause
                    */
                    {
                        try {
                            throw new IllegalArgumentException();
                        } catch (NullPointerException | IllegalArgumentException e) {}
                    }
                }

                /*
                # throw

                # Throwable

                    <http://docs.oracle.com/javase/7/docs/api/java/lang/Throwable.html>

                    Only throwable objects may be thrown and declared thrown with `throws`.

                    The only stdlib implementing classes are Error and Exception
                */
                {
                    // Special case explicit on JLS: `NullPointerException` is thrown.
                    try { throw null; } catch (NullPointerException e) {}
                }

                /*
                # throws

                # Checked exceptions

                # Unchecked exceptions

                    <http://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.4.6>

                    Every method whose interior may throw an exception must declare it,
                    unless they are descendants of:

                    - `Error`
                    - `RuntimeException`

                    which are therefore language-magic.

                    The following hierarchy exists:

                        Object
                        |
                        Throwable
                        |
                        +--Error
                        |
                        +--Exception
                        |
                        +--Runtime exception
                        |
                        +--Many others

                    Exceptions which require the `throws` clause, are called *checked*.
                    The others are called unchecked.
                */
                {
                    // No need for `throws` because `Error` or `RuntimeError`.
                    try { throwError(); } catch (Throwable e) {}
                    try { throwRuntimeException(); } catch (Throwable e) {}

                    // ERROR: you must declare or compile error otherwise.
                    //try { throwNoSuchFieldExceptionFail(); } catch (Throwable e) {}
                    try { throwNoSuchFieldException(); } catch (Throwable e) {}

                    // Declaring throws with a base class also works.
                    // So declaring throws Throwable declares all.
                    try { throwNoSuchFieldExceptionBase();  } catch (Throwable e) {}

                    // ERROR: But you cannot declare throws Object because it is not Throwable.
                    //try { throwNoSuchFieldExceptionObject();  } catch (Throwable e) {}

                    // But you can declare an exception that is not thrown.
                    // TODO why?
                    assert declaresExceptionNotThrown() == 1;
                }

                /*
                # finally
                */
                {
                    /*
                    Can be used without catch.

                    All exceptions will blow up, but the finally will always get run.
                    */
                    {
                        try {
                        } finally {
                        }
                    }

                    // finally and return: finally executes even after a return in the try.
                    // But if you do return or break in the finally,
                    // then the previous return is ignored!
                    {
                        // TODO get working
                        assert finallyAndReturn() == -1;
                    }
                }

                /*
                # OutOfMemoryError

                    Memory allowed by the JVM run out.

                    The maximum can be configured with command line options.

                    <http://docs.oracle.com/javase/7/docs/api/java/lang/OutOfMemoryError.html>
                */

                /*
                # IllegalArgumentException

                    Inherits from `RuntimeException`.

                    Not intended to be declared, since they indicate programming errors
                    that generally cannot be dealt with:
                    http://stackoverflow.com/questions/5304098/should-i-put-throws-illegalargumentexception-at-the-function
                */
            }
        }

        // # for loop
        {
            /*
            # for-each loop

                Informal name for the enhanced for statement.

            # Enhanced for statement

                Called `for-each` on the guide:
                <http://docs.oracle.com/javase/8/docs/technotes/guides/language/foreach.html>
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
                }
            }

            // # break statement
            {
                /*
                # Labeled break statement

                    Generates a new statement. The break then jumps to whatever comes after the label.

                    Don't use it as it is just a goto in desguise:
                    <http://stackoverflow.com/questions/14960419/is-using-a-labeled-break-a-good-practice-in-java>

                    In C, there there are no labeled gotos, this is considered by many style guies
                    to be one of the last valid uses of GOTO.

                    Others also consider any forward GOTOs acceptable.
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
                        int i = 0;
                    label:
                        {
                            // if to avoid unreachable statement.
                            if (true)
                                break label;
                            i = 1;
                        }
                        assert i == 0;
                    }


                    // Labels also have scope, so you can use them many times.
                    {
                        int i = 0;
                    label:
                        {
                            if (true)
                                break label;
                            i = 1;
                        }
                        assert i == 0;
                    }

                    // ERROR: can't have unlabelled break statements outside loop or switch:
                    //break;

                    // ERROR: can't have labelled break statements
                    // that are not surrounded by the label
                    {
                        //break;
                    }

                    // Labelled continue must point to a label of a loop statement.
                    // Or else we could have a backwards GOTO.
                    {
                    label:
                        {
                            // ERROR: not a loop label
                            //continue label;
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

		/*
		# assert

			Built-in language statement.

			If fails, throws `AssertionError`.

			Only gets run if the `-ea` option is passed to `java` to run the class file.

            Vs exceptions:
            http://programmers.stackexchange.com/questions/137158/is-it-better-to-use-assert-or-illegalargumentexception-for-required-method-param
		*/
		{
			boolean fail = false;
			try {
				assert false;
			} catch(AssertionError e) {
				fail = true;
			}
			assert fail;

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

                Java has 4 class scopes: one more than C++, namely package-private.

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
            {
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
                    assert Main.<Object>genericMethod(o) == o;

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

                    TODO

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
                    */
                    {
                        // ERROR: illegal start of expression.
                        // Cannot be used for local variables.
                        //transient int i;

                        assert  Modifier.isTransient(TransientField.class.getField("ti").getModifiers());
                        assert !Modifier.isTransient(TransientField.class.getField("i").getModifiers());
                    }
                }
            }

            /*
            # Class class

                <http://docs.oracle.com/javase/7/docs/api/java/lang/Class.html>

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
                /*
                # Return multiple values

                    Not supported: <http://stackoverflow.com/questions/457629/how-to-return-multiple-objects-from-a-java-method>

                    There seems to be no concept of tuple as in python: <http://stackoverflow.com/questions/2670982/using-tuples-in-java>

                    Best solution: create a class that wraps all the return values.
                */

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

                        Implemented as a binary, often generated from a C source.
                        for speed or access to system specific APIs.
                        TODO get a minimal example working.
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
                    // TODO use existing methods

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
            # Constructor

                JLS7 8.8

                Cannot be `abstract`, `static`, `final`, `native`,
                `strictfp`, or `synchronized`.

                Can however be `private` to prevent object construction,
                e.g. in static utility classes.
            */
            {
                /*
                # Default constructor

                    Constructor automatically defined if you don't define any other constructor.

                    Sets all fields to their default values.
                */
                {
                    class Class {
                        Class(int i) {}
                    }
                    // ERROR
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
                <http://stackoverflow.com/questions/171952/is-there-a-destructor-for-java>

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
                # super
                */
                {
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
                    <http://stackoverflow.com/questions/5181578/use-of-final-class-in-java>

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
            {
                /*
                # Inner class

                    JLS7 8.1.3

                    Types:

                    - local
                    - anonymous
                    - non-static member

                    TODO are inner interfaces possible? Not local.

                    Those classes have in common that they can access the `this`
                    of the object in which they are inserted, unlike static member classes:

                    - local based on the this of the method where it gets defined
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

                        - cannot have a constructor
                        - since called on a new, does not need a constructor either,
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
                            Base b = new Base(1){
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
                        //
                        // TODO is there any use to doing this then?
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
                                    // ERROR
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

                        You need an instance of the outter class in order to access the inner one.
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
                                    // ERROR: cannot referenc this before supertype constructor.
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

        /*
        # enum

            Enums in Java are very close to classes.

            They can even have methods, and occupy a file with their name like a class.

            This is in great contrast where they are just integers.
        */
        {
            // ERROR: enum types must not be local
            //enum Elocal { A, B };

            assert E.A != E.B;

            /*
            Loop all values of an enum.

            Note how those values are strings by default,
            so you print them with `%s`.
            */
            {
                System.out.println("enum loop:");
                for (E e : E.values()) {
                    System.out.format("    %s%n", e);
                }
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
                assert (new Class0()).getClass() == Class0.class;
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
        # null

            <http://stackoverflow.com/questions/2707322/what-is-null-in-java>
        */
        {
            // Objects that are not explicitly initialized are initialized to `null`.
            assert publicStaticString == null;

            // # NullPointerException
            // What you get for attempting to dereference a `null`.
            {
                String s = null;
                boolean fail = false;
                try {
                    s.toLowerCase();
                } catch (NullPointerException e) {
                    assert e.getClass() == NullPointerException.class;
                    fail = true;
                }
                assert fail;
            }
        }

        /*
        # Array

            JLS7 10

            Arrays are built-in into the language.
            Therefore, there is no Javadoc which documents them:
            you have to read the JLS.

            Arrays do however have class-like semantics.

            Arrays are not Generics nor do they implement Collection.

            Arrays have fixed size. Use `ArrayList` for variable size arrays.

            Do not confuse arrays with the Array class:
            http://docs.oracle.com/javase/7/docs/api/java/lang/reflect/Array.html
            which just contains some convenience methods to work with arrays.
        */
        {
            // # Declare arrays
            {
                /*
                Putting `[]` after `is` exactly the same as before.

                I find it bad style since it splits type infomation appart:
                type is `int[]`, not `int`.
                */
                {
                    int[] is;
                    int js[];
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
                <http://stackoverflow.com/questions/3790142/java-equivalent-of-pythons-rangeint-int>

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
                - `Inherited` - Marks another annotation to be inherited to subclasses of annotated class (by default annotations are not inherited to subclasses).
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
                }

                /*
                # Target

                    Limit what kinds of elements the annotation can be applied to.

                    Default: any element type.

                    Takes a list, so can have multiple values.
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

                <http://docs.oracle.com/javase/7/docs/api/java/lang/CharSequence.html>

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
            # String

                <http://docs.oracle.com/javase/7/docs/api/java/lang/String.html>

                Immutable. Therefore not a Collection, which would require `add`.
                It implements `CharSequence` instead.
            */
            {
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
                # Compare strings

                # == operator for strings

                # intern

                    Good explanation: http://stackoverflow.com/a/513839/895245

                    Unlike `+`, `==` is *not* magic for strings,
                    and like for Object compares instances instead of content.

                    What *is* magic, is that:

                    -   compile time constants resolve to `String.intern()`,
                        so `==` works for them.

                    -   `+` for compile time constants is done at compile time.

                        So `==` works in that case.

                        TODO is `+` the only operator on strings?

                    This behavior is specified by:
                    <http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.5>

                    This language design pattern is called "String interning",
                    and it can also exist for other immutable object literals like all literals in Ruby.

                    For you sanity, just don't rely on interning and always use `equals`.
                */

                /*
                # Concatenate strings

                # + operator for strings

                    The `+` operator for strings is magic:

                    - it is overloaded for an object, but Java does not have operator overload.

                    - it generates comile time constants for strings like it does for primitive types

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

                // Get character.
                {
                    assert "ab".charAt(0) == 'a';

                    // ERROR: array required, but string found.
                    // Brace magic is only for arrays.
                    // Furthermore, Strings are immutable,
                    // and brace notation invites mutability.
                    //assert "ab"[0] == "a";
                }

                /*
                # repr like in Python

                    Not by default: <http://stackoverflow.com/questions/1350397/java-equivalent-of-python-repr>
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

            /*
            # StringBuilder

                http://docs.oracle.com/javase/7/docs/api/java/lang/StringBuffer.html

                Mutable string: implements `CharSequence` which is the main interface of String.
            */
            {
                // # toString: convert to String

                // # ensureCapacity: allocate size

                // + does not work: the magic is just for String
                {
                    StringBuilder sb = new StringBuilder("ab");
                    //sb = sb + sb;
                }
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
            # Collection

                Important interface that has methods like `add`, `remove`, `equals` and `size`.

                <http://docs.oracle.com/javase/7/docs/api/java/util/Collection.html>

                Superinterface: `Iterable`.

                Notable subinterfaces: `List`, `Set`.

                Does not specify if ordered or unordered.

                Array does not implement `Collection`.

                Collection seems to be a well known non-Java specific computer science term:
                <http://en.wikipedia.org/wiki/Collection_%28abstract_data_type%29>

            # Collections vs arrays

                Arrays are a Java feature like primitives,
                that exists only to allow writing faster code
                by mapping directly to hardware representations.

                The tradeoff is that the array API is uglier and less flexible.

                If you are not super concerned about speed, use collections by default.

                <http://stackoverflow.com/questions/6100148/collection-interface-vs-arrays>
            */
            {
                /*
                # add Collection

                    Ensures that the collection contains the element.

                    Since collections can be either ordered or not,
                    this does not necessarily insert at the beginning or end.

                    Subinterfaces may specify that. E.g., `List` is ordered,
                    and List#add` always adds to the end of the list.
                */

                /*
                # Initialize collection

                    <http://stackoverflow.com/questions/1005073/initialization-of-an-arraylist-in-one-line>

                    There is no simple one liner to initialize a collection with given values.

                    The best way is likely to `add` them one by one.

                    A slightly less verbose possibility is the "double brace initialization idiom".
                */
                {
                    /*
                    # Double brace initialization idiom

                        Creates an anonymous inner class,
                        and adds an instance initializer to it.

                        Downside: for classes that implement serializable,
                        you need to add an explicit serialVersionUID or you will get a warning.
                        TODO why?

                        The main cause of this idiom is that `add`
                        does not return the modified collection itself, so you cannot chain
                        `add().add()` and pass the result to a method invocation.

                        Even C++ is more convenient on this point, with its initializer lists.
                    */
                    {
                        ArrayList<Integer> l = new ArrayList<Integer>() {
                            private static final long serialVersionUID = 1L;
                            {
                                add(0);
                                add(1);
                            }
                        };
                        ArrayList<Integer> l2 = new ArrayList<>();
                        l2.add(0);
                        l2.add(1);
                        assert l.equals(l2);

                        /*
                        If are fine with an immutable collections, see
                        `Collections` `singletonXXX()`, `emptyXXX` families and `nCopies.
                        */

                        /*
                        `Arrays.asList()` is a possibility
                        is you want multiple different elements and but are fine with fixed size.
                        */
                    }
                }

                /*
                # toArray

                    Creates a new array.
                */
                {
                    Collection<Integer> l = new ArrayList<>();
                    l.add(0);
                    l.add(1);
                    l.add(2);
                    assert Arrays.equals(l.toArray(), new Integer[]{0, 1, 2});
                }

                /*
                # equals Collection

                    Does not specify anything about the semantics besides Object's contract.

                    Inheriting interfaces however do.
                    Notably, `List` and `Set` say that their comparison
                    must be based on the `equals` of the contained elements.
                */

                /*
                # List

                    Interface: <api/java/util/List.html>

                    Superinterface: Collection.

                    The most important difference between `List` and `Collection`
                    is that `List` is ordered and `Iterable`.

                    Most common implementations:

                    - `LinkedList`
                    - `ArrayList`
                */
                {
                    /*
                    # equals (List)

                        Unlike Collection#equals, asserts that both lists have the same elements (a.equals(b))
                        and are in the same order.
                    */
                    {
                        List<Integer> l = new LinkedList<>();
                        l.add(1);
                        l.add(2);
                        List<Integer> l2 = new LinkedList<>();
                        l2.add(1);
                        l2.add(2);
                        List<Integer> l3 = new LinkedList<>();
                        l3.add(2);
                        l3.add(1);
                        assert  l.equals(l2);
                        assert !l.equals(l3);
                    }

                    /*
                    # indexOf

                    # lastIndexOf

                        Strings also have a version to search starting from a given index.

                        `Arrays` don't have a direct method, so just `Arrays.asList().indexOf()` it.
                        <http://stackoverflow.com/questions/4962361/where-is-javas-array-indexof>
                    */
                    {
                        List<Integer> l = new ArrayList<>();
                        l.add(0);
                        l.add(1);
                        l.add(0);
                        l.add(1);
                        l.add(0);
                        l.add(1);
                        l.add(0);
                        assert l.indexOf(1) == 1;
                        assert l.lastIndexOf(1) == 5;
                    }

                    /*
                    # LinkedList

                        Implements `List`.

                        Doubly linked list.

                        <api/java/util/LinkedList.html>
                    */
                    {
                        List<Integer> l = new LinkedList<>();
                        l.add(1);
                        l.add(2);
                        assert l.size() == 2;
                    }

                    /*
                    # ArrayList

                        Implements `List`.

                        Dynamically allocated array-backed list.

                        <api/java/util/ArrayList.html>

                    # Vector

                        Similar to ArrayList but synchronized.
                    */
                    {
                        ArrayList<Integer> l = new ArrayList<>();
                        l.add(1);
                        l.add(3);
                        assert l.size() == 2;

                        // ERROR: cannot find symbol.
                        // TODO why no `pop`?
                        //assert l.pop() == 2;
                    }

                    /*
                    # Stack

                        http://docs.oracle.com/javase/7/docs/api/java/util/Stack.html

                        TODO vs ArrayList?
                    */
                    {
                        Stack<Integer> s = new Stack<>();
                        s.add(1);
                        s.add(2);
                    }
                }
            }

            /*
            # Collections

                Static methods that do operations on classes that implement `Collection`.

                <http://docs.oracle.com/javase/7/docs/api/java/util/Collections.html>

                Some methods however require subintefaces of `Collection`, e.g. `sort` requires `List`,
                because `Collections` are not necessarily ordered, but `List` is.
            */
            {
                /*
                # sort Collections

                    Sort a collection inplace.

                    It's contents must implement `Comparable`.

                    The Javadoc says it's a mergesort similar to Python's.
                */
                {
                    List<Integer> l = new ArrayList<>();
                    l.add(2);
                    l.add(0);
                    l.add(1);
                    List<Integer> l2 = new ArrayList<>();
                    l2.add(0);
                    l2.add(1);
                    l2.add(2);
                    // Returns void
                    Collections.sort(l);
                    assert l.equals(l2);
                }

                /*
                # unmodifiableCollection

                # unmodifiableSet

                # unmodifiableMap

                # unmodifiableList

                    As of JDK9, those methods are implemented by returning private inner classes
                    that override the modifier methods to throw and exception:
                    http://hg.openjdk.java.net/jdk9/jdk9/jdk/file/f08705540498/src/java.base/share/classes/java/util/Collections.java#l4746

                    # TODO how exactly does that work, considering that it just returns a List?

                        Is there a UnmodifiableList class, or is it just some private implementation of it that gets returned?

                        Note that `Collections#unmodifiableCollection` gives you an unmodifiable collection.

                        Guava however does seem to have public immutable collections:
                        <http://stackoverflow.com/questions/5611324/whats-the-difference-between-collections-unmodifiableset-and-immutableset-of>
                */
                {
                    Collection<Integer> c = new ArrayList<>();
                    Collection<Integer> c2 = Collections.unmodifiableCollection(c);
                    boolean fail = false;
                    try {
                        c2.add(1);
                    } catch(UnsupportedOperationException e) {
                        fail = true;
                    }
                    assert fail;
                }

                /*
                # emptySet

                # emptyList

                # emptyMap

                # singleton

                # singletonList

                # singletonMap

                    Create immutable collections that contain one or 0 elements.

                    TODO what is the main application? Just reducing the abilities of the method that gets called?
                    <http://stackoverflow.com/questions/4801794/use-of-javas-collections-singletonlist>


                # nCopies

                    ImmutablelList with n copies of a given reference.

                    All elements are a single reference.

                    Only works for objects.
                */
                {
                }
            }

            /*
            # Arrays class

                Convenience static utilities for Arrays.

                <http://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html>
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
                }

                /*
                # indexOf for arrays

                    http://stackoverflow.com/questions/4962361/where-is-javas-array-indexof

                    Nope, first `Arrays.toList`.
                */
            }

            /*
            # Map

                Interface.

                Major implementations include `HashMap` and `TreeMap`,
                both of which inherit `AbstractMap`.

                Not a collection:
                http://stackoverflow.com/questions/2651819/why-doesnt-java-map-extends-collection

            # TreeMap

                Implements `Map`.

                Javadoc says it's a red-black tree.

                You can use a custom comparator to compare the entries without wrapping the key.

            # HashMap

                Implements `Map`.

                You cannot use a custom hash function without wrapping the key:
                http://stackoverflow.com/questions/5453226/java-need-a-hash-map-where-one-supplies-a-function-todo-the-hashing

            # HashTable

                Like `HashMap` but synchronized.

            # EnumMap

                Only for enum keys.

                TODO why is it faster?
            */
            {
                // Basic usage.
                {
                    Map<Integer,String> m = new TreeMap<>();
                    m.put(0, "zero");
                    m.put(1, "one");
                    assert m.get(0) == "zero";
                    assert m.get(1) == "one";
                }

                /*
                # put

                    . Returns the previous value for the key, null if none.
                */
                {
                    Map<Integer,Integer> m = new TreeMap<>();
                    assert(m.put(0, 0) == null);
                    assert(m.put(0, 1).equals(0));
                    assert(m.put(0, 2).equals(1));
                    //assert(m.put(1, 1).equals(1));
                }

                /*
                # entrySet

                # Iterate over map

                    http://stackoverflow.com/questions/46898/iterate-over-each-entry-in-a-map

                    In Java 8, there is also the `forEach` loop version.
                */
                {
                    //for (Map.Entry<String, String> entry : map.entrySet()) {
                        //System.out.println(entry.getKey() + "/" + entry.getValue());
                    //}
                }

                /*
                # values

                    Collection of the values. Not a copy.

                    Not a set because there can be duplicates.
                */

                /*
                # keySet

                    Set of all the keys.
                */
            }

            /*
            # Set

                Interface. Major implementations include `HashSet`, `TreeSet`, `EnumSet`.

                <http://docs.oracle.com/javase/7/docs/api/java/util/Set.html>
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

                <http://docs.oracle.com/javase/7/docs/api/java/lang/System.html>

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

                    Parameters that configure the JVM.

                    Used instead of command line arguments because they are more flexible. TODO how?

                    Predefined ones:
                    <http://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html>
                */
                {
                    /*
                    # getProperty

                        Get a single property.

                        #os.name

                            May include OS version, and upper case letters. Sample values:

                            - Ubuntu 14.04: "Linux"
                            - Windows XXX

                            The humanish nature of the output makes it harde to detect the OS:

                            http://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java

                        #version

                            http://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java
                    */
                    {
                        System.out.println("System.getProperty");
                        System.out.println("  file.separator = " + System.getProperty("file.separator"));
                        System.out.println("  java.io.tmpdir = " + System.getProperty("java.io.tmpdir"));
                        System.out.println("  java.library.path = " + System.getProperty("java.library.path"));
                        // #version
                        System.out.println("  java.version = " + System.getProperty("java.version"));
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

                    /*
                    # getProperties

                        Get all properties at once in a `Properties` object:
                        http://docs.oracle.com/javase/8/docs/api/java/util/Properties.html
                    */
                    {
                        assert System.getProperty("file.separator") ==
                               System.getProperties().getProperty("file.separator");
                    }

                    // Custom properties can be passed to the JVM with `-D'custom.property=value'
                    {
                        System.out.println("System.getProperty(\"custom.property\") = " +
                                            System.getProperty("custom.property"));
                    }

                    // TODO `file.separator` property vs `File.separator`?
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

                Generate a positive integer TODO

                For multi-threaded operations, prefer `ThreadLocalRandom`.
            */
            {
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
                }

                /*
                # File

                    http://docs.oracle.com/javase/7/docs/api/java/io/File.html#pathSeparatorChar

                    Many file and path related operations: existence, join,
                    permissions,remove, list, transversal convenience.
                */
                {
                    /*
                    # rmrf

                        http://stackoverflow.com/questions/779519/delete-files-recursively-in-java
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

                    -   line flush

                    -   not throw exceptions: sets an internal flag instead,
                        which you can access with check / setError methods.

                    -   buffered

                    -   create file streams without instanciating `FileOutputStream` directly
                        through the `String path` constructors.

                        But you can still wrap any `OutputStream` with the `OutputStream` constructor.

                    `System.out` and `err` are notable instances.

                    #  Line flushing

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

                    <http://docs.oracle.com/javase/7/docs/api/java/io/DataOutputStream.html>

                    Print binary, non-human readable, representations of primitives.

                # DataInputStream

                    <http://docs.oracle.com/javase/7/docs/api/java/io/DataInputStream.html>
                */
                {
                    int in = 0x00_01_02_03;

                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    DataOutputStream o = new DataOutputStream(b);
                    o.writeInt(in);
                    o.close();


                    byte[] out = b.toByteArray();
                    // The representation format is specified in the Javadoc,
                    // so we can assert it.
                    // But you will likely only use this if you are going
                    // to read the data back with `DataInputStream#readXXX` later on.
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

                /*
                # BufferedReader

                    Automagically prefetches reads larger chunks than immediately required.

                    Faster than a non-buffered reader if you are going to read the whole file anyways.

                    <http://docs.oracle.com/javase/7/docs/api/java/io/BufferedReader.html>

                    Is itself a reader, and simply acts as a wraper around another `Reader`:
                    http://docs.oracle.com/javase/7/docs/api/java/io/Reader.html

                    The most common reader to use wrap around is `FileReader`.
                */
                {
                    /*
                    # Read file line-by-line

                    # readLine

                        A line is considered to be terminated by any one of a line feed ('\n'),
                        a carriage return ('\r'), or a carriage return followed immediately by a linefeed.

                        Using BufferedReader + FileReader is the most common combo.

                        Readers must be used instead of the stream because this operation
                        is encoding dependant.
                    */
                    File f = File.createTempFile("aaa", null);
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    for (String line; (line = br.readLine()) != null;) {
                        // process the line.
                    }

                    // Another possibility. Worse because scope of `line` leaks out.
                    //String line;
                    //while ((line = br.readLine()) != null) {}

                    /*
                    Check if reader is at EOF: not possible without reading.
                    <http://stackoverflow.com/questions/3714090/how-to-see-if-a-reader-is-at-eof>
                    */

                    f.delete();
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

            `java.lang.X` classes contents are magic and get automatically imported.

            They contain basic utilities.

            Autoimport not happen for packages inside Java, e.g., `java.lang.reflect.Field`
            needs to be explicitly imported by the programmer.
        */
        {
            assert java.lang.Integer.class == Integer.class;

            /*
            # Math

                http://docs.oracle.com/javase/7/docs/api/java/lang/Math.html
            */
            {
                /*
                # random

                    Same as Random#nextDoubl(): Javadoc says that it creates a Random object, and caches it.

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

                <http://docs.oracle.com/javase/7/docs/api/java/nio/Buffer.html>

                Interface. Important implementation: `ByteBuffer`.

                Good tutorial:
                <http://tutorials.jenkov.com/java-nio/buffers.html>

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

                    <http://docs.oracle.com/javase/7/docs/api/java/nio/ByteBuffer.html>

                    There are two types of ByteBuffer: direct and indirect.

                    The direct ones may not be stored on the heap.

                    - <http://stackoverflow.com/questions/5670862/bytebuffer-allocate-vs-bytebuffer-allocatedirect>
                    - <http://examples.javacodegeeks.com/core-java/nio/bytebuffer/java-direct-bytebuffer-example>
                */
                {
                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(2);
                    ByteBuffer indirectByteBuffer = ByteBuffer.allocate(2);

                    assert byteBuffer.isDirect();
                    assert !indirectByteBuffer.isDirect();

                    // TODO
                    assert !byteBuffer.hasArray();

                    // TODO actually use them, and show that direct is faster.
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
        # System.exit

        # exit status
        */
        {
            System.out.println("ALL ASSERTS PASSED");
            System.exit(0);
            System.exit(1);
        }
    }
}
