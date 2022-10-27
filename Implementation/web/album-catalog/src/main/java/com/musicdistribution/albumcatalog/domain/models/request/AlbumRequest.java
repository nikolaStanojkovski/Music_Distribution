package com.musicdistribution.albumcatalog.domain.models.request;

import com.musicdistribution.albumcatalog.domain.models.entity.Album;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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
    @NotBlank
    private Boolean isPublished;
    @NotNull
    private Genre genre;

    @NotBlank
    private String artistName;
    @NotBlank
    private String producerName;
    @NotBlank
    private String composerName;

    private String creatorId;
}
