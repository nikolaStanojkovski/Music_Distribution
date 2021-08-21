package finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.ValueObject;
import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * ArtistPersonalInfo - Value object for personal information of an artist
 */
@Embeddable
@Getter
public class ArtistPersonalInfo implements ValueObject {

    private String firstName;

    private String lastName;

    private String artName;

    public static ArtistPersonalInfo build(String firstName, String lastName, String artName) {
        return new ArtistPersonalInfo(firstName, lastName, artName);
    }

    public ArtistPersonalInfo(String firstName, String lastName, String artName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.artName = artName;
    }

    protected ArtistPersonalInfo() {
        this.firstName = "";
        this.lastName = "";
        this.artName = "";
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
