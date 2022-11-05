package com.musicdistribution.albumcatalog.domain.models.request;

import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * An album object used to transfer data from the front-end user form to the backend.
 */
@Data
@NoArgsConstructor
public class AlbumShortTransactionRequest {

    @NotBlank
    private String albumId;
    @NotBlank
    private Tier albumTier;
    @NotBlank
    private Money subscriptionFee;
    @NotBlank
    private Money transactionFee;
}
