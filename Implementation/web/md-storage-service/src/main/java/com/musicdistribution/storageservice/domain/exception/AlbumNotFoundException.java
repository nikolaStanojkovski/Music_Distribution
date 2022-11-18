package com.musicdistribution.storageservice.domain.exception;

import com.musicdistribution.storageservice.constant.ExceptionConstants;
import com.musicdistribution.storageservice.domain.model.entity.AlbumId;

/**
 * Runtime exception that is thrown when an album is not found.
 */
public class AlbumNotFoundException extends RuntimeException {

    /**
     * Public constructor for the exception.
     *
     * @param id - the ID of the album which was not found.
     */
    public AlbumNotFoundException(AlbumId id) {
        super(String.format(ExceptionConstants.ALBUM_NOT_FOUND, id.getId()));
    }
}
