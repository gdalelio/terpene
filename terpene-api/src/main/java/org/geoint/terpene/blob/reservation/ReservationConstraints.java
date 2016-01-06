package org.geoint.terpene.blob.reservation;

/**
 * Specifies constraints for an asynchronous Blob reservation.
 *
 * ReservationConstants instances are immutable, so they are thread safe.
 *
 */
public final class ReservationConstraints {

    private final long timeout;
    private final long maxUploadSize;
    public static final long DEFAULT_TIMEOUT = 600000; //default 10min
    public static final long DEFAULT_MAXUPLOAD = 26210000; //default 25 Megabytes

    private ReservationConstraints() {
        this.timeout = DEFAULT_TIMEOUT;
        this.maxUploadSize = DEFAULT_MAXUPLOAD;
    }

    private ReservationConstraints(final long timeout, final long size) {
        this.timeout = timeout;
        this.maxUploadSize = size;
    }

    public static ReservationConstraints instance() {
        return new ReservationConstraints();
    }

    public static ReservationConstraints instance(long timeout, long size) {
        return new ReservationConstraints(timeout, size);
    }

    public long getTimeout() {
        return timeout;
    }

    public long getMaxUploadSize() {
        return maxUploadSize;
    }
}
