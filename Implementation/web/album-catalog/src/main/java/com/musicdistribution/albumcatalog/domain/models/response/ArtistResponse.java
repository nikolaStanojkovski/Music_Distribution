package com.musicdistribution.albumcatalog.domain.models.response;

import com.musicdistribution.albumcatalog.domain.models.entity.Artist;
import com.musicdistribution.albumcatalog.domain.valueobjects.ArtistContactInfo;
import com.musicdistribution.albumcatalog.domain.valueobjects.ArtistPersonalInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for an artist.
 */
@Data
@NoArgsConstructor
public class ArtistResponse {

    private String id;
    private String email;
    private ArtistContactInfo artistContactInfo;
    private ArtistPersonalInfo artistPersonalInfo;

    public static ArtistResponse from(Artist artist, String encryptedId) {
        ArtistResponse artistResponse = new ArtistResponse();
        artistResponse.setId(encryptedId);
        artistResponse.setArtistContactInfo(artist.getArtistContactInfo());
        artistResponse.setArtistPersonalInfo(artist.getArtistPersonalInfo());
        artistResponse.setEmail(artist.getArtistContactInfo().getEmail().getFullAddress());

        return artistResponse;
    }
}
