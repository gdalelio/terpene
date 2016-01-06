package org.geoint.terpene.blob;

import org.geoint.terpene.TerpeneException;

/**
 * Base exception type for terpene-blob exceptions.
 */
public abstract class BlobServiceException extends TerpeneException {

    public BlobServiceException() {
    }

    public BlobServiceException(String message) {
        super(message);
    }

    public BlobServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlobServiceException(Throwable cause) {
        super(cause);
    }
}
