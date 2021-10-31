package finki.ukim.mk.emtproject.albumcatalog.domain.models;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

/**
 * ArtistId value object used as the artist identifier.
 */
public class ArtistId extends DomainObjectId {

    private ArtistId() {
        super(ArtistId.randomId(ArtistId.class).getId());
    }

    public ArtistId(@NonNull String uuid) {
        super(uuid);
    }

    public static ArtistId of(String uuid) {
        return new ArtistId(uuid);
    }
}
