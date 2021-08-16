package finki.ukim.mk.emtproject.albumpublishing.services.form;

import finki.ukim.mk.emtproject.albumpublishing.domain.models.MusicDistributorId;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.Album;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.AlbumId;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.Artist;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.ArtistId;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Money;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;

import java.time.Instant;

@Data
public class PublishedAlbumForm {

    private Album album;

    private Artist artist;

    private MusicDistributorId musicDistributorId;


    private Tier albumTier;

    private Money subscriptionFee;

    private Money transactionFee;
}
