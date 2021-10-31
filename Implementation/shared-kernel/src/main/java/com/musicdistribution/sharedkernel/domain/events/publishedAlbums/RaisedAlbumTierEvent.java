package com.musicdistribution.sharedkernel.domain.events.publishedAlbums;

import com.musicdistribution.sharedkernel.domain.config.TopicHolder;
import com.musicdistribution.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

/**
 * RaisedAlbumTierEvent - specific domain event class for raising an album tier
 */
@Getter
public class RaisedAlbumTierEvent extends DomainEvent {

    private String publishedAlbumId;
    private String albumTier;

    public RaisedAlbumTierEvent() {
        super(TopicHolder.TOPIC_ALBUM_TIER_RAISED);
    }

    public RaisedAlbumTierEvent(String publishedAlbumId, String albumTier) {
        super(TopicHolder.TOPIC_ALBUM_TIER_RAISED);
        this.publishedAlbumId = publishedAlbumId;
        this.albumTier = albumTier;
    }
}
