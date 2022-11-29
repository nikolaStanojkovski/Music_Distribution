package com.musicdistribution.streamingservice.domain.model.response.core;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import com.musicdistribution.streamingservice.domain.model.entity.core.Song;
import com.musicdistribution.streamingservice.domain.valueobject.PaymentInfo;
import com.musicdistribution.streamingservice.domain.valueobject.core.SongLength;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Object used for data transfer from the
 * back-end to the front-end for a song.
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

    /**
     * Method used for building a song response object.
     *
     * @param song              - the song entity from which the properties are to be read from.
     * @param encryptedId       - the encrypted ID of the song entity.
     * @param encryptedArtistId - the encrypted ID of the song's artist entity.
     * @param encryptedAlbumId  - the encrypted ID of the song's album entity.
     * @return the created response object.
     */
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
