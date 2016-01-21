package org.geoint.terpene.blob;

import java.io.Serializable;

/**
 * Unique identity of a Blob.
 * <p>
 * A blob must be universally unique, not simply unique to a bucket or a terpene
 * node, however the way that a blob service implementation actually manages the
 * blob itself is not defined by the service. At a minimum, a blob must be able
 * to be referenced by a universally unique RFC 4122 UUID, however, the specific
 * variant used is determined by the blob service implementation.
 */
public interface BlobId extends Serializable {

    /**
     * Blob ID as a string.
     *
     * @return blob identifier as a String
     */
    public String asString();

}
