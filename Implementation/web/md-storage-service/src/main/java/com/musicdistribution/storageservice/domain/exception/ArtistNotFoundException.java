package com.musicdistribution.storageservice.domain.exception;

import com.musicdistribution.storageservice.domain.model.entity.ArtistId;

/**
 * Runtime exception that is thrown when an artist is not found.
 */
public class ArtistNotFoundException extends RuntimeException {

    public ArtistNotFoundException(ArtistId id) {
        super("Artist with id " + id.getId() + " is not found.");
    }

}
