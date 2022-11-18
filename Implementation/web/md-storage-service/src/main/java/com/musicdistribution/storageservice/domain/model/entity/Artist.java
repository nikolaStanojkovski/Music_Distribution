package com.musicdistribution.storageservice.domain.model.entity;

import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import com.musicdistribution.storageservice.constant.EntityConstants;
import com.musicdistribution.storageservice.domain.valueobject.ArtistContactInfo;
import com.musicdistribution.storageservice.domain.valueobject.ArtistPersonalInfo;
import com.musicdistribution.storageservice.domain.valueobject.ArtistUserInfo;
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
@Table(name = EntityConstants.ARTIST)
public class Artist extends AbstractEntity<ArtistId> implements Serializable {

    @AttributeOverrides({
            @AttributeOverride(name = EntityConstants.USERNAME,
                    column = @Column(name = EntityConstants.ARTIST_USERNAME)),
            @AttributeOverride(name = EntityConstants.PASSWORD,
                    column = @Column(name = EntityConstants.ARTIST_PASSWORD))
    })
    private ArtistUserInfo artistUserInfo;

    @AttributeOverrides({
            @AttributeOverride(name = EntityConstants.EMAIL_DOMAIN_USERNAME,
                    column = @Column(name = EntityConstants.ARTIST_EMAIL_DOMAIN_USERNAME)),
            @AttributeOverride(name = EntityConstants.EMAIL_DOMAIN_NAME,
                    column = @Column(name = EntityConstants.ARTIST_EMAIL_DOMAIN_NAME)),
            @AttributeOverride(name = EntityConstants.TELEPHONE_NUMBER,
                    column = @Column(name = EntityConstants.ARTIST_TELEPHONE_NUMBER))
    })
    private ArtistContactInfo artistContactInfo;

    @AttributeOverrides({
            @AttributeOverride(name = EntityConstants.FIRST_NAME,
                    column = @Column(name = EntityConstants.ARTIST_FIRST_NAME)),
            @AttributeOverride(name = EntityConstants.LAST_NAME,
                    column = @Column(name = EntityConstants.ARTIST_LAST_NAME)),
            @AttributeOverride(name = EntityConstants.ART_NAME,
                    column = @Column(name = EntityConstants.ARTIST_ART_NAME))
    })
    private ArtistPersonalInfo artistPersonalInfo;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Album> albums;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Song> songs;

    /**
     * Protected no args constructor used for creating a new Artist entity.
     */
    protected Artist() {
        super(ArtistId.randomId(ArtistId.class));
    }

    /**
     * Static method for creating a new artist.
     *
     * @param artistContactInfo  - artist's contact information.
     * @param artistPersonalInfo - artist's personal information.
     * @param password           - artist's password.
     * @return the created artist.
     */
    public static Artist build(ArtistContactInfo artistContactInfo,
                               ArtistPersonalInfo artistPersonalInfo,
                               String password) {
        Artist artist = new Artist();
        artist.artistContactInfo = artistContactInfo;
        artist.artistPersonalInfo = artistPersonalInfo;
        artist.artistUserInfo = ArtistUserInfo.from(artistContactInfo.getEmail().getDomainUsername(), password);

        artist.albums = new ArrayList<>();
        artist.songs = new ArrayList<>();

        return artist;
    }

    /**
     * Method used for adding a new song to an artist.
     *
     * @param song - the song to be created.
     */
    public void addSongToArtist(Song song) {
        this.songs.add(song);
    }

    /**
     * Method used for deletion of an existing song made by an artist.
     *
     * @param song - the song to be deleted.
     */
    public void removeSongFromArtist(Song song) {
        this.songs.remove(song);
    }
}
