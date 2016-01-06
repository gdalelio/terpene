package org.geoint.terpene.task.trigger.file;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Indicates to not watch/trigger on the contents of the prompted directory.
 *
 * This is implemented by simply overridding the 
 * {@link SimpleFileVisitor#preVisitDirectory(java.lang.Object, java.nio.file.attribute.BasicFileAttributes) }
 * method, returning {@link FileVisitResult#SKIP_SUBTREE}.
 *
 * Due to its simplified implementation, the NonRecursiveVisitor is implemented
 * as a Singleton, and is thread safe. Simply retrieve the instance by the
 * public field INSTANCE.
 */
public final class NonRecursiveVisitor extends SimpleFileVisitor<Path> {

    public final static NonRecursiveVisitor INSTANCE = new NonRecursiveVisitor();

    private NonRecursiveVisitor() {
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.SKIP_SUBTREE;
    }
}
