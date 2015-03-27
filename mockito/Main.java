import java.util.List;

import org.mockito.Mockito;

public class Main {
    public static void main(String[] args) {
        /*
        # verify

            Assert:

            - how many times a method was called
            - with which arguments
        */
        {
            // Mock creation
            List mockedList = Mockito.mock(List.class);

            // Using mock object - it does not throw any
            // "unexpected interaction" exception
            mockedList.add("one");
            mockedList.clear();

            // Check how many times a method was called and with with arguments.
            Mockito.verify(mockedList).add("one");
            Mockito.verify(mockedList).add("one");
            Mockito.verify(mockedList).add("one2");
            Mockito.verify(mockedList).clear();
        }

        /*
        # Verify any interaction

        # Verify arbitrary interaction

            http://stackoverflow.com/questions/11192187/is-it-possible-to-verify-arbitrary-interaction-using-mockito-in-a-compact-way

            Not possible.
        */
    }
}

