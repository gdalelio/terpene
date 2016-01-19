package org.geoint.terpene.blob;

/**
 *
 */
public class BlobNotFoundException extends BlobServiceException {

    private final BlobId blobId;

    public BlobNotFoundException(BlobId blobId) {
        this.blobId = blobId;
    }

    public BlobNotFoundException(BlobId blobId, String message) {
        super(message);
        this.blobId = blobId;
    }

    public BlobNotFoundException(BlobId blobId, String message, Throwable cause) {
        super(message, cause);
        this.blobId = blobId;
    }

    public BlobNotFoundException(BlobId blobId, Throwable cause) {
        super(cause);
        this.blobId = blobId;
    }

    public BlobId getBlobId() {
        return blobId;
    }

}
