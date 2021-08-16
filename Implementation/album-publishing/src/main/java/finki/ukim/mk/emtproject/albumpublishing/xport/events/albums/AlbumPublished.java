package finki.ukim.mk.emtproject.albumpublishing.xport.events.albums;

import finki.ukim.mk.emtproject.sharedkernel.domain.events.DomainEvent;
import lombok.Getter;

@Getter
public class AlbumPublished extends DomainEvent {

    public AlbumPublished(String topic) {
        super(topic);
    }
}
