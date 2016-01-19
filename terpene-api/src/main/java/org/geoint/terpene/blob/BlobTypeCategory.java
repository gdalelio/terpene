package org.geoint.terpene.blob;

/**
 * Media type categories, as defined by the IANA media type registry
 */
public enum BlobTypeCategory {

    APPLICATION("application"),
    AUDIO("audio"),
    IMAGE("image"),
    MESSAGE("message"),
    MODEL("model"),
    MULTIPART("multipart"),
    TEXT("text"),
    VIDEO("video"),
    UNKNOWN("unknown");
    private final String category;

    private BlobTypeCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return category;
    }
}
