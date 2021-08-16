package finki.ukim.mk.emtproject.albumcatalog.services.form;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Album;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.AlbumId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.Artist;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.ArtistId;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.SongLength;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

@Data
public class SongForm {

    private String songName;

    private Boolean isASingle;

    private Integer lengthInSeconds;


    private String creatorId;

    private String albumId;


    private SongForm() {

    }

    public static SongForm build(String songName, Boolean isASingle, Integer lengthInSeconds, String creatorId, String albumId) {
        SongForm songForm = new SongForm();

        songForm.songName = songName;
        songForm.isASingle = isASingle;
        songForm.lengthInSeconds = lengthInSeconds;
        songForm.creatorId = creatorId;
        songForm.albumId = albumId;

        return songForm;
    }
}
