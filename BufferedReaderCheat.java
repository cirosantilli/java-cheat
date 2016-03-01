/*
# BufferedReader

    Automagically prefetches reads larger chunks than immediately required.

    Faster than a non-buffered reader if you are going to read the whole file anyways.

    http://docs.oracle.com/javase/7/docs/api/java/io/BufferedReader.html

    Is itself a reader, and simply acts as a wraper around another `Reader`:
    http://docs.oracle.com/javase/7/docs/api/java/io/Reader.html

    The most common reader to use wrap around is `FileReader`.
*/

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

public class BufferedReaderCheat {
    public static void main(String[] args) {
        /*
        # Read file line-by-line

        # readLine

            A line is considered to be terminated by any one of a line feed ('\n'),
            a carriage return ('\r'), or a carriage return followed immediately by a linefeed.

            Using BufferedReader + FileReader is the most common combo.

            Readers must be used instead of the stream because this operation
            is encoding dependant.
        */
        {
            BufferedReader br = new BufferedReader(new StringReader("ab\ncd\n"));
            try {
                assert(br.readLine().equals("ab"));
                assert(br.readLine().equals("cd"));
                assert(br.readLine() == null);
            } catch (IOException e) {}

            // Loop usage.
            {
                // Good method: line has small scope.
                /*
                for (String line; (line = br.readLine()) != null;) {
                    // line
                }
                */

                // Saner version.
                /*
                String line = null;
                do  {
                    line = br.readLine();
                } while (line != null);
                */

                // Another possibility.
                /*
                String line;
                while ((line = br.readLine()) != null) {}
                */
            }

            /*
            Check if reader is at EOF: not possible without reading.
            http://stackoverflow.com/questions/3714090/how-to-see-if-a-reader-is-at-eof
            */
        }
    }
}
