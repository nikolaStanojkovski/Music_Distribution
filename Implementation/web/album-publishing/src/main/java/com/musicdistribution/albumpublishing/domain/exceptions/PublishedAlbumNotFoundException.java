package com.musicdistribution.albumpublishing.domain.exceptions;

import com.musicdistribution.albumpublishing.domain.models.entity.PublishedAlbumId;

/**
 * Runtime exception that is thrown when a published album is not found.
 */
public class PublishedAlbumNotFoundException extends RuntimeException {

    public PublishedAlbumNotFoundException(PublishedAlbumId id) {
        super(String.format("There is no published album with id %s.", id.getId()));
    }
}
