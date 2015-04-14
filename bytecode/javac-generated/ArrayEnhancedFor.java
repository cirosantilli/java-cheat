/**
http://stackoverflow.com/questions/3912765/iterator-for-array

Compare with {@link ArrayFor}. Why is this less efficient?
*/
public class ArrayEnhancedFor {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        for (int i : arr)
            System.out.println(i);
    }
}

