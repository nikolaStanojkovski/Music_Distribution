package com.musicdistribution.storageservice.domain.model.response;

import com.musicdistribution.storageservice.domain.model.entity.Artist;
import com.musicdistribution.storageservice.domain.valueobject.ArtistContactInfo;
import com.musicdistribution.storageservice.domain.valueobject.ArtistPersonalInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Object used for data transfer from the
 * back-end to the front-end for an artist.
 */
@Data
@NoArgsConstructor
public class ArtistResponse {

    private String id;
    private String email;
    private ArtistContactInfo artistContactInfo;
    private ArtistPersonalInfo artistPersonalInfo;

    /**
     * Method used for building an artist response object.
     *
     * @param artist      - the artist entity from which the properties are to be read from.
     * @param encryptedId - the encrypted ID of the album entity.
     * @return the created response object.
     */
    public static ArtistResponse from(Artist artist, String encryptedId) {
        ArtistResponse artistResponse = new ArtistResponse();
        artistResponse.setId(encryptedId);
        artistResponse.setArtistContactInfo(artist.getArtistContactInfo());
        artistResponse.setArtistPersonalInfo(artist.getArtistPersonalInfo());
        artistResponse.setEmail(artist.getArtistContactInfo().getEmail().getFullAddress());

        return artistResponse;
    }
}
