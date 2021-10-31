package finki.ukim.mk.emtproject.albumcatalog.domain.models.response;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Artist;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.ArtistContactInfo;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.ArtistPersonalInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for an artist.
 */
@Data
@NoArgsConstructor
public class ArtistResponse {

    private String id;
    private ArtistContactInfo artistContactInfo;
    private ArtistPersonalInfo artistPersonalInfo;
    private String password;

    public static ArtistResponse from(Artist artist) {
        ArtistResponse artistResponse = new ArtistResponse();
        artistResponse.setId(artist.getId().getId());
        artistResponse.setArtistContactInfo(artist.getArtistContactInfo());
        artistResponse.setArtistPersonalInfo(artist.getArtistPersonalInfo());
        artistResponse.setPassword(artist.getPassword());

        return artistResponse;
    }
}
