package org.geoint.terpene.blob;

import org.geoint.terpene.blob.quota.BlobQuotaException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Map;
import org.geoint.terpene.antivirus.api.VirusScanResult;
import org.geoint.terpene.net.ContentType;
import org.geoint.terpene.security.lbac.Labeled;
import org.geoint.terpene.security.lbac.LbacLabel;
import org.geoint.terpene.security.lbac.data.LabeledString;

/**
 * Encapsulates the Binary Large Object (Blob) metadata and retrieval of the
 * Blob content from the storage service.
 *
 */
public interface Blob extends Labeled{

    /**
     * Return the unique blob identifier
     *
     * @return the GUID for the blob
     */
    BlobId getId();

    /**
     * Returns the overall security label for the blob, including the content
     * and metadata.
     *
     * @return
     */
    @Override
    LbacLabel getSecurityLabel();

    /**
     * The name of the bucket the Blob is stored.
     *
     * @return the bucket this blob is stored
     */
    String getBucket();

    /**
     * Return the virus scan results.
     *
     * @return current virus scan results for the blob
     *
     */
    VirusScanResult getVirusScanResult();

    /**
     * Return the time the blob was created
     *
     * @return the time the blob was created
     */
    ZonedDateTime getCreatedTime();

    /**
     * Return the original file name of the blob
     *
     * @return original file name, or null if the name is unknown
     */
    LabeledString getFileName();

    /**
     * Returns the optional description of the blob contents
     *
     * @return human readable description of the blob content or null if no
     * description is available
     */
    LabeledString getDescription();

    /**
     * Return the searchable keywords for the blob.
     *
     * @return immutable collection of keywords associated with the blob or an
     * empty array if no keywords are associated.
     */
    Collection<String> getKeywords();

    /**
     * Return user-associated metadata with the blob.
     *
     * @return an immutable key-value map of metadata associated with the blob
     * by the user. If there isn't any meta, returns an empty map.
     */
    Map<String, String> getMeta();

    /**
     * Return the MIME/ContentType of the blob.
     *
     * @return MIME content of the blob, if known, or null
     */
    ContentType getContentType();

    /**
     * Returns the length, in bytes, of the blob
     *
     * @return non-negative length of the blob
     */
    long getSize();

    /**
     * Returns an ASCII SHA1 hash of the blob.
     *
     * @return hash of the blob
     */
    String getSHA1();

    /**
     * Returns a stream to the blob content.
     *
     * @return blob content as stream
     * @throws BlobQuotaException if the blob cannot be received because the
     * component, user, or service has exceeded it's quota limit
     * @throws BlobStoreException if the blob content cannot be received due to
     * an store (system) issue
     */
    InputStream getContent() throws BlobQuotaException, BlobStoreException;
}
