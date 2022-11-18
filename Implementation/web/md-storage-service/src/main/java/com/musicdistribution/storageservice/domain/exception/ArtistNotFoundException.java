package com.musicdistribution.storageservice.domain.exception;

import com.musicdistribution.storageservice.constant.ExceptionConstants;
import com.musicdistribution.storageservice.domain.model.entity.ArtistId;

/**
 * Runtime exception that is thrown when an artist is not found.
 */
public class ArtistNotFoundException extends RuntimeException {

    /**
     * Public constructor for the exception.
     *
     * @param id - the ID of the artist which was not found.
     */
    public ArtistNotFoundException(ArtistId id) {
        super(String.format(ExceptionConstants.ARTIST_NOT_FOUND, id.getId()));
    }
}
