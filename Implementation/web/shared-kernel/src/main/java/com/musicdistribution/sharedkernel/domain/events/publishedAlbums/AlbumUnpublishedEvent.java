package com.musicdistribution.sharedkernel.domain.events.publishedAlbums;

import com.musicdistribution.sharedkernel.domain.config.TopicHolder;
import com.musicdistribution.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

/**
 * Specific domain event class for making an album unpublished.
 */
@Getter
public class AlbumUnpublishedEvent extends DomainEvent {

    private String albumId;

    /**
     * Public no-args constructor for the album unpublished event.
     */
    public AlbumUnpublishedEvent() {
        super(TopicHolder.TOPIC_ALBUM_UNPUBLISHED);
    }

    /**
     * Public args constructor for album unpublished event.
     *
     * @param albumId - album's id.
     */
    public AlbumUnpublishedEvent(String albumId) {
        super(TopicHolder.TOPIC_ALBUM_UNPUBLISHED);
        this.albumId = albumId;
    }
}
