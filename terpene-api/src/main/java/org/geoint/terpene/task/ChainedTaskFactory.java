
package org.geoint.terpene.task;

import java.util.concurrent.ExecutorService;

/**
 *
 */
public interface ChainedTaskFactory<I, R>  {

    /**
     * Create a ChainedTask for a data item
     * 
     * @param item
     * @return 
     */
    ChainedTask<I, R> create (I item, ExecutorService executor);
}
