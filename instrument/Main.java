final public class Main {
    public static void main(String [] args) {
        System.out.println("Object");
        System.out.println(Sizeof.sizeof(new Object()));

        System.out.println("/\"\"");
        System.out.println(Sizeof.sizeof(""));

        System.out.println("/\"abc\"");
        System.out.println(Sizeof.sizeof("abc"));

        System.out.println("int[0]");
        System.out.println(Sizeof.sizeof(new int[0]));

        System.out.println("int[10]");
        System.out.println(Sizeof.sizeof(new int[10]));

        class OneInt {
            public int i;
        }
        System.out.println("OneInt");
        System.out.println(Sizeof.sizeof(new OneInt()));

        class TwoInts {
            public int i;
            public int j;
        }
        System.out.println("TwoInts");
        System.out.println(Sizeof.sizeof(new TwoInts()));

        class IntArray0 {
            int[] i = new int[10];
        }
        System.out.println("IntArray0");
        System.out.println(Sizeof.sizeof(new IntArray0()));

        class IntArray10 {
            int[] i = new int[10];
        }
        System.out.println("IntArray10");
        System.out.println(Sizeof.sizeof(new IntArray10()));
    }
}
