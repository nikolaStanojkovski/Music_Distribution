package finki.ukim.mk.emtproject.albumpublishing.domain.models;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class MusicDistributorId extends DomainObjectId {

    private MusicDistributorId() {
        super(MusicDistributorId.randomId(MusicDistributorId.class).getId());
    }

    public MusicDistributorId(@NonNull String uuid) {
        super(uuid);
    }

    public static MusicDistributorId of(String uuid) {
        MusicDistributorId m = new MusicDistributorId(uuid);
        return m;
    }
}
