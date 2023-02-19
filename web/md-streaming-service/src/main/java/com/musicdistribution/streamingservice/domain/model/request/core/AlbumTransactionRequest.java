package com.musicdistribution.streamingservice.domain.model.request.core;

import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * An extended album request wrapper object used to transfer
 * album transaction data from the front-end to the back-end.
 */
@Data
@NoArgsConstructor
public class AlbumTransactionRequest {

    @NotBlank
    private String albumName;
    @NotNull
    private Genre albumGenre;

    private String artistName;
    private String producerName;
    private String composerName;

    @NotBlank
    @NotEmpty
    private List<String> songIdList;

    @NotNull
    private Tier albumTier;
    @NotNull
    private Money subscriptionFee;
    @NotNull
    private Money transactionFee;
}
