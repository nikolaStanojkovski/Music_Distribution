package com.musicdistribution.sharedkernel.domain.events.publishedAlbums;

import com.musicdistribution.sharedkernel.domain.config.TopicHolder;
import com.musicdistribution.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

/**
 * Specific domain event class for album publishing.
 */
@Getter
public class AlbumPublishedEvent extends DomainEvent {

    private String albumId;
    private String artistId;
    private String musicPublisherId;

    private String albumTier;
    private String subscriptionFee;
    private String transactionFee;

    /**
     * Public no-args constructor for the published album event.
     */
    public AlbumPublishedEvent() {
        super(TopicHolder.TOPIC_ALBUM_PUBLISHED);
    }

    /**
     * Public args constructor for the album publishing event.
     *
     * @param albumId          - album's id.
     * @param artistId         - artist's id.
     * @param musicPublisherId - music publisher's id.
     * @param albumTier        - album's tier.
     * @param subscriptionFee  - album's subscription fee.
     * @param transactionFee   - album's transaction fee.
     */
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

