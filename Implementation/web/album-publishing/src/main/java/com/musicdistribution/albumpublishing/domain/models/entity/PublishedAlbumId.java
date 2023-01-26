package com.musicdistribution.albumpublishing.domain.models.entity;

import com.musicdistribution.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

/**
 * PublishedAlbumId value object for the published album identifier.
 */
public class PublishedAlbumId extends DomainObjectId {

    /**
     * Protected no-args constructor for a published album  id.
     */
    private PublishedAlbumId() {
        super(PublishedAlbumId.randomId(PublishedAlbumId.class).getId());
    }

    /**
     * Constructor for the published album id.
     *
     * @param uuid - the id that is used for the creation of the published album id.
     */
    public PublishedAlbumId(@NonNull String uuid) {
        super(uuid);
    }

    /**
     * Static method for creating a published album id.
     *
     * @param uuid - the id that is used for the creation of the published album id.
     * @return the published album id.
     */
    public static PublishedAlbumId of(String uuid) {
        return new PublishedAlbumId(uuid);
    }
}
