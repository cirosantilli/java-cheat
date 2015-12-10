/*
# List

    Interface: https://docs.oracle.com/javase/7/docs/api/java/util/List.html

    Superinterface: Collection.

    The most important difference between `List` and `Collection`
    is that `List` is ordered and `Iterable`.

    Most common implementations:

    - `LinkedList`
    - `ArrayList`

    List basically only contains operations that `ArrayList`, can do well.

    # size

        O(1) on major implementations: Java chaches it:
        http://stackoverflow.com/questions/863469/what-is-the-time-complexity-of-a-size-call-on-a-linkedlist-in-java
*/

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ListCheat {
    public static void main(String[] args) {
        /*
        # equals (List)

            Unlike Collection#equals, asserts that both lists have the same elements (a.equals(b))
            and are in the same order.
        */
        {
            List<Integer> l = new LinkedList<>();
            l.add(1);
            l.add(2);
            List<Integer> l2 = new LinkedList<>();
            l2.add(1);
            l2.add(2);
            List<Integer> l3 = new LinkedList<>();
            l3.add(2);
            l3.add(1);
            assert  l.equals(l2);
            assert !l.equals(l3);
        }

        /*
        # indexOf

        # lastIndexOf

            Strings also have a version to search starting from a given index.

            Arrays don't have a direct method, so just `Arrays.asList().indexOf()` it.
            http://stackoverflow.com/questions/4962361/where-is-javas-array-indexof
        */
        {
            List<Integer> l = new ArrayList<>();
            l.add(0);
            l.add(1);
            l.add(0);
            l.add(1);
            l.add(0);
            l.add(1);
            l.add(0);
            assert l.indexOf(1) == 1;
            assert l.lastIndexOf(1) == 5;
        }

        /*
        # LinkedList

            Implements `List`, `Deque`.

            # Doubly linked

            The list is doubly linked:

            https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html

            This is basically mandatory because ListIterator has `previous()` and `hasPrevious()`.

        # ListIterator

            Adds many methods to Iterator, including `add`, `delete` and `previous`.

            http://stackoverflow.com/questions/2102499/iterating-through-a-list-in-reverse-order-in-java

        # Deque

            Supports operations on both ends of the queue.
        */
        {
            Deque<Integer> l = new LinkedList<>();
            l.add(1);
            l.add(2);
            assert l.size() == 2;
            assert l.pop() == 1;
            assert l.size() == 1;
        }

        /*
        # ArrayList

            Implements `List`.

            List basically only contains operations which `ArrayList` can do efficiently.

            Other operations that would require array rotation are in other interfaces
            like Deque and Queue.

            Dynamically allocated array-backed list.

            https://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html

        # Vector

            Similar to ArrayList but synchronized.
        */
        {
            ArrayList<Integer> l = new ArrayList<>();
            l.add(1);
            l.add(3);
            assert l.size() == 2;

            // ERROR: cannot find symbol.
            // TODO why no `pop`?
            //assert l.pop() == 2;
        }

        /*
        # Stack

            http://docs.oracle.com/javase/7/docs/api/java/util/Stack.html

            Derived from Vector, thus synchronized.

            If you want a regular stack, just use `LinkedList`.

            TODO: show it.
        */
        {
            Stack<Integer> s = new Stack<>();
            s.add(1);
            s.add(2);
        }
    }
}
