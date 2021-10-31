package com.musicdistribution.albumpublishing.domain.models.request;

import com.musicdistribution.albumpublishing.domain.models.entity.PublishedAlbum;
import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Response object for a published album.
 */
@Data
@NoArgsConstructor
public class PublishedAlbumRequest {

    private String publishedAlbumId;

    private String albumId;
    private String albumName;

    private String artistId;
    private String artistInformation;

    private String musicPublisherId;
    private String musicPublisherInfo;

    private Instant publishedOn;
    private Tier albumTier;

    private Money totalCost;

    private Double subscriptionFee;
    private Double transactionFee;

    /**
     * Static method used for creation of a published album response object.
     *
     * @param publishedAlbum - the published album's information from which the response object is created.
     * @return the created published album response object.
     */
    public static PublishedAlbumRequest from(PublishedAlbum publishedAlbum) {
        PublishedAlbumRequest publishedAlbumRequest = new PublishedAlbumRequest();
        publishedAlbumRequest.setPublishedAlbumId(publishedAlbum.getId().getId());
        publishedAlbumRequest.setAlbumId(publishedAlbum.getAlbumId().getId());
        publishedAlbumRequest.setAlbumName(publishedAlbum.getAlbumName());
        publishedAlbumRequest.setArtistId(publishedAlbum.getArtistId().getId());
        publishedAlbumRequest.setArtistInformation(publishedAlbum.getArtistInformation());
        publishedAlbumRequest.setMusicPublisherId(publishedAlbum.getPublisher().getId().getId());
        publishedAlbumRequest.setMusicPublisherInfo(publishedAlbum.getPublisher().getDistributorInfo().getDistributorInformation());
        publishedAlbumRequest.setPublishedOn(publishedAlbum.getPublishedOn());
        publishedAlbumRequest.setAlbumTier(publishedAlbum.getAlbumTier());
        publishedAlbumRequest.setTotalCost(publishedAlbum.getTransactionFee().add(publishedAlbum.getSubscriptionFee()));
        publishedAlbumRequest.setTransactionFee(publishedAlbum.getTransactionFee().getAmount());
        publishedAlbumRequest.setSubscriptionFee(publishedAlbum.getSubscriptionFee().getAmount());

        return publishedAlbumRequest;
    }
}
