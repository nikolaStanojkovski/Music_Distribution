package com.musicdistribution.albumpublishing.domain.valueobjects.auxiliary;

import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import com.musicdistribution.sharedkernel.domain.valueobjects.Email;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * Value object that keeps the contact information for the album value object.
 */
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ArtistContactInfo implements ValueObject {

    private Email email;
    private String telephoneNumber;

    public static ArtistContactInfo build(String telephoneNumber, String username, EmailDomain emailDomain) {
        return new ArtistContactInfo(Email.createEmail(username, emailDomain), telephoneNumber);
    }
}
