package com.musicdistribution.streamingservice.domain.model.request;

import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * A song request wrapper object used to transfer short
 * song transaction data from the front-end to the back-end.
 */
@Data
@NoArgsConstructor
public class SongShortTransactionRequest {

    @NotBlank
    private String songId;
    @NotBlank
    private Tier songTier;
    @NotBlank
    private Money subscriptionFee;
    @NotBlank
    private Money transactionFee;
}
