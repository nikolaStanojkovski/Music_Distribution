package finki.ukim.mk.emtproject.albumpublishing.domain.exceptions;

import finki.ukim.mk.emtproject.albumpublishing.domain.models.MusicDistributorId;

public class MusicDistributorNotFoundException extends RuntimeException {

    public MusicDistributorNotFoundException(MusicDistributorId id) {
        super(String.format("Music distributor with id %s doesn't exist", id.getId()));
    }
}
