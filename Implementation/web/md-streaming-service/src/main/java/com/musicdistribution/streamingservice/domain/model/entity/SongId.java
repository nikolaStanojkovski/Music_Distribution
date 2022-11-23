package com.musicdistribution.streamingservice.domain.model.entity;

import com.musicdistribution.sharedkernel.domain.base.DomainObjectId;
import lombok.NoArgsConstructor;

/**
 * SongId value object used as the song identifier.
 */
@NoArgsConstructor
public class SongId extends DomainObjectId {

    /**
     * Public constructor used for creating a new song unique ID.
     *
     * @param uuid - the value of the identifier to be created.
     */
    public SongId(String uuid) {
        super(uuid);
    }

    /**
     * Static method for creating a new song ID.
     *
     * @param uuid - the unique identifier for a song.
     * @return the created song ID.
     */
    public static SongId of(String uuid) {
        return new SongId(uuid);
    }
}
