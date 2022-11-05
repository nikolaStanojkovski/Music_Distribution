package com.musicdistribution.albumcatalog.domain.models.response;

import com.musicdistribution.albumcatalog.domain.models.entity.Song;
import com.musicdistribution.albumcatalog.domain.valueobjects.PaymentInfo;
import com.musicdistribution.albumcatalog.domain.valueobjects.SongLength;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Objects;

/**
 * Data transfer object for a song.
 */
@Data
@NoArgsConstructor
public class SongResponse {

    private String id;
    private String songName;
    private Genre songGenre;
    private Boolean isASingle;
    private Boolean isPublished;

    private PaymentInfo paymentInfo;
    private SongLength songLength;
    private ArtistResponse creator;
    private AlbumResponse album;

    public static SongResponse from(Song song, String encryptedId,
                                    String encryptedArtistId, String encryptedAlbumId) {
        SongResponse songResponse = new SongResponse();
        songResponse.setId(encryptedId);
        songResponse.setSongName(song.getSongName());
        songResponse.setSongGenre(song.getSongGenre());
        songResponse.setIsASingle(song.getIsASingle());
        songResponse.setIsPublished(song.getIsPublished());
        songResponse.setSongLength(song.getSongLength());
        songResponse.setPaymentInfo(song.getPaymentInfo());
        songResponse.setCreator(ArtistResponse.from(song.getCreator(), encryptedArtistId));
        songResponse.setAlbum(Objects.isNull(song.getAlbum()) ? null :
                AlbumResponse.from(song.getAlbum(), encryptedAlbumId, encryptedArtistId));

        return songResponse;
    }
}
