package com.musicdistribution.albumpublishing.domain.models.request;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Object used to transfer data from the front-end user form to the backend.
 */
@Data
public class AlbumPublishRequest {

    private String albumId;
    private String artistId;
    private String musicPublisherId;

    private Tier albumTier;
    private Double subscriptionFee;
    private Double transactionFee;
}
