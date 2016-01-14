package org.geoint.terpene.task.trigger.file;

import java.io.FilenameFilter;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Determines if a file gets triggered based on a file name.
 *
 */
public class FileNameTriggerFilter extends SimpleTriggerFileFilter {

    public FileNameTriggerFilter(FilenameFilter filter) {
        super(new FileNameFilterVisitor(filter));
    }

    private static class FileNameFilterVisitor extends SimpleFileVisitor<Path> {

        private final FilenameFilter filter;

        public FileNameFilterVisitor(FilenameFilter filter) {
            this.filter = filter;
        }

        @Override
        public FileVisitResult visitFile(Path p, BasicFileAttributes bfa) {
            if (filter.accept(p.toFile(), p.getFileName().toString())) {
                return FileVisitResult.CONTINUE;
            }
            return FileVisitResult.SKIP_SUBTREE;
        }
    }
}
