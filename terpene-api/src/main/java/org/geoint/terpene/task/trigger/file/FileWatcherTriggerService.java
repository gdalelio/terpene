package org.geoint.terpene.task.trigger.file;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.terpene.task.trigger.TaskTriggerService;
import org.geoint.terpene.task.trigger.TriggerEvent;

/**
 * Watches a specified root directory for new files, using an optional
 * {@link WatchTriggerFileFilter} to determine if a new file triggers a task to
 * run
 */
public class FileWatcherTriggerService extends TaskTriggerService<Path> {

    private final Path root;
    private final WatchTriggerFileFilter filter;
    private final Map<WatchKey, Path> watchedDirs = new HashMap<>();
    private WatchService watcher;
    private final static Logger logger = Logger.getLogger("harpoon.trigger.fileWatcher");

    public FileWatcherTriggerService(Path root, WatchTriggerFileFilter filter) {
        this.root = root;
        this.filter = filter;
    }

    @Override
    protected void start() throws IOException {
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Starting file watcher service on "
                    + "root directory {0}", root);
        }

        watcher = root.getFileSystem().newWatchService();
        watch(root);

        //monitor for events
        while (!state.isDone()) {

            WatchKey key = null;
            try {
                key = watcher.take();
                Path dir = watchedDirs.get(key);

                if (dir == null) {
                    logger.warning("Unknown watchkey, unable to trigger event.");
                    continue;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    Kind kind = event.kind();
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path name = ev.context();
                        Path child = dir.resolve(name);

                        try {
                            if (Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS) && watchDir(child)) {
                                watch(child);
                            } else {
                                if (triggerOnFile(child)) {
                                    trigger(child);
                                }
                            }
                        } catch (IOException ex) {
                            logger.log(Level.SEVERE, "Unable to initialize "
                                    + "monitoring directory, some files, specifically "
                                    + child.toAbsolutePath().toString()
                                    + " may not trigger.", ex);
                        }
                    }
                }
            } catch (InterruptedException ex) {
                Thread.interrupted();
                continue;
            } finally {
                if (key != null) {
                    if (!key.reset()) {
                        //key is invalid
                        watchedDirs.remove(key);
                    }
                }
            }
        }
    }

    private void trigger(Path p) {
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Trigger on file {0}",
                    p.toAbsolutePath().toString());
        }
        trigger(new TriggerEvent(p));
    }

    private boolean triggerOnFile(Path f) {
        boolean trigger = filter.triggerNewFile(f);
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Trigger on file: {0}", trigger);
        }
        return trigger;
    }

    private boolean watchDir(Path f) {
        boolean watch = (f.equals(root) || filter.watchDirectory(f));
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Watch directory: {0}", watch);
        }
        return watch;
    }

    private void watch(final Path p) throws IOException {
        Files.walkFileTree(p, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path f, BasicFileAttributes bfs) {
                if (triggerOnFile(f)) {
                    trigger(f);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path f, BasicFileAttributes bfs) {
                if (watchDir(f)) {
                    try {
                        WatchKey k = f.register(watcher,
                                StandardWatchEventKinds.ENTRY_CREATE);
                        watchedDirs.put(k, f);
                    } catch (IOException ex) {
                        logger.log(Level.WARNING, "Unable to watch directory "
                                + f.toAbsolutePath().toString(), ex);
                    }
                    return FileVisitResult.CONTINUE;
                }
                return FileVisitResult.SKIP_SUBTREE;
            }
        });
    }
}
