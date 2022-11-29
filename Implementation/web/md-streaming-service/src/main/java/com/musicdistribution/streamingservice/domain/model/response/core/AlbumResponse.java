package com.musicdistribution.streamingservice.domain.model.response.core;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import com.musicdistribution.streamingservice.domain.model.entity.core.Album;
import com.musicdistribution.streamingservice.domain.valueobject.core.AlbumInfo;
import com.musicdistribution.streamingservice.domain.valueobject.PaymentInfo;
import com.musicdistribution.streamingservice.domain.valueobject.core.SongLength;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Object used for data transfer from the
 * back-end to the front-end for an album.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumResponse {

    private String id;
    private String albumName;
    private Genre genre;

    private PaymentInfo paymentInfo;
    private SongLength totalLength;
    private AlbumInfo albumInfo;
    private ArtistResponse creator;

    /**
     * Method used for building an album response object.
     *
     * @param album             - the album entity from which the properties are to be read from.
     * @param encryptedId       - the encrypted ID of the album entity.
     * @param encryptedArtistId - the encrypted ID of the artist entity.
     * @return the created response object.
     */
    public static AlbumResponse from(Album album, String encryptedId, String encryptedArtistId) {
        AlbumResponse albumResponse = new AlbumResponse();
        albumResponse.setId(encryptedId);
        albumResponse.setAlbumName(album.getAlbumName());
        albumResponse.setTotalLength(album.getTotalLength());
        albumResponse.setGenre(album.getGenre());
        albumResponse.setAlbumInfo(album.getAlbumInfo());
        albumResponse.setPaymentInfo(album.getPaymentInfo());
        albumResponse.setCreator(Objects.isNull(album.getCreator()) ? null
                : ArtistResponse.from(album.getCreator(), encryptedArtistId));

        return albumResponse;
    }
}
