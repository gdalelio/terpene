package org.geoint.terpene.task.trigger.file;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Simple watch file implementation, which notifies on any file created in this
 * (and optionally recursive) director[y|ies].
 *
 * Recursive directory monitoring is managed by the optionally provided
 * FileVisitor. By default, subdirectories are not watched.
 */
public class SimpleTriggerFileFilter implements WatchTriggerFileFilter {

    private final FileVisitor<Path> fileVisitor;

    public SimpleTriggerFileFilter() {
        fileVisitor = NonRecursiveVisitor.INSTANCE;
    }

    public SimpleTriggerFileFilter(FileVisitor<Path> recursiveFilter) {
        this.fileVisitor = recursiveFilter;
    }

    /**
     * Calls the provided {@link FileVisitor#preVisitDirectory(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
     * }
     * method to determine if the directory should be monitored for new files.
     *
     * @param p
     * @return false
     */
    @Override
    public boolean watchDirectory(Path p) {
        return false;
    }
//
//    /**
//     * Determines if the creation of the provided directory should trigger an
//     * task.
//     *
//     * The default implementation returns false - no trigger on directory
//     * creation.
//     *
//     * @param p
//     * @return
//     */
//    @Override
//    public boolean triggerNewDirectory(Path p) {
//        return false;
//    }

    /**
     * Calls the provided {@link FileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
     * }
     * method to determine if the new file should trigger a task.
     *
     * @param p
     * @return
     */
    @Override
    public boolean triggerNewFile(Path p) {
        try {
            return fileVisitor.visitFile(p,
                    Files.readAttributes(p, BasicFileAttributes.class)).equals(FileVisitResult.CONTINUE);
        } catch (IOException ex) {
            return false;
        }
    }
}
