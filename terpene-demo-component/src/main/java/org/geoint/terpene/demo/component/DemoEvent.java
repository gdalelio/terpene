package org.geoint.terpene.demo.component;

/**
 *
 * @author steve_siebert
 */
public class DemoEvent {

    private final long sequence;

    public DemoEvent(long sequence) {
        this.sequence = sequence;
    }

    public long getSequence() {
        return sequence;
    }

    @Override
    public String toString() {
        return String.valueOf(sequence);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (int) (this.sequence ^ (this.sequence >>> 32));
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
        final DemoEvent other = (DemoEvent) obj;
        if (this.sequence != other.sequence) {
            return false;
        }
        return true;
    }

}
