
package org.geoint.terpene.task;

import java.util.concurrent.Callable;

/**
 *
 */
public interface ChainedCallable<I, R> extends Chained<I>, Callable<R> {

}
