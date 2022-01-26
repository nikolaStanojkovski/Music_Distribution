package com.musicdistribution.albumcatalog.domain.exceptions;

import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;

/**
 * Runtime exception that is thrown when an artist is not found.
 */
public class ArtistNotFoundException extends RuntimeException {

    public ArtistNotFoundException(ArtistId id) {
        super("Artist with id " + id.getId() + " is not found.");
    }

}
