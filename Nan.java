// https://stackoverflow.com/questions/2618059/in-java-what-does-nan-mean/55673220#55673220
/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import java.lang.Float;
import java.lang.Math;

public class Nan {
    public static void main(String[] args) {
        // Generate some NaNs.
        float nan            = Float.NaN;
        float zero_div_zero  = 0.0f / 0.0f;
        float sqrt_negative  = (float)Math.sqrt(-1.0);
        float log_negative   = (float)Math.log(-1.0);
        float inf_minus_inf  = Float.POSITIVE_INFINITY - Float.POSITIVE_INFINITY;
        float inf_times_zero = Float.POSITIVE_INFINITY * 0.0f;
        float quiet_nan1     = Float.intBitsToFloat(0x7fc00001);
        float quiet_nan2     = Float.intBitsToFloat(0x7fc00002);
        float signaling_nan1 = Float.intBitsToFloat(0x7fa00001);
        float signaling_nan2 = Float.intBitsToFloat(0x7fa00002);
        float nan_minus      = -nan;

        // Generate some infinities.
        float positive_inf   = Float.POSITIVE_INFINITY;
        float negative_inf   = Float.NEGATIVE_INFINITY;
        float one_div_zero   = 1.0f / 0.0f;
        float log_zero       = (float)Math.log(0.0);

        // Double check that they are actually NaNs.
        assert  Float.isNaN(nan);
        assert  Float.isNaN(zero_div_zero);
        assert  Float.isNaN(sqrt_negative);
        assert  Float.isNaN(inf_minus_inf);
        assert  Float.isNaN(inf_times_zero);
        assert  Float.isNaN(quiet_nan1);
        assert  Float.isNaN(quiet_nan2);
        assert  Float.isNaN(signaling_nan1);
        assert  Float.isNaN(signaling_nan2);
        assert  Float.isNaN(nan_minus);
        assert  Float.isNaN(log_negative);

        // Double check that they are infinities.
        assert  Float.isInfinite(positive_inf);
        assert  Float.isInfinite(negative_inf);
        assert !Float.isNaN(positive_inf);
        assert !Float.isNaN(negative_inf);
        assert one_div_zero == positive_inf;
        assert log_zero == negative_inf;

        // Double check infinities.

        // See what they look like.
        System.out.printf("nan            0x%08x %f\n", Float.floatToRawIntBits(nan           ), nan           );
        System.out.printf("zero_div_zero  0x%08x %f\n", Float.floatToRawIntBits(zero_div_zero ), zero_div_zero );
        System.out.printf("sqrt_negative  0x%08x %f\n", Float.floatToRawIntBits(sqrt_negative ), sqrt_negative );
        System.out.printf("log_negative   0x%08x %f\n", Float.floatToRawIntBits(log_negative  ), log_negative  );
        System.out.printf("inf_minus_inf  0x%08x %f\n", Float.floatToRawIntBits(inf_minus_inf ), inf_minus_inf );
        System.out.printf("inf_times_zero 0x%08x %f\n", Float.floatToRawIntBits(inf_times_zero), inf_times_zero);
        System.out.printf("quiet_nan1     0x%08x %f\n", Float.floatToRawIntBits(quiet_nan1    ), quiet_nan1    );
        System.out.printf("quiet_nan2     0x%08x %f\n", Float.floatToRawIntBits(quiet_nan2    ), quiet_nan2    );
        System.out.printf("signaling_nan1 0x%08x %f\n", Float.floatToRawIntBits(signaling_nan1), signaling_nan1);
        System.out.printf("signaling_nan2 0x%08x %f\n", Float.floatToRawIntBits(signaling_nan2), signaling_nan2);
        System.out.printf("nan_minus      0x%08x %f\n", Float.floatToRawIntBits(nan_minus     ), nan_minus     );
        System.out.printf("positive_inf   0x%08x %f\n", Float.floatToRawIntBits(positive_inf  ), positive_inf  );
        System.out.printf("negative_inf   0x%08x %f\n", Float.floatToRawIntBits(negative_inf  ), negative_inf  );
        System.out.printf("one_div_zero   0x%08x %f\n", Float.floatToRawIntBits(one_div_zero  ), one_div_zero  );
        System.out.printf("log_zero       0x%08x %f\n", Float.floatToRawIntBits(log_zero      ), log_zero      );

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
