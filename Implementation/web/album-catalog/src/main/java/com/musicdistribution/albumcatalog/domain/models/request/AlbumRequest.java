package com.musicdistribution.albumcatalog.domain.models.request;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * An album object used to transfer data from the front-end user form to the backend.
 */
@Data
@NoArgsConstructor
public class AlbumRequest {

    @NotBlank
    private String albumName;
    @NotBlank
    private Integer totalLength;
    @NotNull
    private Genre genre;

    @NotBlank
    private String artistName;
    @NotBlank
    private String producerName;
    @NotBlank
    private String composerName;

    @NotBlank
    private String subscriptionFee;
    @NotBlank
    private String transactionFee;
    @NotBlank
    private Tier tier;

    @NotBlank
    private String creatorId;
}
