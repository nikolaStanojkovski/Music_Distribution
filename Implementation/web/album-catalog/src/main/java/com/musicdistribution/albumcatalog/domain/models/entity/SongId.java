package com.musicdistribution.albumcatalog.domain.models.entity;

import com.musicdistribution.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

/**
 * SongId value object used as the song identifier.
 */
public class SongId extends DomainObjectId {

    /**
     * Protected no-args constructor for a song id.
     */
    protected SongId() {
        super(randomId(ArtistId.class).getId());
    }

    /**
     * Constructor for the song id.
     *
     * @param uuid - the id that is used for the creation of the song id.
     */
    public SongId(@NonNull String uuid) {
        super(uuid);
    }

    /**
     * Static method for creating a song id.
     *
     * @param uuid - the id that is used for the creation of the song id.
     * @return the song id.
     */
    public static SongId of(String uuid) {
        return new SongId(uuid);
    }
}
