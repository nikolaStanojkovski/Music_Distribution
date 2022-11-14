package com.musicdistribution.storageservice.domain.exception;

import com.musicdistribution.storageservice.domain.model.entity.AlbumId;

/**
 * Runtime exception that is thrown when an album is not found.
 */
public class AlbumNotFoundException extends RuntimeException {

    public AlbumNotFoundException(AlbumId id) {
        super("Album with id " + id.getId() + " is not found.");
    }
}
