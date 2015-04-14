import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;

/** Minimalistic example that runs only once. */
public class HelloWorld {

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }

    public static void main(final String[] args) throws InterruptedException, IOException {
        Path directory = Files.createTempDirectory("watch-service-hello-world");
        final WatchService watchService = FileSystems.getDefault().newWatchService();
        directory.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE);
        final Path p = directory.resolve(Paths.get("Hello World!"));
        for (int i = 0; i < 2; i++) {
            Files.createFile(p);
            Files.delete(p);
        }
        for (final WatchEvent<?> event : watchService.take().pollEvents()) {
            final WatchEvent.Kind kind = event.kind();
            System.out.format("%s:%d %s\n", kind.name(), event.count(), cast(event).context());
        }
        Files.delete(directory);
    }
}
