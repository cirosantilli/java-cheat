/*
# Exceptions
*/

import java.io.Closeable;

public class ExceptionCheat {
    public static void main(String[] args) throws Exception {
        /*
        # try

            Catches exceptions.
        */
        {
            /*
            # Closeable

            # try with resources

                Java 7 Project Coin feature.

                http://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html

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
                    assert CloseableClass.i == 0;
                    // Dummy usage to avoid warnings.
                    assert c != null;
                    assert c2 != null;
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

            /*
            # finally and return

                Finally executes even after a return in the try.
                But if you do return or break in the finally,
                then the previous return is ignored!
            */
            {
                // TODO get working
                assert finallyAndReturn() == -1;
            }
        }

        /*
        # OutOfMemoryError

            Memory allowed by the JVM run out.

            The maximum can be configured with command line options.

            http://docs.oracle.com/javase/7/docs/api/java/lang/OutOfMemoryError.html
        */

        /*
        # IllegalArgumentException

            Inherits from `RuntimeException`.

            Not intended to be declared, since they indicate programming errors
            that generally cannot be dealt with:
            http://stackoverflow.com/questions/5304098/should-i-put-throws-illegalargumentexception-at-the-function
        */
    }

    private static class CloseableClass implements Closeable {
        public static int i;
        @Override
        public void close() {
            CloseableClass.i += 1;
        }
    }

    private static void throwError() {
        throw new Error();
    }

    private static void throwRuntimeException() {
        throw new RuntimeException();
    }

    //private static void throwNoSuchFieldExceptionFail() {
        //throw new NoSuchFieldException();
    //}

    private static void throwNoSuchFieldException() throws NoSuchFieldException {
        throw new NoSuchFieldException();
    }

    private static void throwNoSuchFieldExceptionBase() throws Throwable {
        throw new NoSuchFieldException();
    }

    // ERROR: incompatible types.
    //private static void throwNoSuchFieldExceptionObject() throws Object {
        //throw new NoSuchFieldException();
    //}

    private static int declaresExceptionNotThrown() throws Exception {
        return 1;
    }

    private static int finallyAndReturn() {
    label:
        try {
            return 1;
        } finally {
            break label;
        }
        return -1;
    }
}
