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
    private String password;

    public static ArtistResponse from(Artist artist) {
        ArtistResponse artistResponse = new ArtistResponse();
        artistResponse.setId(artist.getId().getId());
        artistResponse.setEmail(artist.getArtistContactInfo().getEmail().getFullAddress());
        artistResponse.setArtistContactInfo(artist.getArtistContactInfo());
        artistResponse.setArtistPersonalInfo(artist.getArtistPersonalInfo());
        artistResponse.setPassword(artist.getPassword());

        return artistResponse;
    }
}
