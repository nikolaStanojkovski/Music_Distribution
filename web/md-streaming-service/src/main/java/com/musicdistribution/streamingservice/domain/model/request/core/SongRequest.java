package com.musicdistribution.streamingservice.domain.model.request.core;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * An extended song request wrapper object used to transfer
 * song form data from the front-end to the back-end.
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
