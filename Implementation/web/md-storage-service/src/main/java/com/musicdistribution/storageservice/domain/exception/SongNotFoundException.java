package com.musicdistribution.storageservice.domain.exception;

import com.musicdistribution.storageservice.domain.model.entity.SongId;

/**
 * Runtime exception that is thrown when a song is not found.
 */
public class SongNotFoundException extends RuntimeException {

    public SongNotFoundException(SongId id) {
        super("Song with id " + id.getId() + " is not found.");
    }
}
