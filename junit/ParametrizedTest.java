import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized;

/**
Each data in the list will get passed to the constructor.
<p>
Tests will get run once after each construction.
*/
@RunWith(Parameterized.class)
public class ParametrizedTest {

    private static final boolean DO_FAIL = false;

    @Parameters
    public static Collection<Object[]> data() {
        if (DO_FAIL) {
            return Arrays.asList(new Object[][] {
                {0, 0}, {1, 1}, {1, 2}
            });
        } else {
            return Arrays.asList(new Object[][] {
                {0, 0}, {1, 1}
            });
        }
    }

    private int i;
    private int j;

    public ParametrizedTest(int i, int j) {
        this.i = i;
        this.j= j;
    }

    @Test
    public void equal() {
        Assert.assertEquals(i, j);
    }
}
