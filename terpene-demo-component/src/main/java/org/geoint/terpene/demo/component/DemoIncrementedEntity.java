package org.geoint.terpene.demo.component;

import org.geoint.terpene.domain.Operation;

/**
 * Entity that may be one-up incremented, for demonstration purposes.
 *
 * @author steve_siebert
 */
public class DemoIncrementedEntity {

    private final int value;

    public DemoIncrementedEntity() {
        this.value = 0;
    }

    public DemoIncrementedEntity(int value) {
        this.value = value;
    }

    @Operation
    public IncrementedEvent increment() {
        return new IncrementedEvent(value + 1);
    }

}
