package finki.ukim.mk.emtproject.albumcatalog.domain.models.response;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Album;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.AlbumInfo;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.SongLength;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Data transfer object for an album.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumResponse {

    private String id;
    private String albumName;
    private SongLength totalLength;
    private Boolean isPublished;
    private Genre genre;
    private AlbumInfo albumInfo;

    private ArtistResponse creator;
    private String artistId;
    private String artistName;

    public static AlbumResponse from(Album album) {
        AlbumResponse albumResponse = new AlbumResponse();
        albumResponse.setId(album.getId().getId());
        albumResponse.setAlbumName(album.getAlbumName());
        albumResponse.setTotalLength(album.getTotalLength());
        albumResponse.setIsPublished(album.getIsPublished());
        albumResponse.setGenre(album.getGenre());
        albumResponse.setAlbumInfo(album.getAlbumInfo());
        albumResponse.setCreator(Objects.isNull(album.getCreator()) ? null : ArtistResponse.from(album.getCreator()));
        albumResponse.setArtistId(Objects.isNull(album.getCreator()) ? null : album.getCreator().getId().getId());

        return albumResponse;
    }
}
