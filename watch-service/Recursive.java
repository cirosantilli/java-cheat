import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;

public class Recursive {

    private static final boolean RECURSIVE = true;
    private static final String WATCH_DIR = "d";

    private static WatchService watchService;
    private static Map<WatchKey,Path> keys;
    private static boolean trace = false;

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }

    private static void register(Path dir) throws IOException {
        WatchKey key = dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                System.out.format("register: %s\n", dir);
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("update: %s -> %s\n", prev, dir);
                }
            }
        }
        keys.put(key, dir);
    }

    private static void registerAll(final Path start) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                throws IOException
            {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void main(String[] args) throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
        keys = new HashMap<WatchKey,Path>();

        {
            Path dir = Paths.get(WATCH_DIR);
            if (RECURSIVE) {
                registerAll(dir);
                System.out.println("Ready. Go create some files under: " + WATCH_DIR);
            } else {
                register(dir);
            }
        }

        trace = true;

        for (;;) {
            WatchKey key;
            try {
                key = watchService.take();
            } catch (InterruptedException x) {
                return;
            }

            Path dir = keys.get(key);
            if (dir == null) {
                // TODO how can this happen?
                System.err.println("WatchKey not recognized!!");
                continue;
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                // TODO what to do here?
                if (kind == OVERFLOW)
                    continue;

                // TODO remove this `ev` tmp variable.
                WatchEvent<Path> ev = cast(event);
                Path name = ev.context();

                System.out.format("%s: %s\n", kind.name(), name);

                // If a directory is created, and watching recursively, then
                // register it and its sub-directories
                Path child = dir.resolve(name);
                if (RECURSIVE && (kind == ENTRY_CREATE)) {
                    if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                        registerAll(child);
                    }
                }
            }

            // Reset key and remove from set if directory no longer accessible
            if (!key.reset()) {
                keys.remove(key);
                // All directories are inaccessible
                if (keys.isEmpty())
                    break;
            }
        }
    }
}
