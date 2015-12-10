/*
# Collection

    Important interface that has methods like `add`, `remove`, `equals` and `size`.

    http://docs.oracle.com/javase/7/docs/api/java/util/Collection.html

    Superinterface: `Iterable`.

    Notable subinterfaces: `List`, `Set`.

    Does not specify if ordered or unordered.

    Array does not implement `Collection`.

    Collection seems to be a well known non-Java specific computer science term:
    http://en.wikipedia.org/wiki/Collection_%28abstract_data_type%29

# Collections vs arrays

    Arrays are a Java feature like primitives,
    that exists only to allow writing faster code
    by mapping directly to hardware representations.

    The tradeoff is that the array API is uglier and less flexible.

    If you are not super concerned about speed, use collections by default.

    http://stackoverflow.com/questions/6100148/collection-interface-vs-arrays
*/

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

public class CollectionCheat {
    public static void main(String[] args) {
        /*
        # add Collection

            Ensures that the collection contains the element.

            Since collections can be either ordered or not,
            this does not necessarily insert at the beginning or end.

            Subinterfaces may specify that. E.g., `List` is ordered,
            and List#add` always adds to the end of the list.
        */
        {
            /*
            Return value: true if the collection was changed, false otherwise.

            E.g. for List it is always true because duplicates are allowed.

            But for Set it can be false since duplicates are not allowed.
            */
            {
                Collection<Integer> c = new TreeSet<Integer>();
                assert c.add(1);
                assert !c.add(1);
            }
        }

        /*
        # Initialize collection

            http://stackoverflow.com/questions/1005073/initialization-of-an-arraylist-in-one-line

            There is no simple one liner to initialize a collection with given values.

            The best way is likely to `add` them one by one.

            A slightly less verbose possibility is the "double brace initialization idiom".
        */
        {
            /*
            # Double brace initialization idiom

                Creates an anonymous inner class,
                and adds an instance initializer to it.

                Downside: for classes that implement serializable,
                you need to add an explicit serialVersionUID or you will get a warning.
                TODO why?

                The main cause of this idiom is that `add`
                does not return the modified collection itself, so you cannot chain
                `add().add()` and pass the result to a method invocation.

                Even C++ is more convenient on this point, with its initializer lists.
            */
            {
                ArrayList<Integer> l = new ArrayList<Integer>() {
                    private static final long serialVersionUID = 1L;
                    {
                        add(0);
                        add(1);
                    }
                };
                ArrayList<Integer> l2 = new ArrayList<>();
                l2.add(0);
                l2.add(1);
                assert l.equals(l2);

                /*
                If are fine with an immutable collections, see
                `Collections` `singletonXXX()`, `emptyXXX` families and `nCopies.
                */

                /*
                `Arrays.asList()` is a possibility
                is you want multiple different elements and but are fine with fixed size.
                */
            }
        }

        /*
        # toArray

            Creates a new array.
        */
        {
            Collection<Integer> l = new ArrayList<>();
            l.add(0);
            l.add(1);
            l.add(2);
            assert Arrays.equals(l.toArray(), new Integer[]{0, 1, 2});
        }

        /*
        # equals Collection

            Does not specify anything about the semantics besides Object's contract.

            Inheriting interfaces however do.
            Notably, `List` and `Set` say that their comparison
            must be based on the `equals` of the contained elements.
        */

        /*
        # isEmpty
        */
        {
            Collection<Integer> c = new ArrayList<Integer>();
            assert c.isEmpty();
            c.add(1);
            assert !c.isEmpty();
            c.remove(1);
            assert c.isEmpty();
        }
    }
}
