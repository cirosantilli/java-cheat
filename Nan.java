// https://stackoverflow.com/questions/2618059/in-java-what-does-nan-mean/55673220#55673220

import java.lang.Float;
import java.lang.Math;

public class Nan {
    public static void main(String[] args) {
        // Generate some NaNs.
        float nan = Float.NaN;
        float zero_div = 0.0f / 0.0f;
        float sqrt = (float)Math.sqrt(-1.0);
        float inf_minus = Float.POSITIVE_INFINITY - Float.POSITIVE_INFINITY;
        float inf_times = Float.POSITIVE_INFINITY * 0.0f;
        float quiet_nan1 = Float.intBitsToFloat(0x7fc00001);
        float quiet_nan2 = Float.intBitsToFloat(0x7fc00002);
        float signaling_nan1 = Float.intBitsToFloat(0x7fa00001);
        float signaling_nan2 = Float.intBitsToFloat(0x7fa00002);
        float nan_minus = -nan;

        // Double check that they are actually NaNs.
        assert Float.isNaN(nan);
        assert Float.isNaN(zero_div);
        assert Float.isNaN(sqrt);
        assert Float.isNaN(inf_minus);
        assert Float.isNaN(inf_times);
        assert Float.isNaN(quiet_nan1);
        assert Float.isNaN(quiet_nan2);
        assert Float.isNaN(signaling_nan1);
        assert Float.isNaN(signaling_nan2);
        assert Float.isNaN(nan_minus);

        // See what they look like.
        System.out.printf("nan            %f 0x%x\n", nan,            Float.floatToRawIntBits(nan));
        System.out.printf("zero_div       %f 0x%x\n", zero_div,       Float.floatToRawIntBits(zero_div));
        System.out.printf("sqrt           %f 0x%x\n", sqrt,           Float.floatToRawIntBits(sqrt));
        System.out.printf("inf_minus      %f 0x%x\n", inf_minus,      Float.floatToRawIntBits(inf_minus));
        System.out.printf("inf_times      %f 0x%x\n", inf_times,      Float.floatToRawIntBits(inf_times));
        System.out.printf("quiet_nan1     %f 0x%x\n", quiet_nan1,     Float.floatToRawIntBits(quiet_nan1));
        System.out.printf("quiet_nan2     %f 0x%x\n", quiet_nan2,     Float.floatToRawIntBits(quiet_nan2));
        System.out.printf("signaling_nan1 %f 0x%x\n", signaling_nan1, Float.floatToRawIntBits(signaling_nan1));
        System.out.printf("signaling_nan2 %f 0x%x\n", signaling_nan2, Float.floatToRawIntBits(signaling_nan2));
        System.out.printf("nan_minus      %f 0x%x\n", nan_minus,      Float.floatToRawIntBits(nan_minus));

        // NaN comparisons always fail.
        // Therefore, all tests that we will do afterwards will be just isNaN.
        assert !(1.0f < nan);
        assert !(1.0f == nan);
        assert !(1.0f > nan);
        assert !(nan == nan);

        // NaN propagate through most operations.
        assert Float.isNaN(nan + 1.0f);
        assert Float.isNaN(1.0f + nan);
        assert Float.isNaN(nan + nan);
        assert Float.isNaN(nan / 1.0f);
        assert Float.isNaN(1.0f / nan);
        assert Float.isNaN((float)Math.sqrt((double)nan));
    }
}
