package finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * Value object for an artist personal information that contains the first name, last name and art name of the artist.
 */
@Getter
@Embeddable
@AllArgsConstructor
public class ArtistPersonalInfo implements ValueObject {

    private final String firstName;
    private final String lastName;
    private final String artName;

    /**
     * Protected no-args constructor for ArtistPersonalInfo.
     */
    protected ArtistPersonalInfo() {
        this.firstName = "";
        this.lastName = "";
        this.artName = "";
    }

    /**
     * Static method for creating a new artist personal information object.
     *
     * @param firstName - artist's first name.
     * @param lastName  - artist's last name.
     * @param artName   - artist's art name.
     * @return the created artist personal information object.
     */
    public static ArtistPersonalInfo build(String firstName, String lastName, String artName) {
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
