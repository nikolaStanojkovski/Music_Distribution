package com.musicdistribution.albumcatalog.domain.models.response;

import com.musicdistribution.albumcatalog.domain.models.entity.Artist;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for an artist with a JWT token.
 */
@Data
@NoArgsConstructor
public class ArtistJwtResponse {

    private static final String type = "Bearer";

    private ArtistResponse artistResponse;
    private String jwtToken;

    public static ArtistJwtResponse from(Artist artist, String jwtToken, String encryptedId) {
        ArtistJwtResponse artistJwtResponse = new ArtistJwtResponse();
        artistJwtResponse.setArtistResponse(ArtistResponse.from(artist, encryptedId));
        artistJwtResponse.setJwtToken(jwtToken);

        return artistJwtResponse;
    }
}