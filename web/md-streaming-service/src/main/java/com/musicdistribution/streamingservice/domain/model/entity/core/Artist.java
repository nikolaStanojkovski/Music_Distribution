package com.musicdistribution.streamingservice.domain.model.entity.core;

import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import com.musicdistribution.streamingservice.constant.EntityConstants;
import com.musicdistribution.streamingservice.domain.model.entity.id.ArtistId;
import com.musicdistribution.streamingservice.domain.valueobject.core.UserContactInfo;
import com.musicdistribution.streamingservice.domain.valueobject.core.UserPersonalInfo;
import com.musicdistribution.streamingservice.domain.valueobject.core.UserRegistrationInfo;
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
    private UserRegistrationInfo userRegistrationInfo;

    @AttributeOverrides({
            @AttributeOverride(name = EntityConstants.EMAIL_DOMAIN_USERNAME,
                    column = @Column(name = EntityConstants.ARTIST_EMAIL_DOMAIN_USERNAME)),
            @AttributeOverride(name = EntityConstants.EMAIL_DOMAIN_NAME,
                    column = @Column(name = EntityConstants.ARTIST_EMAIL_DOMAIN_NAME)),
            @AttributeOverride(name = EntityConstants.TELEPHONE_NUMBER,
                    column = @Column(name = EntityConstants.ARTIST_TELEPHONE_NUMBER))
    })
    private UserContactInfo userContactInfo;

    @AttributeOverrides({
            @AttributeOverride(name = EntityConstants.FIRST_NAME,
                    column = @Column(name = EntityConstants.ARTIST_FIRST_NAME)),
            @AttributeOverride(name = EntityConstants.LAST_NAME,
                    column = @Column(name = EntityConstants.ARTIST_LAST_NAME)),
            @AttributeOverride(name = EntityConstants.ART_NAME,
                    column = @Column(name = EntityConstants.ARTIST_ART_NAME))
    })
    private UserPersonalInfo userPersonalInfo;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Album> albums;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
     * @param userContactInfo      - artist's contact information.
     * @param userPersonalInfo     - artist's personal information.
     * @param userRegistrationInfo - artist's registration information.
     * @return the created artist.
     */
    public static Artist build(UserContactInfo userContactInfo,
                               UserPersonalInfo userPersonalInfo,
                               UserRegistrationInfo userRegistrationInfo) {
        Artist artist = new Artist();
        artist.userContactInfo = userContactInfo;
        artist.userPersonalInfo = userPersonalInfo;
        artist.userRegistrationInfo = userRegistrationInfo;

        artist.albums = new ArrayList<>();
        artist.songs = new ArrayList<>();

        return artist;
    }

    /**
     * Method used for adding a new song to an artist.
     *
     * @param song - the song to be created.
     */
    public void addSong(Song song) {
        this.songs.add(song);
    }
}
