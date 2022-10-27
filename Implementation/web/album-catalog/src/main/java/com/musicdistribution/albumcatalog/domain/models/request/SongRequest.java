package com.musicdistribution.albumcatalog.domain.models.request;

import com.musicdistribution.albumcatalog.domain.models.entity.Song;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
