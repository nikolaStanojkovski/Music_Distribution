package com.musicdistribution.albumcatalog.domain.models.request;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * An album object used to transfer transaction data from the front-end user form to the backend.
 */
@Data
@NoArgsConstructor
public class AlbumTransactionRequest {

    @NotBlank
    private String albumName;
    @NotBlank
    private Genre albumGenre;
    private String artistName;
    private String producerName;
    private String composerName;

    @NotBlank
    @NotEmpty
    private List<String> songIdList;

    @NotBlank
    private Tier albumTier;
    @NotBlank
    private String subscriptionFee;
    @NotBlank
    private String transactionFee;
}
