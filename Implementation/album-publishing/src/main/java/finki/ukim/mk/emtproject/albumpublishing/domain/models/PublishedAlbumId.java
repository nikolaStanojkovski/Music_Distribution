package finki.ukim.mk.emtproject.albumpublishing.domain.models;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class PublishedAlbumId extends DomainObjectId {

    private PublishedAlbumId() {
        super(PublishedAlbumId.randomId(PublishedAlbumId.class).getId());
    }

    public PublishedAlbumId(@NonNull String uuid) {
        super(uuid);
    }

    public static PublishedAlbumId of(String uuid) {
        PublishedAlbumId p = new PublishedAlbumId(uuid);
        return p;
    }
}
