package com.musicdistribution.streamingservice.domain.model.response.core;

import com.musicdistribution.streamingservice.domain.model.entity.core.Artist;
import com.musicdistribution.streamingservice.domain.valueobject.core.UserContactInfo;
import com.musicdistribution.streamingservice.domain.valueobject.core.UserPersonalInfo;
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
    private UserContactInfo userContactInfo;
    private UserPersonalInfo userPersonalInfo;

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
        artistResponse.setUserContactInfo(artist.getUserContactInfo());
        artistResponse.setUserPersonalInfo(artist.getUserPersonalInfo());
        artistResponse.setEmail(artist.getUserContactInfo().getEmail().getFullAddress());

        return artistResponse;
    }
}
