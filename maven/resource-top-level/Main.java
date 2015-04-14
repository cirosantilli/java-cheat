import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println(new String(Files.readAllBytes(Paths.get("resource"))));
    }
}
