package finki.ukim.mk.emtproject.albumcatalog.domain.models;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

/**
 * AlbumId value object used as the album identifier.
 */
public class AlbumId extends DomainObjectId {

    private AlbumId() {
        super(AlbumId.randomId(AlbumId.class).getId());
    }

    public AlbumId(@NonNull String uuid) {
        super(uuid);
    }

    public static AlbumId of(String uuid) {
        return new AlbumId(uuid);
    }
}
