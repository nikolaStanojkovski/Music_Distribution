package finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.ValueObject;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Email;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * ArtistContactInfo - Value object for the artist contact information that contains the email and the telephone number
 */
@Embeddable
@Getter
public class ArtistContactInfo implements ValueObject {

    private Email email;

    private String telephoneNumber;

    public ArtistContactInfo(Email email, String telephoneNumber) {
        this.email = email;
        this.telephoneNumber = telephoneNumber;
    }

    protected ArtistContactInfo() {
    }

    public static ArtistContactInfo build(String telephoneNumber, String username, EmailDomain emailDomain) {
        return new ArtistContactInfo(Email.createEmail(username, emailDomain), telephoneNumber);
    }
}
