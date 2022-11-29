package com.musicdistribution.streamingservice.domain.valueobject.core;

import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * A value object that contains user's user information.
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRegistrationInfo implements ValueObject, Serializable {

    private String username;
    private String password;

    /**
     * Method used for creating a new user user information object.
     *
     * @param username - user's username.
     * @param password - user's password.
     * @return the created user user information object.
     */
    public static UserRegistrationInfo from(String username, String password) {
        return new UserRegistrationInfo(username, password);
    }
}