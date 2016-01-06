package org.geoint.terpene.task.trigger.file;

import java.nio.file.Path;

/**
 * 
 */
public interface WatchTriggerFileFilter {
    
    boolean watchDirectory (Path p);
    boolean triggerNewFile (Path p);
}
