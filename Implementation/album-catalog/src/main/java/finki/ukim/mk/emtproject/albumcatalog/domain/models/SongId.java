package finki.ukim.mk.emtproject.albumcatalog.domain.models;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

/**
 * SongId value object used as the song identifier.
 */
public class SongId extends DomainObjectId {

    private SongId() {
        super(ArtistId.randomId(ArtistId.class).getId());
    }

    public SongId(@NonNull String uuid) {
        super(uuid);
    }

    public static SongId of(String uuid) {
        return new SongId(uuid);
    }
}
