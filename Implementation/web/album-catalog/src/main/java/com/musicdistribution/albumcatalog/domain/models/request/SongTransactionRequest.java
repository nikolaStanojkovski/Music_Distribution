package com.musicdistribution.albumcatalog.domain.models.request;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * An song object used to transfer transaction data from the front-end user form to the backend.
 */
@Data
@NoArgsConstructor
public class SongTransactionRequest {

    @NotBlank
    private String songId;
    @NotBlank
    private Tier songTier;
    @NotBlank
    private String subscriptionFee;
    @NotBlank
    private String transactionFee;
}
