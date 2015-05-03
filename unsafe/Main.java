import java.lang.reflect.Field;

import sun.misc.Unsafe;
import sun.misc.VM;

public class Main {

    public static long sizeof(Object o) {
        // TODO implement this. It is hard.
        // For now we just return a huge value that will be enough for our simple tests,
        // while still fitting into memory.
        return 1024L;
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe;

        // Access unsafe
        {
            /*
            In order to instanciate unsafe, you need to pass command line options to the JVM:

                java -Xbootclasspath:/usr/jdk1.7.0/jre/lib/rt.jar:. com.mishadoff.magic.UnsafeClient

            We won't do that here for now.
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

        // System information.
        {
            System.out.println("addressSize = " + unsafe.addressSize());
            System.out.println("pageSize = " + unsafe.pageSize());
        }


        /*
        # Direct memory allocation

        # allocateMemory

        # freeMemory

            This memory will not be garbage collected, so like with malloc you can:

            - create memory leaks
            - segfault the VM on illegal access

            The advantage is that you won't lose time with garbage collection,
            which may be crucial if objects will live in memory for very long.

            You have to keep track of memory and call `freeMemory` on it after usage.

        # getInt

        # putInt
        */
        {
            // Primitives.
            {
                int i0 = 0x0001_0002;
                int i1 = 0x1000_2000;
                long size = 8;
                long startIndex = unsafe.allocateMemory(size);
                unsafe.putInt(startIndex, i0);
                unsafe.putInt(startIndex + 4, i1);
                assert unsafe.getInt(startIndex) == i0;
                assert unsafe.getInt(startIndex + 4) == i1;
                unsafe.freeMemory(startIndex);
            }

            /*
            Objects.

                Handling objects is more complicated.

                The main problem is that it's hard to determine the object's size.
            */
            {
                /*
                # sizeof

                    This will consider only Unsafe specific methods.

                    http://stackoverflow.com/questions/52353/in-java-what-is-the-best-way-to-determine-the-size-of-an-object

                    - read into an internal field of the memory representation that contains that data:
                        https://highlyscalable.wordpress.com/2012/02/02/direct-memory-access-in-java/

                    - iterate all fields, and make a worst case estimate with `unsafe.objectFieldOffset`
                        http://java.dzone.com/articles/understanding-sunmiscunsafe?page=0,1
                */
                {
                }

                // Example once sizeof is given.
                {
                    class C {
                        public int i;
                        public int j;
                        public C(int i, int j) {
                            this.i = i;
                            this.j = j;
                        }
                    }

                    C c0 = new C(0, 1);
                    C c1 = new C(2, 3);

                    long size0 = sizeof(c0);
                    long size1 = sizeof(c1);
                    long startIndex = unsafe.allocateMemory(size0 + size1);
                    unsafe.putInt(startIndex, i0);
                    unsafe.putInt(startIndex + 4, i1);
                    assert unsafe.getInt(startIndex) == i0;
                    assert unsafe.getInt(startIndex + 4) == i1;
                    unsafe.freeMemory(startIndex);
                }

            }

            // SIGSEGV
            //assert unsafe.getInt(0) == i1;

            /*
            # setMemory
            */
            {
                // Set all bytes to given byte.
                //unsafe.setMemory(startIndex, size, (byte)0);
            }
        }

        System.out.println("ALL ASSERTS PASSED");
    }
}
