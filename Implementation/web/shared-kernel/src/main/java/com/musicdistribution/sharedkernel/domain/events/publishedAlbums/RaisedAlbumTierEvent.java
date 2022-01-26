package com.musicdistribution.sharedkernel.domain.events.publishedAlbums;

import com.musicdistribution.sharedkernel.domain.config.TopicHolder;
import com.musicdistribution.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

/**
 * Specific domain event class for raising an album tier.
 */
@Getter
public class RaisedAlbumTierEvent extends DomainEvent {

    private String publishedAlbumId;
    private String albumTier;

    /**
     * Public no-args constructor for the album raise tier event.
     */
    public RaisedAlbumTierEvent() {
        super(TopicHolder.TOPIC_ALBUM_TIER_RAISED);
    }

    /**
     * Public args constructor for the album raise tier event.
     *
     * @param publishedAlbumId - published album's id.
     * @param albumTier        - album's tier.
     */
    public RaisedAlbumTierEvent(String publishedAlbumId, String albumTier) {
        super(TopicHolder.TOPIC_ALBUM_TIER_RAISED);
        this.publishedAlbumId = publishedAlbumId;
        this.albumTier = albumTier;
    }
}
