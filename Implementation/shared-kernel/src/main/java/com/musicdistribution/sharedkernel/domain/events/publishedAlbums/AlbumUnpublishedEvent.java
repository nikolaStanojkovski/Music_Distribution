package com.musicdistribution.sharedkernel.domain.events.publishedAlbums;

import com.musicdistribution.sharedkernel.domain.config.TopicHolder;
import com.musicdistribution.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

/**
 * AlbumUnpublishedEvent - specific domain event class for album unpublishing
 */
@Getter
public class AlbumUnpublishedEvent extends DomainEvent {

    private String albumId;

    public AlbumUnpublishedEvent() {
        super(TopicHolder.TOPIC_ALBUM_UNPUBLISHED);
    }

    public AlbumUnpublishedEvent(String albumId) {
        super(TopicHolder.TOPIC_ALBUM_UNPUBLISHED);
        this.albumId = albumId;
    }
}
