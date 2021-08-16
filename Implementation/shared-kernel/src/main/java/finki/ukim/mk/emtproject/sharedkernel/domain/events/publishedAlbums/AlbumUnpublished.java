package finki.ukim.mk.emtproject.sharedkernel.domain.events.publishedAlbums;

import finki.ukim.mk.emtproject.sharedkernel.domain.config.TopicHolder;
import finki.ukim.mk.emtproject.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

@Getter
public class AlbumUnpublished extends DomainEvent {

    private String albumId;

    public AlbumUnpublished() {
        super(TopicHolder.TOPIC_ALBUM_UNPUBLISHED);
    }

    public AlbumUnpublished(String albumId) {
        super(TopicHolder.TOPIC_ALBUM_UNPUBLISHED);
        this.albumId = albumId;
    }
}
