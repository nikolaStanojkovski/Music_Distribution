package com.musicdistribution.albumcatalog.domain.models.request;

import com.musicdistribution.albumcatalog.domain.models.entity.Song;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * An song object used to transfer data from the front-end user form to the backend.
 */
@Data
@NoArgsConstructor
public class SongRequest {

    @NotBlank
    private String songName;
    @NotNull
    private Boolean isASingle;
    @NotBlank
    private Integer lengthInSeconds;

    @NotNull
    private String creatorId;
    private String albumId;

    /**
     * Static method used for creation of a new song form object.
     *
     * @return the new song form object.
     */
    public static SongRequest build(Song song) {
        SongRequest songRequest = new SongRequest();
        songRequest.setSongName(song.getSongName());
        songRequest.setIsASingle(song.getIsASingle());
        songRequest.setLengthInSeconds(song.getSongLength().getLengthInSeconds());
        songRequest.setCreatorId(song.getCreator().getId().getId());
        songRequest.setAlbumId(Objects.isNull(song.getAlbum()) ? null : song.getAlbum().getId().getId());

        return songRequest;
    }
}
