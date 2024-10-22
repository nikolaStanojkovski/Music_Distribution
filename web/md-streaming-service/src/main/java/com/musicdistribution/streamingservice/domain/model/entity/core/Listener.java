package com.musicdistribution.streamingservice.domain.model.entity.core;

import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import com.musicdistribution.sharedkernel.domain.valueobjects.Email;
import com.musicdistribution.streamingservice.constant.EntityConstants;
import com.musicdistribution.streamingservice.domain.model.entity.id.ListenerId;
import com.musicdistribution.streamingservice.domain.valueobject.core.UserRegistrationInfo;
import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Listener domain entity.
 */
@Entity
@Getter
@Table(name = EntityConstants.LISTENER)
public class Listener extends AbstractEntity<ListenerId> implements Serializable {

    @AttributeOverrides({
            @AttributeOverride(name = EntityConstants.USERNAME,
                    column = @Column(name = EntityConstants.LISTENER_USERNAME)),
            @AttributeOverride(name = EntityConstants.PASSWORD,
                    column = @Column(name = EntityConstants.LISTENER_PASSWORD))
    })
    private UserRegistrationInfo userRegistrationInfo;

    @AttributeOverrides({
            @AttributeOverride(name = EntityConstants.DOMAIN_USERNAME,
                    column = @Column(name = EntityConstants.LISTENER_EMAIL_DOMAIN_USERNAME)),
            @AttributeOverride(name = EntityConstants.DOMAIN_NAME,
                    column = @Column(name = EntityConstants.LISTENER_EMAIL_DOMAIN_NAME))
    })
    private Email userEmail;

    @ManyToMany(fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Album> favouriteAlbums;

    @ManyToMany(fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Song> favouriteSongs;

    @ManyToMany(fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Artist> favouriteArtists;

    @OneToMany(fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Notification> notifications;

    /**
     * Protected no args constructor used for creating a new Listener entity.
     */
    protected Listener() {
        super(ListenerId.randomId(ListenerId.class));
    }

    /**
     * Static method for creating a new listener.
     *
     * @param userRegistrationInfo - listener's registration information.
     * @param userEmail            - listener's email address.
     * @return the created listener.
     */
    public static Listener build(UserRegistrationInfo userRegistrationInfo,
                                 Email userEmail) {
        Listener listener = new Listener();
        listener.userRegistrationInfo = userRegistrationInfo;
        listener.userEmail = userEmail;

        listener.favouriteAlbums = new LinkedHashSet<>();
        listener.favouriteSongs = new LinkedHashSet<>();
        listener.favouriteArtists = new LinkedHashSet<>();

        return listener;
    }

    /**
     * Method used for adding an artist to the favourites list.
     *
     * @param artist - the artist to be added to the favourites list.
     */
    public void addFavouriteArtist(Artist artist) {
        this.favouriteArtists.add(artist);
    }

    /**
     * Method used for adding an album to the favourites list.
     *
     * @param album - the album to be added to the favourites list.
     */
    public void addFavouriteAlbum(Album album) {
        this.favouriteAlbums.add(album);
    }

    /**
     * Method used for adding an song to the favourites list.
     *
     * @param song - the song to be added to the favourites list.
     */
    public void addFavouriteSong(Song song) {
        this.favouriteSongs.add(song);
    }

    /**
     * Method used for removing an artist from the favourites list.
     *
     * @param artist - the artist to be removed from the favourites list.
     */
    public void removeFavouriteArtist(Artist artist) {
        this.favouriteArtists.remove(artist);
    }

    /**
     * Method used for removing an album from the favourites list.
     *
     * @param album - the album to be removed from the favourites list.
     */
    public void removeFavouriteAlbum(Album album) {
        this.favouriteAlbums.remove(album);
    }

    /**
     * Method used for removing a song from the favourites list.
     *
     * @param song - the song to be removed from the favourites list.
     */
    public void removeFavouriteSong(Song song) {
        this.favouriteSongs.remove(song);
    }
}
