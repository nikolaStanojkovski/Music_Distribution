package com.musicdistribution.streamingservice.domain.model.entity;

import com.musicdistribution.sharedkernel.domain.base.DomainObjectId;
import lombok.NoArgsConstructor;

/**
 * AlbumId value object used as an album unique identifier.
 */
@NoArgsConstructor
public class AlbumId extends DomainObjectId {

    /**
     * Public constructor used for creating a new album unique ID.
     *
     * @param uuid - the value of the identifier to be created.
     */
    public AlbumId(String uuid) {
        super(uuid);
    }

    /**
     * Static method for creating a new album ID.
     *
     * @param uuid - the unique identifier for a album.
     * @return the created album ID.
     */
    public static AlbumId of(String uuid) {
        return new AlbumId(uuid);
    }
}
