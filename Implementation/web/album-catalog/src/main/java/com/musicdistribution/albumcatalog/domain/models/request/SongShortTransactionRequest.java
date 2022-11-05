package com.musicdistribution.albumcatalog.domain.models.request;

import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * A song object used to transfer data from the front-end user form to the backend.
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
