package com.musicdistribution.streamingservice.domain.model.request.core;

import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * An extended song request wrapper object used to transfer
 * song transaction data from the front-end to the back-end.
 */
@Data
@NoArgsConstructor
public class SongTransactionRequest {

    @NotBlank
    private String songId;
    @NotBlank
    private Tier songTier;
    @NotBlank
    private Money subscriptionFee;
    @NotBlank
    private Money transactionFee;
}
