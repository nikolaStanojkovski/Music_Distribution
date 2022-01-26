package com.musicdistribution.albumcatalog.domain.exceptions;

import com.musicdistribution.albumcatalog.domain.models.entity.AlbumId;

/**
 * Runtime exception that is thrown when an album is not found.
 */
public class AlbumNotFoundException extends RuntimeException {

    public AlbumNotFoundException(AlbumId id) {
        super("Album with id " + id.getId() + " is not found.");
    }
}
