package org.geoint.terpene.blob.quota;

/**
 * Thrown when a service operation cannot be completed because the storage
 * limits defined for the blob service has been, or would be, exceeded by
 * completing the operation.
 */
public class BlobStorageQuotaException extends BlobQuotaException {

    private final long limit;
    private final long currentSize;

    public BlobStorageQuotaException(long limit, long currentSize) {
        this.limit = limit;
        this.currentSize = currentSize;
    }

    public BlobStorageQuotaException(long limit, long currentSize,
            String message) {
        super(message);
        this.limit = limit;
        this.currentSize = currentSize;
    }

    public BlobStorageQuotaException(long limit, long currentSize,
            String message, Throwable cause) {
        super(message, cause);
        this.limit = limit;
        this.currentSize = currentSize;
    }

    public BlobStorageQuotaException(long limit, long currentSize,
            Throwable cause) {
        super(cause);
        this.limit = limit;
        this.currentSize = currentSize;
    }

    public long getLimit() {
        return limit;
    }

    public long getCurrentSize() {
        return currentSize;
    }

}
