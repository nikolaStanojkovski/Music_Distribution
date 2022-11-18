package com.musicdistribution.storageservice.domain.valueobject;

import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Embeddable;

/**
 * A value object that contains artist's personal information.
 */
@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtistPersonalInfo implements ValueObject {

    private final String firstName;
    private final String lastName;
    private final String artName;

    /**
     * Protected no-args constructor for the class.
     */
    protected ArtistPersonalInfo() {
        this.firstName = StringUtils.EMPTY;
        this.lastName = StringUtils.EMPTY;
        this.artName = StringUtils.EMPTY;
    }

    /**
     * Method used for creating a new artist personal information object.
     *
     * @param firstName - artist's first name.
     * @param lastName  - artist's last name.
     * @param artName   - artist's art name.
     * @return the created artist personal information object.
     */
    public static ArtistPersonalInfo from(String firstName, String lastName, String artName) {
        return new ArtistPersonalInfo(firstName, lastName, artName);
    }

    /**
     * Getter method for artist's full name.
     *
     * @return artist's full name.
     */
    public String getFullName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }
}
