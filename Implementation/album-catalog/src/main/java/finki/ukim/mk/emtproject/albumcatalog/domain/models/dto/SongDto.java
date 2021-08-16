package finki.ukim.mk.emtproject.albumcatalog.domain.models.dto;

import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.SongLength;
import lombok.Data;

@Data
public class SongDto {

    private String id;

    private String songName;

    private Boolean isASingle;

    private SongLength songLength;

    private ArtistDto creator;

    private AlbumDto album;

    public SongDto(String id, String songName, Boolean isASingle, SongLength songLength, ArtistDto creator, AlbumDto album) {
        this.id = id;
        this.songName = songName;
        this.isASingle = isASingle;
        this.songLength = songLength;
        this.creator = creator;
        this.album = album;
    }
}
