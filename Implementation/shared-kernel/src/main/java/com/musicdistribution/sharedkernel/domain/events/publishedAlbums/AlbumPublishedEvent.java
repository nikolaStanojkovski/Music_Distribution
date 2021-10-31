package com.musicdistribution.sharedkernel.domain.events.publishedAlbums;

import com.musicdistribution.sharedkernel.domain.config.TopicHolder;
import com.musicdistribution.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

/**
 * AlbumPublishedEvent - specific domain event class for album publishing
 */
@Getter
public class AlbumPublishedEvent extends DomainEvent {

    private String albumId;
    private String artistId;
    private String musicPublisherId;

    private String albumTier;
    private String subscriptionFee; // in eur
    private String transactionFee; // in eur

    public AlbumPublishedEvent() {
        super(TopicHolder.TOPIC_ALBUM_PUBLISHED);
    }

    public AlbumPublishedEvent(String albumId, String artistId, String musicPublisherId, String albumTier, Double subscriptionFee, Double transactionFee) {
        super(TopicHolder.TOPIC_ALBUM_PUBLISHED);

        this.albumId = albumId;
        this.artistId = artistId;
        this.musicPublisherId = musicPublisherId;
        this.albumTier = albumTier;
        this.subscriptionFee = subscriptionFee.toString();
        this.transactionFee = transactionFee.toString();
    }
}

