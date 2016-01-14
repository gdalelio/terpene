package org.geoint.terpene.task;

import java.util.Comparator;

/**
 *
 */
public class TaskPriorityComparator implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        return -(o1.getPriority() - o2.getPriority());
    }
}
