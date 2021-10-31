package finki.ukim.mk.emtproject.albumcatalog.xport.events;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.AlbumId;
import finki.ukim.mk.emtproject.albumcatalog.services.AlbumService;
import finki.ukim.mk.emtproject.sharedkernel.domain.config.TopicHolder;
import finki.ukim.mk.emtproject.sharedkernel.domain.events.DomainEvent;
import finki.ukim.mk.emtproject.sharedkernel.domain.events.publishedAlbums.AlbumPublishedEvent;
import finki.ukim.mk.emtproject.sharedkernel.domain.events.publishedAlbums.AlbumUnpublishedEvent;
import finki.ukim.mk.emtproject.sharedkernel.domain.events.publishedAlbums.RaisedAlbumTierEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Class that consumes the events for album publishing.
 */
@Service
@AllArgsConstructor
public class PublishedAlbumEventListener {

    private final AlbumService albumService;

    /**
     * Event used for receiving a notification about an published album.
     *
     * @param jsonMessage - the message that is received from the published event.
     */
    @KafkaListener(topics = TopicHolder.TOPIC_ALBUM_PUBLISHED, groupId = "albumCatalog")
    public void consumePublishedAlbumEvent(String jsonMessage) {
        try {
            AlbumPublishedEvent event = DomainEvent.fromJson(jsonMessage, AlbumPublishedEvent.class);
            albumService.albumPublished(AlbumId.of(event.getAlbumId()));
        } catch (Exception ignored) {
        }
    }

    /**
     * Event used for receiving a notification about an unpublished album.
     *
     * @param jsonMessage - the message that is received from the published event.
     */
    @KafkaListener(topics = TopicHolder.TOPIC_ALBUM_UNPUBLISHED, groupId = "albumCatalog")
    public void consumeUnpublishedAlbumEvent(String jsonMessage) {
        try {
            AlbumUnpublishedEvent event = DomainEvent.fromJson(jsonMessage, AlbumUnpublishedEvent.class);
            albumService.albumUnpublished(AlbumId.of(event.getAlbumId()));
        } catch (Exception ignored) {
        }
    }

    /**
     * Event used for receiving a notification about a raised album tier.
     *
     * @param jsonMessage - the message that is received from the published event.
     */
    @KafkaListener(topics = TopicHolder.TOPIC_ALBUM_TIER_RAISED, groupId = "albumCatalog")
    public void consumeAlbumRaisedTierEvent(String jsonMessage) {
        try {
            RaisedAlbumTierEvent event = DomainEvent.fromJson(jsonMessage, RaisedAlbumTierEvent.class);
            System.out.println("An album tier for published album with id " + event.getPublishedAlbumId() + " has been raised to " + event.getAlbumTier());
        } catch (Exception ignored) {
        }
    }
}
