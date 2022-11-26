package com.musicdistribution.streamingservice.domain.model.entity;

import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import com.musicdistribution.sharedkernel.domain.valueobjects.Email;
import com.musicdistribution.streamingservice.constant.EntityConstants;
import com.musicdistribution.streamingservice.domain.model.entity.id.ListenerId;
import com.musicdistribution.streamingservice.domain.valueobject.UserRegistrationInfo;
import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Album> favouriteAlbums;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Song> favouriteSongs;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Artist> favouriteArtists;

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

        listener.favouriteAlbums = new ArrayList<>();
        listener.favouriteSongs = new ArrayList<>();
        listener.favouriteArtists = new ArrayList<>();

        return listener;
    }
}
