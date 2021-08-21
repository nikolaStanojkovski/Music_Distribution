package finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

/**
 * ArtistId - value object that keeps the main identifier for the artist value object
 */
@Embeddable
public class ArtistId extends DomainObjectId {

    private ArtistId() {
        super(ArtistId.randomId(ArtistId.class).getId());
    }

    public ArtistId(String uuid) {
        super(uuid);
    }

    public static ArtistId of(String uuid) {
        ArtistId a = new ArtistId(uuid);
        return a;
    }
}
