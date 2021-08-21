package finki.ukim.mk.emtproject.albumpublishing.domain.models.dto;

import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Money;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;

import java.time.Instant;

/**
 * PublishedAlbumDto - Dto object for a published album
 */
@Data
public class PublishedAlbumDto {

    // published album id
    private String publishedAlbumId;

    // album
    private String albumId;
    private String albumName;
    // artist
    private String artistId;
    private String artistInformation;
    // publisher
    private String musicPublisherId;
    private String musicPublisherInfo;

    // other
    private Instant publishedOn;
    private Tier albumTier;

    // optional
    private Money totalCost;

    private Double subscriptionFee; // in eur
    private Double transactionFee; // in eur

    public PublishedAlbumDto(String publishedAlbumId, String albumId, String albumName, String artistId, String artistInformation, Instant publishedOn, Tier albumTier, Money totalCost, String musicPublisherId, String musicPublisherInfo) {
        this.publishedAlbumId = publishedAlbumId;
        this.albumId = albumId;
        this.albumName = albumName;
        this.artistId = artistId;
        this.artistInformation = artistInformation;
        this.publishedOn = publishedOn;
        this.albumTier = albumTier;
        this.totalCost = totalCost;
        this.musicPublisherId = musicPublisherId;
        this.musicPublisherInfo = musicPublisherInfo;
    }
}
