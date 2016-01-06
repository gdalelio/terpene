package org.geoint.terpene.demo.component;

import javax.inject.Inject;
import org.terpene.task.StatefulTask;
import org.terpene.task.TaskSchedule;
import org.terpene.task.TaskState;
import org.terpene.event.EventService;
import org.terpene.component.ComponentVersion;

/**
 * Simple demo component (a terpene task) that submits an event every 10 seconds.
 * 
 * @author steve_siebert
 */
@ComponentVersion("1.0-DEV")
@TaskSchedule(defaultState=TaskState.ENABLED, defaultSchedule="10 * * * *")
public class DemoTerpeneComponent implements StatefulTask {
    
    @Inject
    EventService events;
    private long sequence = 0L;
    
    @Override
    public void execute () {
        DemoEvent event = new DemoEvent(sequence);
        events.publish("demoEvent", event);
        sequence++;
    }
}
