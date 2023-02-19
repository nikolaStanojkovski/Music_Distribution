package com.musicdistribution.streamingservice.domain.model.entity.id;

import com.musicdistribution.sharedkernel.domain.base.SingularObjectId;
import lombok.NoArgsConstructor;

/**
 * ArtistId value object used as the artist identifier.
 */
@NoArgsConstructor
public class ArtistId extends SingularObjectId {

    /**
     * Public constructor used for creating a new artist unique ID.
     *
     * @param uuid - the value of the identifier to be created.
     */
    public ArtistId(String uuid) {
        super(uuid);
    }

    /**
     * Static method used for creating a new artist ID.
     *
     * @param uuid - the unique identifier for a artist.
     * @return the created artist ID.
     */
    public static ArtistId of(String uuid) {
        return new ArtistId(uuid);
    }
}
