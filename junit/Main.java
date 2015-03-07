import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Main {

    /*
    #static fields

        Avoid using static variables in real test suites,
        as they stay in the JVM for the entire duration of the tests,
        and may overload the memory of later tests.
    */
    private static final boolean DO_FAIL = false;

    int beforeInt = 0;
    int constructorInt = 0;

    @Test
    public void pass() {
    }

    @Test
    public void asert() {
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

    // Assert that an exception is thrown.
    @Test(expected=IllegalArgumentException.class)
    public void testIndexOutOfBoundsException() {
        throw new IllegalArgumentException();
    }

    @Test
    public void fail() {
        if (DO_FAIL)
            Assert.assertEquals(0, 1);
    }

    // Exactly the same as a failing assert statement,
    // but the assert statement requires `-ea` to be evaluated,
    // so it is better to use JUnit's assertion.
    // http://stackoverflow.com/questions/2966347/assert-vs-junit-assertions
    @Test
    public void failThrow() {
        if (DO_FAIL)
            throw new AssertionError();
    }

    @Test
    public void failAssert() {
        if (DO_FAIL)
            assert false;
    }

    /*
    #Before

        Gets run before every test, after the constructor of the class is called.

        You can therefore initialize member variables from it.

        Why not use the constructor:
        <http://stackoverflow.com/questions/3648712/setup-teardown-before-after-why-we-need-them-in-junit>

        - symmetry with `@After`, for which there is no destructor

    #Constructor

        The constructor gets run once before every `@Test`.

    #After

        TODO run once at the end of every `@Test`.

        The major application of this is to free non-memory resources like files and threads,
        since the constructor gets called once for every `@Test`

    #AfterClass

    #BeforeClass

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
}
