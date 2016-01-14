package org.geoint.terpene.blob;

/**
 * The query used to search the blob store.
 */
public interface BlobQuery {

    /**
     * The buckets to search.
     *
     * @return
     */
    String[] getBuckets();

    /**
     * Specific blob ids to search for.
     *
     * @return
     */
    BlobId[] getBlobIds();

    /**
     * The maximum number of blobs to be returned in the results.
     *
     * @return
     */
    Integer getLimit();

}
