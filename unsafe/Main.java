import java.lang.reflect.Field;

import sun.misc.Unsafe;
import sun.misc.VM;

final public class Main {

    private static Unsafe unsafe;

    /* Access unsafe */
    static {
        /*
        In order to instanciate unsafe, you need to pass command line options to the JVM:

            java -Xbootclasspath:/usr/jdk1.7.0/jre/lib/rt.jar:. Main

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
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (Exception e) {}
    }

    private static Unsafe getUnsafe() {
        return unsafe;
    }

    private static long normalize(int value) {
        if (value >= 0) return value;
        return (~0L >>> 32) & value;
    }

    /* Get address of given object */
    private static long getAddress(Object obj) {
        return unsafe.getLong(
                    // There is no way to get the address of variables direclty.
                    // We need to wrap it into an array or object to do it.
                    new Object[] {obj},
                    (long)getUnsafe().arrayBaseOffset(Object[].class));
    }

    /* Get the object whose address starts at the given address. */
    private static Object fromAddress(final long address) {
        final Object[] array = new Object[1];
        getUnsafe().putLong(
                array,
                (long)getUnsafe().arrayBaseOffset(Object[].class),
                address);
        return array[0];
    }

    /* Copy obj to off-heap memory, and return the off-heap copy. */
    private static Object offheapCopy(final Object obj) {
        final long size = Sizeof.sizeof(obj);
        final long address = getUnsafe().allocateMemory(size);
        getUnsafe().copyMemory(getAddress(obj), address, size);
        return fromAddress(address);
    }

    private static void freeOffheap(final Object obj) {
        getUnsafe().freeMemory(getAddress(obj));
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // System information.
        {
            System.out.println("addressSize = " + getUnsafe().addressSize());
            System.out.println("pageSize = " + getUnsafe().pageSize());
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
                long startIndex = getUnsafe().allocateMemory(size);
                getUnsafe().putInt(startIndex, i0);
                getUnsafe().putInt(startIndex + 4, i1);
                assert getUnsafe().getInt(startIndex) == i0;
                assert getUnsafe().getInt(startIndex + 4) == i1;
                getUnsafe().freeMemory(startIndex);
            }

            /*
            Objects.

                Handling objects is more complicated.

                The main problem is that it's hard to determine the object's size.

                http://mishadoff.com/blog/java-magic-part-4-sun-dot-misc-dot-unsafe/
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
                        public int sum() {
                            return this.i + this.j;
                        }
                        public C(int i, int j) {
                            this.i = i;
                            this.j = j;
                        }
                    }

                    C cOffHeap = null;

                    // Attempt 1
                    {
                        // TODO this method segfaults
                        //cOffHeap = (C)offheapCopy(new C(1, 3));
                    }

                    // Attempt 2
                    {
                        class Pointer {
                            Object pointer;
                        }
                        C c = new C(1, 2);
                        long size = Sizeof.sizeof(c);
                        long offheapPointer = getUnsafe().allocateMemory(size);
                        getUnsafe().copyMemory(
                                c,
                                0,
                                // Means that the destination is given by the absolute address.
                                null,
                                offheapPointer,
                                size
                        );

                        Pointer p = new Pointer();
                        getUnsafe().putLong(
                                p,
                                getUnsafe().objectFieldOffset(Pointer.class.getDeclaredField("pointer")),
                                offheapPointer);
                        cOffHeap = (C)p.pointer;
                    }


                    //assert c.sum() == 3;
                    //freeOffheap(c);
                }

            }

            // SIGSEGV
            //assert getUnsafe().getInt(0) == i1;

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
