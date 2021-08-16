package finki.ukim.mk.emtproject.albumpublishing.domain.exceptions;

import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbumId;

public class PublishedAlbumNotFoundException extends RuntimeException {

    public PublishedAlbumNotFoundException(PublishedAlbumId id) {
        super(String.format("There is no published album with id %s.", id.getId()));
    }
}
