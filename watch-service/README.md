# WatchService

Watch for changes on the filesystem.

- [HelloWorld.java](HelloWorld.java)
- [Main.java](Main.java)
- [Recursive.java](Recursive.java)
- [Overflow.java](Overflow.java)

## Usage

    make run

On another terminal:

    cd d
    mkdir d
    touch a b d/a
    rm a b d/a
    rmdir d

The first terminal shows:

    ENTRY_CREATE: d
    ENTRY_CREATE: a
    ENTRY_MODIFY: a
    ENTRY_CREATE: b
    ENTRY_MODIFY: b
    ENTRY_DELETE: a
    ENTRY_DELETE: b
    ENTRY_DELETE: d

Note how events under `d/d` were not considered: operation is non-recursive by default.

## Tutorial

-   register directories to the watcher service

-   each directory registration returns a key corresponding to that directory

-   `watchService.take` returns one of the previously registered keys which has an event. `poll` is the non-blocking version which may return `null`.

-   each key returns events only for the directory on which it was registered.

    You cannot get the directory from the key because `key.watchable()` returns a `Watchable`, so you have to keep a map of `key -> directory`

    The only JDK implementation of `Watchable` however is `Path`.

    TODO confirm: So registering the same path multiple times should return equal keys:

        final WatchService watchService = FileSystems.getDefault().newWatchService();
        final WatchKey key = Paths.get(WATCH_DIR).register(
                watchService,
                StandardWatchEventKinds.ENTRY_MODIFY);
        final WatchKey key2 = Paths.get(WATCH_DIR).register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
        assert key.equals(key2);

## Watch a single file

Not possible: the best you can do is filter the events.

<http://stackoverflow.com/questions/16251273/can-i-watch-for-single-file-change-with-watchservice-not-the-whole-directory>

TODO count
