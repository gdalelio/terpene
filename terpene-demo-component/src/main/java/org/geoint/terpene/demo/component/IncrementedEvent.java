package org.geoint.terpene.demo.component;

/**
 *
 * @author steve_siebert
 */
public class IncrementedEvent {

    private final int newValue;

    public IncrementedEvent(int newValue) {
        this.newValue = newValue;
    }

    public int getNewValue() {
        return newValue;
    }

    @Override
    public String toString() {
        return String.format("Incremented to %d", newValue);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.newValue;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IncrementedEvent other = (IncrementedEvent) obj;
        if (this.newValue != other.newValue) {
            return false;
        }
        return true;
    }

}
