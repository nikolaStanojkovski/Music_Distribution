package com.musicdistribution.albumpublishing.domain.models.entity;

import com.musicdistribution.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

/**
 * MusicDistributorId value object used as the music distributor identifier.
 */
public class MusicDistributorId extends DomainObjectId {

    /**
     * Protected no-args constructor for a music distributor id.
     */
    private MusicDistributorId() {
        super(MusicDistributorId.randomId(MusicDistributorId.class).getId());
    }

    /**
     * Constructor for the music distributor id.
     *
     * @param uuid - the id that is used for the creation of the music distributor id.
     */
    public MusicDistributorId(@NonNull String uuid) {
        super(uuid);
    }

    /**
     * Static method for creating a music distributor id.
     *
     * @param uuid - the id that is used for the creation of the music distributor id.
     * @return the music distributor id.
     */
    public static MusicDistributorId of(String uuid) {
        return new MusicDistributorId(uuid);
    }
}
