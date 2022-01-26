package com.musicdistribution.albumpublishing.domain.valueobjects;

import com.musicdistribution.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;

/**
 * ArtistId value object used as the artist identifier.
 */
@Embeddable
public class ArtistId extends DomainObjectId {

    /**
     * Protected no-args constructor for a artist id.
     */
    protected ArtistId() {
        super(ArtistId.randomId(ArtistId.class).getId());
    }

    /**
     * Constructor for the artist id.
     *
     * @param uuid - the id that is used for the creation of the artist id.
     */
    public ArtistId(@NonNull String uuid) {
        super(uuid);
    }

    /**
     * Static method for creating a artist id.
     *
     * @param uuid - the id that is used for the creation of the artist id.
     * @return the artist id.
     */
    public static ArtistId of(String uuid) {
        return new ArtistId(uuid);
    }
}
