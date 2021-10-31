package finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.ValueObject;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Email;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * Value object for an artist contact information that contains the email and telephone number of the artist.
 */
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ArtistContactInfo implements ValueObject {

    private Email email;

    private String telephoneNumber;

    /**
     * Static method for creating a new artist contact information object.
     *
     * @param telephoneNumber - artist's telephone number.
     * @param username        - artist's username.
     * @param emailDomain     - artist's email domain.
     * @return the created artist contact information object.
     */
    public static ArtistContactInfo build(String telephoneNumber, String username, EmailDomain emailDomain) {
        return new ArtistContactInfo(Email.createEmail(username, emailDomain), telephoneNumber);
    }
}
