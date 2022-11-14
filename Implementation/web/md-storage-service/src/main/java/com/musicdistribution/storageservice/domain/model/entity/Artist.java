package com.musicdistribution.storageservice.domain.model.entity;

import com.musicdistribution.storageservice.domain.valueobject.ArtistContactInfo;
import com.musicdistribution.storageservice.domain.valueobject.ArtistPersonalInfo;
import com.musicdistribution.storageservice.domain.valueobject.ArtistUserInfo;
import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Artist domain entity.
 */
@Entity
@Getter
@Table(name = "artist")
public class Artist extends AbstractEntity<ArtistId> implements Serializable {

    @AttributeOverrides({
            @AttributeOverride(name = "username", column = @Column(name = "artist_username")),
            @AttributeOverride(name = "password", column = @Column(name = "artist_password"))
    })
    private ArtistUserInfo artistUserInfo;

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

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Album> albums;

    @OneToMany(cascade = CascadeType.ALL)
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
        artist.artistUserInfo = ArtistUserInfo.build(artistContactInfo.getEmail().getDomainUsername(), password);

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
}
