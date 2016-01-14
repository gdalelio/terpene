package org.geoint.terpene.blob;

import java.io.InputStream;
import org.geoint.terpene.blob.reservation.ReservationConstraints;
import org.geoint.terpene.blob.quota.BlobQuotaException;
import org.geoint.terpene.security.authz.UnauthorizedOperationException;

/**
 * Service interface to interact with a blob store.
 */
public interface BlobService {

    /**
     * Reserve a blob in the requesters default bucket.
     *
     * @return reserved blob id
     * @throws UnauthorizedOperationException thrown if the user does not have
     * permission to create a blob
     * @throws BlobQuotaException thrown if the user cannot create the blob
     * because they are over limits
     * @throws BlobStoreException thrown if there are server-side provides with
     * the reservation
     */
    BlobId reserve() throws UnauthorizedOperationException, BlobQuotaException,
            BlobStoreException;

    /**
     * Reserves a blob in the specified bucket.
     *
     * @param bucketId bucket identifier
     * @return reserved blob id
     * @throws UnauthorizedOperationException thrown if the user does not have
     * permission to create a blob in this bucket
     * @throws BlobQuotaException thrown if the user cannot create the blob
     * because they are over limits
     * @throws BlobStoreException thrown if there are server-side provides with
     * the reservation
     */
    BlobId reserve(String bucketId) throws UnauthorizedOperationException,
            BlobQuotaException, BlobStoreException;

    /**
     * Reserve a blob in the requesters default bucket with the specified
     * reservation constraints.
     *
     * @param constraints reservation constraints
     * @return reserved blobId
     * @throws UnauthorizedOperationException thrown if the user does not have
     * permission to create a blob
     * @throws BlobQuotaException thrown if the user cannot create the blob
     * because they are over limits
     * @throws BlobStoreException thrown if there are server-side provides with
     * the reservation
     */
    BlobId reserve(ReservationConstraints constraints)
            throws UnauthorizedOperationException, BlobQuotaException,
            BlobStoreException;

    /**
     * Reserve a blob in the specified bucket with the specified reservation
     * constraints.
     *
     * @param bucketId bucket identifier
     * @param constraints reservation constraints
     * @return reserved blobId
     * @throws UnauthorizedOperationException thrown if the user does not have
     * permission to create a blob
     * @throws BlobQuotaException thrown if the user cannot create the blob
     * because they are over limits
     * @throws BlobStoreException thrown if there are server-side provides with
     * the reservation
     */
    BlobId reserve(String bucketId, ReservationConstraints constraints)
            throws UnauthorizedOperationException, BlobQuotaException,
            BlobStoreException;

    /**
     * Deletes a blob from the store.
     *
     * @param id blob GUID to delete
     * @throws UnauthorizedOperationException thrown if the user does not have
     * permission to delete the blob
     * @throws BlobNotFoundException thrown if the blob does not exist
     * @throws BlobStoreException if there is a problem with the storage
     */
    void delete(BlobId id) throws UnauthorizedOperationException,
            BlobNotFoundException, BlobStoreException;

    /**
     * Check if the blob, or a reservation for a blob, exists in the store.
     *
     * This skips instantiating a Blob instance, so is a preferred approach than
     * checking the results from {@link #find() } if all that is needed to see
     * if the blob exists.
     *
     * @param id blob id
     * @return true if the blob has been reserved/exists
     * @throws UnauthorizedOperationException thrown if the user does not have
     * permission to request information about this blob
     * @throws BlobQuotaException thrown if the request exceeds quota limits
     * @throws BlobStoreException if there is a problem with the storage
     */
    boolean exists(BlobId id) throws UnauthorizedOperationException,
            BlobQuotaException, BlobStoreException;

    /**
     * Returns a query builder that is used to search for blobs within the
     * store.
     *
     * @return blob search builder
     */
    BlobSearchBuilder find();
//
//    /**
//     * Retrieve a {@link Blob} by ID.
//     *
//     * @param query
//     * @return Blob or null if no blob by ID is found
//     * @throws BlobQuotaException
//     * @throws BlobStoreException if there is a problem with the storage
//     */
//    ServiceResponse<Blob> find(BlobQuery query)
//            throws BlobQuotaException, BlobStoreException;

    /**
     * Save the stream to the blob store at the specific blob id.
     *
     * @param id identifier to save the blob content
     * @param in stream of content to save to the blob store
     * @throws UnauthorizedOperationException if the entity is not permitted to
     * save content to this blob
     * @throws BlobQuotaException if saving this content would overflow a quota
     * @throws BlobStoreException if there is a problem with the storage
     */
    void save(BlobId id, InputStream in) throws UnauthorizedOperationException,
            BlobQuotaException, BlobStoreException;

}
