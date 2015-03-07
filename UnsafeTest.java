import java.lang.reflect.Field;

import sun.misc.Unsafe;
// TODO. play with it.
import sun.misc.VM;

/*
#Unsafe

    Not documented officialy:
    <http://stackoverflow.com/questions/16819234/sun-misc-unsafe-documentation>

    Put in a separate file as it is not stable, and generates warnings.

    An unofficial API listing can be found at:
    <http://www.docjar.com/docs/api/sun/misc/Unsafe.html>

    Unstable API that may break on the Java versions.
    javac even gives a warning saying this if you import it.

    Tutorials:

    - <http://mishadoff.com/blog/java-magic-part-4-sun-dot-misc-dot-unsafe/>
    - <http://howtodoinjava.com/2013/10/19/usage-of-class-sun-misc-unsafe/>
*/
public class UnsafeTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe;

        // Access unsafe
        {
            /*
            In order to instanciate unsafe, you need to pass command line options to the JVM:

                java -Xbootclasspath:/usr/jdk1.7.0/jre/lib/rt.jar:. com.mishadoff.magic.UnsafeClient
            */
            {
                boolean fail = false;
                try {
                    unsafe = Unsafe.getUnsafe();
                } catch (SecurityException e) {
                    fail = true;
                }
                assert fail;
            }

            /*
            The alternative is to make the `theUnsafe` private field publicly visible.
            */
            {
                Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                unsafe = (Unsafe) f.get(null);
            }
        }

        // System information:
        {
            System.out.println("addressSize = " + unsafe.addressSize());
            System.out.println("pageSize = " + unsafe.pageSize());
        }

        System.out.println("ALL ASSERTS PASSED");
    }
}
