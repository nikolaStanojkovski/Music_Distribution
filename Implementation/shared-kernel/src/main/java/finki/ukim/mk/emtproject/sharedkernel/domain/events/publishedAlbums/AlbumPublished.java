package finki.ukim.mk.emtproject.sharedkernel.domain.events.publishedAlbums;

import finki.ukim.mk.emtproject.sharedkernel.domain.events.DomainEvent;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Getter;

@Getter
public class AlbumPublished extends DomainEvent {

    private String albumId;
    private String artistId;
    private String musicPublisherId;

    private Tier albumTier;
    private Double subscriptionFee; // in eur
    private Double transactionFee; // in eur

    public AlbumPublished() {
        super(finki.ukim.mk.emtproject.sharedkernel.domain.config.TopicHolder.TOPIC_ALBUM_PUBLISHED);
    }

    public AlbumPublished(String albumId, String artistId, String musicPublisherId, Tier albumTier, Double subscriptionFee, Double transactionFee) {
        super(finki.ukim.mk.emtproject.sharedkernel.domain.config.TopicHolder.TOPIC_ALBUM_PUBLISHED);

        this.albumId = albumId;
        this.artistId = artistId;
        this.musicPublisherId = musicPublisherId;
        this.albumTier = albumTier;
        this.subscriptionFee = subscriptionFee;
        this.transactionFee = transactionFee;
    }
}

