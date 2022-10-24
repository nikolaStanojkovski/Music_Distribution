package com.musicdistribution.albumcatalog.domain.models.request;

import com.musicdistribution.albumcatalog.domain.models.entity.Song;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
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
    @NotNull
    private Boolean isASingle;
    @NotBlank
    private Integer lengthInSeconds;

    /**
     * Static method used for creation of a new song form object.
     *
     * @return the new song form object.
     */
    public static SongRequest build(Song song) {
        SongRequest songRequest = new SongRequest();
        songRequest.setSongName(song.getSongName());
        songRequest.setSongGenre(song.getSongGenre());
        songRequest.setIsASingle(song.getIsASingle());
        songRequest.setLengthInSeconds(song.getSongLength().getLengthInSeconds());

        return songRequest;
    }
}
