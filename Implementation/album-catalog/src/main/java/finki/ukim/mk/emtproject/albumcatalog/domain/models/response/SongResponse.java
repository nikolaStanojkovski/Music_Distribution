package finki.ukim.mk.emtproject.albumcatalog.domain.models.response;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Song;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.SongLength;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Data transfer object for a song.
 */
@Data
@NoArgsConstructor
public class SongResponse {

    private String id;
    private String songName;
    private Boolean isASingle;
    private SongLength songLength;
    private ArtistResponse creator;
    private AlbumResponse album;

    public static SongResponse from(Song song) {
        SongResponse songResponse = new SongResponse();
        songResponse.setId(song.getId().getId());
        songResponse.setSongName(song.getSongName());
        songResponse.setIsASingle(song.getIsASingle());
        songResponse.setSongLength(song.getSongLength());
        songResponse.setCreator(ArtistResponse.from(song.getCreator()));
        songResponse.setAlbum(Objects.isNull(song.getAlbum()) ? null : AlbumResponse.from(song.getAlbum()));

        return songResponse;
    }
}
