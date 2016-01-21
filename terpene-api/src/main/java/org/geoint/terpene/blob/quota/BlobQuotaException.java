package org.geoint.terpene.blob.quota;

import org.geoint.terpene.blob.BlobServiceException;

/**
 * Request surpassed quota limits of the service.
 */
public abstract class BlobQuotaException extends BlobServiceException {

    public BlobQuotaException() {
    }

    public BlobQuotaException(String message) {
        super(message);
    }

    public BlobQuotaException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlobQuotaException(Throwable cause) {
        super(cause);
    }
}
