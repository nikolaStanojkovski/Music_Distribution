package com.musicdistribution.albumpublishing.domain.valueobjects;

import com.musicdistribution.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;

/**
 * AlbumId value object used as the album identifier.
 */
@Embeddable
public class AlbumId extends DomainObjectId {

    /**
     * Protected no-args constructor for a album id.
     */
    protected AlbumId() {
        super(AlbumId.randomId(AlbumId.class).getId());
    }

    /**
     * Constructor for the album id.
     *
     * @param uuid - the id that is used for the creation of the album id.
     */
    public AlbumId(@NonNull String uuid) {
        super(uuid);
    }

    /**
     * Static method for creating a album id.
     *
     * @param uuid - the id that is used for the creation of the album id.
     * @return the album id.
     */
    public static AlbumId of(String uuid) {
        return new AlbumId(uuid);
    }
}
