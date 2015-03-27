import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.rules.ExpectedException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class Main {

    /*
    # Static fields

        Avoid using static variables in real test suites,
        as they stay in the JVM for the entire duration of the tests,
        and may overload the memory of later tests.

        One possibility is to explicitly se them to `null` at `@ClassAfter`
        if you absolutely need to use them.

        This is not necessary for instance variables,
        which makes them more convenient.
    */
    private static final boolean DO_FAIL = false;

    int beforeInt = 0;
    int constructorInt = 0;

    @Test
    public void pass() {}

    @Test
    public void asert() {
        Assert.assertFalse(false);
        Assert.assertTrue(true);
        Assert.assertNull(null);
        Assert.assertEquals(0, 0);

        // ==
        Object o = new Object();
        Assert.assertSame(o, o);
        Assert.assertNotSame(new Object(), new Object());

        Assert.assertArrayEquals(new int[]{0, 1}, new int[]{0, 1});
        // ArrayEquals with delta of `0.3`.
        // Delta is taken per element.
        Assert.assertArrayEquals(
            new double[]{0.0, 0.0},
            new double[]{0.2, 0.2},
            0.3
        );
    }

    /*
    # Exception

        Assert that an exception is thrown.
        http://stackoverflow.com/questions/156503/how-do-you-assert-that-a-certain-exception-is-thrown-in-junit-4-tests/24621006#24621006

        Methods:

        - `try` `catch` `fail()`
        - `@Test` annotation `exception` parameter
        - `ExpectedException` `@Try`
        - Java 8 lambdas and `assertThrown`
    */

        /*
        # expected
        */
        {
            try {
                // `if` to make the following statement accessible,
                // otherwise we have a compile time error.
                if (true)
                    throw new IllegalArgumentException();
                Assert.fail("Not thrown.");
            } catch(IllegalArgumentException e) {}
        }

        /*
        # expected @Test annotation parameter

            Assert that an exception is thrown anywhere in the method.

            `ExceptionExpected` is better, as it determines:

            -   more precisely starting from where the exception has to be thrown,
                while `expected` acts on the entire method

            -   can also assert properties of the exception,
                like message through `.expectMessage`.
        */
        @Test(expected=IllegalArgumentException.class)
        public void testExceptionExpectedAnnotationParameter() {
            throw new IllegalArgumentException();
        }

        // ExpectedException
        @Rule
        public ExpectedException expectedException = ExpectedException.none();

        @Test
        public void testExceptionExpectedExceptionRule() {

            // Fail: too early.
            //throw new IllegalArgumentException();

            expectedException.expect(IllegalArgumentException.class);

            // Would fail without this.
            throw new IllegalArgumentException();

            // It is not possible to undo the `exception.expect`,
            // because if an exception throws above, then the test ends.
            // You need try catch fail here.
            //throw new IllegalArgumentException();
        }

    @Test
    public void fail() {
        if (DO_FAIL)
            Assert.assertEquals(0, 1);
    }

    /*
    Exactly the same as a failing assert statement,
    but the assert statement requires `-ea` to be evaluated,
    so it is better to use JUnit's assertion:
    http://stackoverflow.com/questions/2966347/assert-vs-junit-assertions
    */
    @Test
    public void failThrow() {
        if (DO_FAIL)
            throw new AssertionError();
    }

    /*
    # fail

        http://stackoverflow.com/questions/3869954/whats-the-actual-use-of-fail-in-junit-test-case

        Applications:

        -   mark a TODO in a test that is being written.

        -   fail if a test does not raise the exception it should.

            In JUnit 4 mostly replaced by the `expected` `@Test` annotation parameter
            and `ExpectedException` and Java 8 with lambdas.
    */
    @Test
    public void failFail() {
        if (DO_FAIL)
            Assert.fail();
    }

    @Test
    public void failAssert() {
        if (DO_FAIL)
            assert false;
    }

    /*
    # Before

        Gets run before every test, after the constructor of the class is called.

        You can therefore initialize member variables from it.

        Why not use the constructor:
        <http://stackoverflow.com/questions/3648712/setup-teardown-before-after-why-we-need-them-in-junit>

        - symmetry with `@After`, for which there is no destructor

        On overload, only the derived class before is called.
        If there is no overload, superclass `@Before`s are called first, then base.

    # Constructor

        The constructor gets run once before every `@Test`.

    # After

        Run once at the end of every `@Test`.

        The major application of this is to free non-memory resources like files and threads,
        since the constructor gets called once for every `@Test`

    # AfterClass

    # BeforeClass

        Application: turn on and off resources that take a lot of time to startup and shutdown.
    */
    @Before
    public void before() {
        this.beforeInt = 1;
    }

    @Test
    public void testBefore() {
        Assert.assertEquals(beforeInt, 1);

        this.constructorInt += 1;
        Assert.assertEquals(beforeInt, 1);
    }

    /*
    # Assume

        If false, skip the test.

        Works by throwing a special exception that tells JUnit to skip the test.
    */
    @Test
    public void testAssume() {
        Assume.assumeTrue(false);
        Assert.assertEquals(0, 1);
    }

    /*
    # Pending test

        http://stackoverflow.com/questions/14341277/is-there-a-general-way-to-mark-a-junit-test-as-pending

        Either:

        - `Assume`
        - `@Ignore`

        But neither shows up proeminently on the output of the tests.
    */

    /*
    # Ignore

        The test shows as an `I` on the short output, and does not count towards the test totals.

        TODO In a big test suite, will it go unseen?
    */
    @Ignore
    @Test
    public void testIgnore() {
        Assert.fail();
    }

    /*
    # Non-deterministic tests

        Welcome to hell :)

        Language agnostic article by Martin Fowler:
        http://martinfowler.com/articles/nonDeterminism.html

        `System.time`: mock it:

        - http://stackoverflow.com/questions/17398279/is-it-possible-to-freeze-system-currenttimemillis-for-testing
        - http://stackoverflow.com/questions/2001671/override-java-system-currenttimemillis-for-testing-time-sensitive-code

        Threads: no solution.
    */

}
