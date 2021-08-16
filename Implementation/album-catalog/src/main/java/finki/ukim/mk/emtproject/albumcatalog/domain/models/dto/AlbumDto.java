package finki.ukim.mk.emtproject.albumcatalog.domain.models.dto;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Album;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.AlbumId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.Artist;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.AlbumInfo;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.ArtistContactInfo;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.ArtistPersonalInfo;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.SongLength;
import finki.ukim.mk.emtproject.sharedkernel.domain.base.AbstractEntity;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.Data;

@Data
public class AlbumDto {

    private String id;

    private String albumName;

    private SongLength totalLength;

    private Boolean isPublished;

    private Genre genre;

    private AlbumInfo albumInfo;

    private ArtistDto creator;

    private String artistId;
    private String artistName;

    public AlbumDto(Album album) {
        if(album != null) {
            this.id = album.getId().getId();
            this.albumName = album.getAlbumName();
            this.totalLength = album.getTotalLength();
            this.isPublished = album.getIsPublished();
            this.genre = album.getGenre();
            this.albumInfo = album.getAlbumInfo();
            this.creator = null;

            this.artistId = album.getCreator().getId().getId();
            this.artistName = album.getCreator().getArtistPersonalInfo().getFullName();
        }
    }

    public AlbumDto(String id, String albumName, SongLength totalLength, Boolean isPublished, Genre genre, AlbumInfo albumInfo, ArtistDto creator) {
        this.id = id;
        this.albumName = albumName;
        this.totalLength = totalLength;
        this.isPublished = isPublished;
        this.genre = genre;
        this.albumInfo = albumInfo;
        this.creator = creator;

        this.artistId = creator.getId();
        this.artistName = creator.getArtistPersonalInfo().getFullName();
    }
}
