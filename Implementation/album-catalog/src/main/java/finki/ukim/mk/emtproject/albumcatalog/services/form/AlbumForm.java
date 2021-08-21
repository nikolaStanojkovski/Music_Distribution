package finki.ukim.mk.emtproject.albumcatalog.services.form;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Artist;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.ArtistId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.Song;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.AlbumInfo;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.Data;

import java.util.List;

/**
 * AlbumForm - object used to transfer data from the front-end user form to the backend
 */
@Data
public class AlbumForm {

    private String albumName;

    private Integer totalLength;

    private Boolean isPublished;

    private Genre genre;

    // Album Info

    private String artistName;

    private String producerName;

    private String composerName;

    // Creator info

    private String creatorId;

    private AlbumForm() {

    }

    public static AlbumForm build(String albumName, Integer totalLength, Boolean isPublished, Genre genre, String artistName, String producerName, String composerName, String creatorId) {
        AlbumForm albumForm = new AlbumForm();

        albumForm.albumName = albumName;
        albumForm.totalLength = totalLength;
        albumForm.isPublished = isPublished;
        albumForm.genre = genre;
        albumForm.artistName = artistName;
        albumForm.producerName = producerName;
        albumForm.composerName = composerName;
        albumForm.creatorId = creatorId;

        return albumForm;
    }
}
