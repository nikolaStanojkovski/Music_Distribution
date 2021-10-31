package finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.auxiliary;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * ArtistPersonalInfo - value object that keeps the main personal information for the artist value object
 */
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ArtistPersonalInfo implements ValueObject {

    private String firstName;
    private String lastName;
    private String artName;

    public static ArtistPersonalInfo build(String firstName, String lastName, String artName) {
        return new ArtistPersonalInfo(firstName, lastName, artName);
    }
}
