package finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects;

import finki.ukim.mk.emtproject.albumpublishing.domain.models.MusicDistributorId;
import finki.ukim.mk.emtproject.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

/**
 * AlbumId - value object that keeps the main identifier for the album value object
 */
@Embeddable
public class AlbumId extends DomainObjectId {

    private AlbumId() {
        super(AlbumId.randomId(AlbumId.class).getId());
    }

    public AlbumId(String uuid) {
        super(uuid);
    }

    public static AlbumId of(String uuid) {
        AlbumId a = new AlbumId(uuid);
        return a;
    }
}
