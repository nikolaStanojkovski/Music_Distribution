package com.musicdistribution.albumcatalog.xport.events;

import com.musicdistribution.albumcatalog.domain.models.entity.AlbumId;
import com.musicdistribution.albumcatalog.services.AlbumService;
import com.musicdistribution.sharedkernel.domain.config.TopicHolder;
import com.musicdistribution.sharedkernel.domain.events.DomainEvent;
import com.musicdistribution.sharedkernel.domain.events.publishedAlbums.AlbumPublishedEvent;
import com.musicdistribution.sharedkernel.domain.events.publishedAlbums.AlbumUnpublishedEvent;
import com.musicdistribution.sharedkernel.domain.events.publishedAlbums.RaisedAlbumTierEvent;
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
