package com.musicdistribution.albumpublishing.domain.valueobjects.auxiliary;

import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * Value object that keeps the personal information for the artist value object.
 */
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ArtistPersonalInfo implements ValueObject {

    private String firstName;
    private String lastName;
    private String artName;

    /**
     * Static method that is used for creation of a new object for artist's personal information.
     *
     * @param firstName - artist's first name.
     * @param lastName  - artist's last name.
     * @param artName   - artist's art name.
     * @return the created ArtistPersonalInfo object.
     */
    public static ArtistPersonalInfo build(String firstName, String lastName, String artName) {
        return new ArtistPersonalInfo(firstName, lastName, artName);
    }
}
