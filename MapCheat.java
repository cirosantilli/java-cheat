/*
# Map

    Interface.

    Major implementations include `HashMap` and `TreeMap`,
    both of which inherit `AbstractMap`.

    *Not* a collection:
    http://stackoverflow.com/questions/2651819/why-doesnt-java-map-extends-collection

# TreeMap

    Implements `Map`.

    Javadoc says it's a red-black tree.

    You can use a custom comparator to compare the entries without wrapping the key.

# HashMap

    Implements `Map`.

    You cannot use a custom hash function without wrapping the key:
    http://stackoverflow.com/questions/5453226/java-need-a-hash-map-where-one-supplies-a-function-todo-the-hashing

# HashTable

    Like `HashMap` but synchronized.

# EnumMap

    Only for enum keys.

    TODO why is it faster?
*/

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MapCheat {
    public static void main(String[] args) {
        // Basic usage.
        {
            Map<Integer,String> m = new TreeMap<>();
            m.put(0, "zero");
            m.put(1, "one");
            assert m.get(0).equals("zero");
            assert m.get(1).equals("one");
            assert m.get(2) == null;
        }

        /*
        # put

            Returns the previous value for the key, null if none.
        */
        {
            Map<Integer,Integer> m = new TreeMap<>();
            assert m.put(0, 0) == null;
            assert m.put(0, 1).equals(0);
            assert m.put(0, 2).equals(1);
            //assert(m.put(1, 1).equals(1));
        }

        /*
        # entrySet

        # Iterate over map

            http://stackoverflow.com/questions/46898/iterate-over-each-entry-in-a-map

            In Java 8, there is also the `forEach` loop version.
        */
        {
            //for (Map.Entry<String, String> entry : map.entrySet()) {
                //System.out.println(entry.getKey() + "/" + entry.getValue());
            //}

            /*
            The entrySet is backed by the map. Modifications to it modify the map.
            */
            {
                Map<Integer,Integer> m = new TreeMap<>();
                assert m.put(1, -1) == null;
                assert m.put(2, -2) == null;
                Set<Integer> entrySet = m.keySet();
                Iterator<Integer> it = entrySet.iterator();
                while (it.hasNext())
                    if (it.next() == 1)
                        it.remove();
                assert m.get(2) == -2;
                assert m.size() == 1;
            }
        }

        /*
        # values

            Collection of the values. Not a copy.

            Not a set because there can be duplicates.
        */

        /*
        # keySet

            Set of all the keys.
        */
    }
}
