package finki.ukim.mk.emtproject.sharedkernel.domain.events.publishedAlbums;

import finki.ukim.mk.emtproject.sharedkernel.domain.events.DomainEvent;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Tier;
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
        super(finki.ukim.mk.emtproject.sharedkernel.domain.config.TopicHolder.TOPIC_ALBUM_PUBLISHED);
    }

    public AlbumPublishedEvent(String albumId, String artistId, String musicPublisherId, String albumTier, Double subscriptionFee, Double transactionFee) {
        super(finki.ukim.mk.emtproject.sharedkernel.domain.config.TopicHolder.TOPIC_ALBUM_PUBLISHED);

        this.albumId = albumId;
        this.artistId = artistId;
        this.musicPublisherId = musicPublisherId;
        this.albumTier = albumTier;
        this.subscriptionFee = subscriptionFee.toString();
        this.transactionFee = transactionFee.toString();
    }
}

