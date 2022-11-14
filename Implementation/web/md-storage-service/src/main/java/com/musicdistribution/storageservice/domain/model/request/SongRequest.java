package com.musicdistribution.storageservice.domain.model.request;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * An song object used to transfer data from the front-end user form to the backend.
 */
@Data
@NoArgsConstructor
public class SongRequest {

    @NotBlank
    private String songName;
    @NotBlank
    private Genre songGenre;
    @NotBlank
    private String songId;
    @NotNull
    private Boolean isASingle;
    @NotBlank
    private Integer lengthInSeconds;
}
