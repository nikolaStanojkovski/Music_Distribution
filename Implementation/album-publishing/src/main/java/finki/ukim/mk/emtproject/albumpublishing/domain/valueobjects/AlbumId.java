package finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class AlbumId extends DomainObjectId {

    private AlbumId() {
        super(AlbumId.randomId(AlbumId.class).getId());
    }

    public AlbumId(String uuid) {
        super(uuid);
    }
}
