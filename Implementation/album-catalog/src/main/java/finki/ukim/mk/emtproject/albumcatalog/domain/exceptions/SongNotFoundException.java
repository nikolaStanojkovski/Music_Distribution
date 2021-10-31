package finki.ukim.mk.emtproject.albumcatalog.domain.exceptions;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.AlbumId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.SongId;

/**
 * SongNotFoundException - thrown if the song is not found.
 */
public class SongNotFoundException extends RuntimeException {

    public SongNotFoundException(SongId id) {
        super("Song with id " + id.getId() + " is not found.");
    }

}
