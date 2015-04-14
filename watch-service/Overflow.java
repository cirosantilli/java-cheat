import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;
import java.util.List;

public class Overflow {

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }

    public static void main(final String[] args) throws InterruptedException, IOException {
        int nfiles;
        if (args.length > 0)
            nfiles = Integer.parseInt(args[0]);
        else
            nfiles = 10_000;
        Path directory = Files.createTempDirectory("watch-service-overflow");
        final WatchService watchService = FileSystems.getDefault().newWatchService();
        directory.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE);
        final Path p = directory.resolve(Paths.get("Hello World!"));
        for (int i = 0; i < nfiles; i++) {
            Files.createFile(p);
            Files.delete(p);
        }
        List<WatchEvent<?>> events = watchService.take().pollEvents();
        System.out.println("Number of events: " + events.size());
        for (final WatchEvent<?> event : events) {
            if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
                System.out.println("Overflow.");
                return;
            }
        }
        System.out.println("No overflow.");
        Files.delete(directory);
    }
}
