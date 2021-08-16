package finki.ukim.mk.emtproject.albumpublishing.domain.models.dto;

import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Money;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;

import java.time.Instant;

@Data
public class PublishedAlbumDto {

    private String albumId;
    private String albumName;

    private String artistId;
    private String artistInformation;

    private Instant publishedOn;
    private Tier albumTier;
    private Money totalCost;

    private String musicPublisherId;
    private String musicPublisherInfo;

    public PublishedAlbumDto(String albumId, String artistId, Instant publishedOn, Tier albumTier, Money totalCost, String musicPublisherId, String musicPublisherInfo) {
        this.albumId = albumId;
        this.artistId = artistId;
        this.publishedOn = publishedOn;
        this.albumTier = albumTier;
        this.totalCost = totalCost;
        this.musicPublisherId = musicPublisherId;
        this.musicPublisherInfo = musicPublisherInfo;
    }
}
