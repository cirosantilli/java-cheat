/*
# Collections

    Static methods that do operations on classes that implement `Collection`.

    http://docs.oracle.com/javase/7/docs/api/java/util/Collections.html

    Some methods however require subintefaces of `Collection`, e.g. `sort` requires `List`,
    because `Collections` are not necessarily ordered, but `List` is.
*/

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsCheat {
    public static void main(String[] args) {
        /*
        # sort Collections

            Sort a collection inplace.

            It's contents must implement `Comparable`.

            The Javadoc says it's a mergesort similar to Python's.
        */
        {
            List<Integer> l = new ArrayList<>();
            l.add(2);
            l.add(0);
            l.add(1);
            List<Integer> l2 = new ArrayList<>();
            l2.add(0);
            l2.add(1);
            l2.add(2);
            // Returns void
            Collections.sort(l);
            assert l.equals(l2);
        }

        /*
        # unmodifiableCollection

        # unmodifiableSet

        # unmodifiableMap

        # unmodifiableList

            As of JDK9, those methods are implemented by returning private inner classes
            that override the modifier methods to throw and exception:
            http://hg.openjdk.java.net/jdk9/jdk9/jdk/file/f08705540498/src/java.base/share/classes/java/util/Collections.java#l4746

            # TODO how exactly does that work, considering that it just returns a List?

                Is there a UnmodifiableList class, or is it just some private implementation of it that gets returned?

                Note that `Collections#unmodifiableCollection` gives you an unmodifiable collection.

                Guava however does seem to have public immutable collections:
                <http://stackoverflow.com/questions/5611324/whats-the-difference-between-collections-unmodifiableset-and-immutableset-of>
        */
        {
            Collection<Integer> c = new ArrayList<>();
            Collection<Integer> c2 = Collections.unmodifiableCollection(c);
            boolean fail = false;
            try {
                c2.add(1);
            } catch(UnsupportedOperationException e) {
                fail = true;
            }
            assert fail;
        }

        /*
        # emptySet

        # emptyList

        # emptyMap

        # singleton

        # singletonList

        # singletonMap

            Create immutable collections that contain one or 0 elements.

            TODO what is the main application? Just reducing the abilities of the method that gets called?
            <http://stackoverflow.com/questions/4801794/use-of-javas-collections-singletonlist>


        # nCopies

            ImmutablelList with n copies of a given reference.

            All elements are a single reference.

            Only works for objects.
        */
        {
        }
    }
}
