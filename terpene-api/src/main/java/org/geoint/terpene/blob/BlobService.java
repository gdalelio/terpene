package org.geoint.terpene.blob;

import java.io.InputStream;
import org.geoint.terpene.blob.reservation.ReservationConstraints;
import org.geoint.terpene.blob.quota.BlobQuotaException;

/**
 * Service interface to interact with a blob store.
 */
public interface BlobService {

    /**
     * Reserve a blob in the requesters default bucket.
     *
     * @return reserved blob id
     * @throws BlobQuotaException thrown if the user cannot create the blob
     * because they are over limits
     */
    BlobId reserve() throws BlobQuotaException;

    /**
     * Reserves a blob in the specified bucket.
     *
     * @param bucketId bucket identifier
     * @return reserved blob id
     * @throws BlobQuotaException thrown if the user cannot create the blob
     * because they are over limits
     */
    BlobId reserve(String bucketId) throws BlobQuotaException;

    /**
     * Reserve a blob in the requesters default bucket with the specified
     * reservation constraints.
     *
     * @param constraints reservation constraints
     * @return reserved blobId
     * @throws BlobQuotaException thrown if the user cannot create the blob
     * because they are over limits
     */
    BlobId reserve(ReservationConstraints constraints)
            throws BlobQuotaException;

    /**
     * Reserve a blob in the specified bucket with the specified reservation
     * constraints.
     *
     * @param bucketId bucket identifier
     * @param constraints reservation constraints
     * @return reserved blobId
     * @throws BlobQuotaException thrown if the user cannot create the blob
     * because they are over limits
     */
    BlobId reserve(String bucketId, ReservationConstraints constraints)
            throws BlobQuotaException;

    /**
     * Deletes a blob from the store.
     *
     * @param id blob GUID to delete
     * @throws BlobNotFoundException thrown if the blob does not exist
     */
    void delete(BlobId id) throws BlobNotFoundException;

    /**
     * Check if the blob, or a reservation for a blob, exists in the store.
     *
     * This skips instantiating a Blob instance, so is a preferred approach than
     * checking the results from {@link #find() } if all that is needed to see
     * if the blob exists.
     *
     * @param id blob id
     * @return true if the blob has been reserved/exists
     * @throws BlobQuotaException thrown if the request exceeds quota limits
     */
    boolean exists(BlobId id) throws BlobQuotaException;

    /**
     * Save the stream to the blob store at the specific blob id.
     *
     * @param id identifier to save the blob content
     * @param in stream of content to save to the blob store
     * @throws BlobQuotaException if saving this content would overflow a quota
     */
    void save(BlobId id, InputStream in) throws BlobQuotaException;

}
