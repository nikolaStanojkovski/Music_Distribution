package com.musicdistribution.streamingservice.domain.model.request;

import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * An album request wrapper object used to transfer short
 * album transaction data from the front-end to the back-end.
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
