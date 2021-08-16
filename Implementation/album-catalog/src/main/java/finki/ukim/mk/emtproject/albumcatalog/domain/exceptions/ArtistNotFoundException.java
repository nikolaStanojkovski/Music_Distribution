package finki.ukim.mk.emtproject.albumcatalog.domain.exceptions;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.ArtistId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.SongId;

public class ArtistNotFoundException extends RuntimeException {

    public ArtistNotFoundException(ArtistId id) {
        super("Artist with id " + id.getId() + " is not found.");
    }

}
