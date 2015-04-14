import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.StandardWatchEventKinds;

/** Minimalistic example that watches a single directory forever. */
public class Main {

    private static final String WATCH_DIR = "d";

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }

    public static void main(final String[] args) throws InterruptedException, IOException {
        final WatchService watchService = FileSystems.getDefault().newWatchService();
        Paths.get(WATCH_DIR).register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
        for (;;) {
            final WatchKey key = watchService.take();
            for (final WatchEvent<?> event : key.pollEvents()) {
                final WatchEvent.Kind kind = event.kind();
                // TODO
                if (kind == StandardWatchEventKinds.OVERFLOW) continue;
                System.out.format("%s: %s\n", kind.name(), cast(event).context());
            }
            key.reset();
        }
    }
}
