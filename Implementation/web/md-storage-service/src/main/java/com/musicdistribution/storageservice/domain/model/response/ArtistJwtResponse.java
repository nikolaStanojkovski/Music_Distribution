package com.musicdistribution.storageservice.domain.model.response;

import com.musicdistribution.storageservice.domain.model.entity.Artist;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Object used to transfer JWT data from the
 * back-end to the front-end.
 */
@Data
@NoArgsConstructor
public class ArtistJwtResponse {

    private ArtistResponse artistResponse;
    private String jwtToken;

    /**
     * Method used for building an album response object.
     *
     * @param artist      - the artist entity from which the properties are to be read from.
     * @param jwtToken    - the JWT token used for user authentication.
     * @param encryptedId - the encrypted ID of the artist entity.
     * @return the created response object.
     */
    public static ArtistJwtResponse from(Artist artist, String jwtToken, String encryptedId) {
        ArtistJwtResponse artistJwtResponse = new ArtistJwtResponse();
        artistJwtResponse.setArtistResponse(ArtistResponse.from(artist, encryptedId));
        artistJwtResponse.setJwtToken(jwtToken);

        return artistJwtResponse;
    }
}
