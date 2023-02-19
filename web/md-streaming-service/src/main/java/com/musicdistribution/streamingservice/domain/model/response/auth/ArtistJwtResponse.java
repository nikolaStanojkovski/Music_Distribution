package com.musicdistribution.streamingservice.domain.model.response.auth;

import com.musicdistribution.streamingservice.domain.model.entity.core.Artist;
import com.musicdistribution.streamingservice.domain.model.response.core.ArtistResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Object used to transfer JWT data from the
 * back-end to the front-end for an artist.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtistJwtResponse extends JwtResponse {

    private ArtistResponse artistResponse;

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
