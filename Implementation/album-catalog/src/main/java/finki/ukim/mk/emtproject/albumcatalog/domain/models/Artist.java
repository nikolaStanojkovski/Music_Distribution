package finki.ukim.mk.emtproject.albumcatalog.domain.models;

import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.ArtistContactInfo;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.ArtistPersonalInfo;
import finki.ukim.mk.emtproject.sharedkernel.domain.base.AbstractEntity;
import lombok.Getter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Artist domain entity
 */
@Entity
@Table(name="artist")
@Getter
public class Artist extends AbstractEntity<ArtistId> {

    /**
     * Required properties defintiion
     */

    @AttributeOverrides({
            @AttributeOverride(name="email", column = @Column(name="artist_email")),
            @AttributeOverride(name="telephoneNumber", column = @Column(name="artist_telephoneNumber"))
    })
    private ArtistContactInfo artistContactInfo;

    @AttributeOverrides({
            @AttributeOverride(name="firstName", column = @Column(name="artist_firstName")),
            @AttributeOverride(name="lastName", column = @Column(name="artist_lastName")),
            @AttributeOverride(name="artName", column = @Column(name="artist_artName"))
    })
    private ArtistPersonalInfo artistPersonalInfo;

    // Didn't add encryption to the password because it's out of this project's scope
    @Column(nullable = false)
    private String password;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Album> albums;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Song> songs;


    private Artist() {
        super(ArtistId.randomId(ArtistId.class));
    }

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
     * Methods used for defining the consistency rules
     */

    // create a new album
    public Album createAlbum(Album album) {
        this.albums.add(album);

        return album;
    }

    // add a new song to an artist
    public Song addSongToArtist(Song song) {
        this.songs.add(song);

        return song;
    }

    // remove a song from an artist
    public Song removeSongFromArtist(Song song) {
        this.songs.remove(song);

        return song;
    }

    // make some artist's album published
    public Boolean makeAlbumPublished(AlbumId albumId) {
        Album album = this.albums.stream().filter(i -> i.getId().getId().equals(albumId.getId()))
                .findAny().get();
        album.setIsPublished(true);

        return album.getIsPublished();
    }

    // make some artist's album unpublished
    public Boolean makeAlbumUnpublished(AlbumId albumId) {
        Album album = this.albums.stream().filter(i -> i.getId().getId().equals(albumId.getId()))
                .findAny().get();
        album.setIsPublished(false);

        return album.getIsPublished();
    }
}
