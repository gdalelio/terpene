package org.geoint.terpene.blob;

import org.geoint.terpene.component.service.ServiceResponse;
import org.geoint.terpene.net.ContentType;

/**
 * Fluid interface to search for blobs.
 *
 */
public interface BlobSearchBuilder {

    /**
     * Adds a bucket to search in.
     *
     * Zero or more buckets may be searched. If not bucket is specified the
     * {@code DEFAULT} bucket is searched. If any much is specified, the
     * {@code DEFAULT} bucket must be explicitly added if desired.
     * <p>
     * Subsequent calls to this method adds additional buckets to the search
     * criteria, effectively as a boolean *or*, and will widen the search to the
     * additional buckets.
     *
     * @param bucketIds a specific bucket(s) to search in
     * @return search builder
     */
    BlobSearchBuilder inBucket(String... bucketIds);

    /**
     * Search for a specific blob, by ID.
     * <p>
     * Subsequent calls to this method adds additional blobIds to the search
     * criteria, effectively as a boolean *or*, and will widen the search.
     *
     * @param ids ids to include in the search
     * @return search builder
     */
    BlobSearchBuilder withId(String... ids);

    /**
     * Search for a specific blob, by ID.
     * <p>
     * Subsequent calls to this method adds additional blobIds to the search
     * criteria, effectively as a boolean *or*, and will widen the search.
     *
     * @param ids ids to include in the search
     * @return search builder
     */
    BlobSearchBuilder withId(BlobId... ids);

    /**
     * Search for a blob by its original file name.
     * <p>
     * Subsequent calls to this method adds additional original file names to
     * the search criteria, effectively as a boolean *or*, and will widen the
     * search.
     *
     * @param names original file names
     * @return search builder
     */
    BlobSearchBuilder withName(String... names);

    /**
     * Search for blob(s) by the content type of the file.
     * <p>
     * Subsequent calls to this method adds additional content types to the
     * search criteria, effectively as a boolean *or*, and will widen the
     * search.
     *
     * @param types content types to include in the search
     * @return search builder
     */
    BlobSearchBuilder withType(ContentType... types);

    /**
     * Execute the search in its current state.
     * <p>
     * Calling this method <b>does not</b> clear the builder.
     *
     * @return search results
     */
    ServiceResponse<Blob> search();
}
