package org.geoint.terpene.blob;

/**
 * Exception is thrown when there is a resource (I/O) problem accessing the
 * underlying storage.
 */
public class BlobStoreException extends BlobServiceException {

    public BlobStoreException() {
    }

    public BlobStoreException(String message) {
        super(message);
    }

    public BlobStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlobStoreException(Throwable cause) {
        super(cause);
    }
}
