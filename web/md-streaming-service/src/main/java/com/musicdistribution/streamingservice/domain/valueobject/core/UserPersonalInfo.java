package com.musicdistribution.streamingservice.domain.valueobject.core;

import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * A value object that contains user's personal information.
 */
@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPersonalInfo implements ValueObject, Serializable {

    private final String firstName;
    private final String lastName;
    private final String artName;

    /**
     * Protected no-args constructor for the class.
     */
    protected UserPersonalInfo() {
        this.firstName = StringUtils.EMPTY;
        this.lastName = StringUtils.EMPTY;
        this.artName = StringUtils.EMPTY;
    }

    /**
     * Method used for creating a new user personal information object.
     *
     * @param firstName - user's first name.
     * @param lastName  - user's last name.
     * @param artName   - user's art name.
     * @return the created user personal information object.
     */
    public static UserPersonalInfo from(String firstName, String lastName, String artName) {
        return new UserPersonalInfo(firstName, lastName, artName);
    }

    /**
     * Getter method for user's full name.
     *
     * @return user's full name.
     */
    public String getFullName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }
}
