package com.musicdistribution.albumcatalog.domain.exceptions;

import com.musicdistribution.albumcatalog.domain.models.entity.SongId;

/**
 * Runtime exception that is thrown when a song is not found.
 */
public class SongNotFoundException extends RuntimeException {

    public SongNotFoundException(SongId id) {
        super("Song with id " + id.getId() + " is not found.");
    }

}
