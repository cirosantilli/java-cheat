import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import com.google.common.base.Joiner;

/**
 * Javadoc for Main.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Main");

        // By default runs from the same directory where the pom is located.
        System.out.println("cwd = " + Paths.get("").toAbsolutePath().toString());

        // TODO make -ea work.
        assert false;

        // Dependencies
        if (!Joiner.on(" ").join("a", "b").equals("a b")) {
            throw new AssertionError();
        }

        // Resources. TODO how to read?
        // http://stackoverflow.com/questions/19916845/cant-access-to-files-in-resources-directory-with-maven-with-eclipse-ide
        //if (!Arrays.equals(new byte[]{'a'}, Files.readAllBytes(getClass().getResource("/a")))) {
            //throw new AssertionError();
        //}
    }
}
