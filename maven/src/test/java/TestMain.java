import org.junit.Assert;
import org.junit.Test;

import com.company.app.Main;

public class TestMain {
    @Test
    public void asert() {
        Assert.assertEquals(0, 0);
        //Assert.assertEquals(0, 1);
        // Test that the import worked.
        Assert.assertEquals(0, Main.i);
    }
}
