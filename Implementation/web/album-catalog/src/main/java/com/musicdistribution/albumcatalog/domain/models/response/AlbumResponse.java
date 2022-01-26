package com.musicdistribution.albumcatalog.domain.models.response;

import com.musicdistribution.albumcatalog.domain.models.entity.Album;
import com.musicdistribution.albumcatalog.domain.valueobjects.AlbumInfo;
import com.musicdistribution.albumcatalog.domain.valueobjects.SongLength;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
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
