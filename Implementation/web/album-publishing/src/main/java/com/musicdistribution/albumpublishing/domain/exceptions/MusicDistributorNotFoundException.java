package com.musicdistribution.albumpublishing.domain.exceptions;

import com.musicdistribution.albumpublishing.domain.models.entity.MusicDistributorId;

/**
 * Runtime exception that is thrown when a music distributor is not found.
 */
public class MusicDistributorNotFoundException extends RuntimeException {

    public MusicDistributorNotFoundException(MusicDistributorId id) {
        super(String.format("Music distributor with id %s doesn't exist", id.getId()));
    }
}
