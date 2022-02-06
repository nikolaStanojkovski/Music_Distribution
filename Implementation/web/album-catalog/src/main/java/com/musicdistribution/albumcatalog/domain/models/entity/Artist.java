package com.musicdistribution.albumcatalog.domain.models.entity;

import com.musicdistribution.albumcatalog.domain.valueobjects.ArtistContactInfo;
import com.musicdistribution.albumcatalog.domain.valueobjects.ArtistPersonalInfo;
import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Artist domain entity.
 */
@Entity
@Getter
@Table(name = "artist")
public class Artist extends AbstractEntity<ArtistId> {

    @AttributeOverrides({
            @AttributeOverride(name = "email", column = @Column(name = "artist_email")),
            @AttributeOverride(name = "telephoneNumber", column = @Column(name = "artist_telephoneNumber"))
    })
    private ArtistContactInfo artistContactInfo;

    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "artist_firstName")),
            @AttributeOverride(name = "lastName", column = @Column(name = "artist_lastName")),
            @AttributeOverride(name = "artName", column = @Column(name = "artist_artName"))
    })
    private ArtistPersonalInfo artistPersonalInfo;

    @Column(nullable = false)
    private String password;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Album> albums;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Song> songs;

    /**
     * Protected no args constructor for the Artist entity.
     */
    protected Artist() {
        super(ArtistId.randomId(ArtistId.class));
    }

    /**
     * Static method for creating a new album.
     *
     * @param artistContactInfo  - artist's contact information.
     * @param artistPersonalInfo - artist's contact information.
     * @param password           - artist's password.
     * @return the created artist.
     */
    public static Artist build(ArtistContactInfo artistContactInfo, ArtistPersonalInfo artistPersonalInfo, String password) {
        Artist artist = new Artist();
        artist.artistContactInfo = artistContactInfo;
        artist.artistPersonalInfo = artistPersonalInfo;
        artist.password = password;

        artist.albums = new ArrayList<>();
        artist.songs = new ArrayList<>();

        return artist;
    }

    /**
     * Method used for creation of a new album by an artist.
     *
     * @param album - the album to be created.
     */
    public void createAlbum(Album album) {
        this.albums.add(album);
    }

    /**
     * Method used for creation of a new song by an artist.
     *
     * @param song - the song to be created.
     */
    public void addSongToArtist(Song song) {
        this.songs.add(song);
    }

    /**
     * Method used for deletion of a song by an artist.
     *
     * @param song - the song to be deleted.
     */
    public void removeSongFromArtist(Song song) {
        this.songs.remove(song);
    }

    /**
     * Method used for making an album published for an artist.
     *
     * @param albumId - the album's id to be published.
     */
    public void makeAlbumPublished(AlbumId albumId) {
        this.albums.stream().filter(a -> a.getId().getId().equals(albumId.getId()))
                .findAny().ifPresent(a -> a.setIsPublished(Boolean.TRUE));
    }

    /**
     * Method used for making an album unpublished for an artist.
     *
     * @param albumId - the album's id to be unpublished.
     */
    public void makeAlbumUnpublished(AlbumId albumId) {
        this.albums.stream().filter(a -> a.getId().getId().equals(albumId.getId()))
                .findAny().ifPresent(a -> a.setIsPublished(Boolean.FALSE));
    }
}
