package org.geoint.terpene.blob;

/**
 * Thrown by a blob store when a request for a new, specific blob reservation
 * collides with an existing blob.
 */
public class BlobAlreadyExistsException extends BlobServiceException {

    public BlobAlreadyExistsException() {
    }

    public BlobAlreadyExistsException(String message) {
        super(message);
    }

    public BlobAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlobAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
