package com.musicdistribution.albumcatalog.domain.valueobjects;

import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ArtistUserInfo implements ValueObject {

    private String username;
    private String password;

    /**
     * Static method for creating a new artist user information object.
     *
     * @param username - artist's username.
     * @param password  - artist's password.
     * @return the created artist user information object.
     */
    public static ArtistUserInfo build(String username, String password) {
        return new ArtistUserInfo(username, password);
    }
}