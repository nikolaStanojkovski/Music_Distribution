package com.musicdistribution.streamingservice.domain.valueobject;

import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import com.musicdistribution.sharedkernel.domain.valueobjects.Email;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * A value object that contains artist's contact information.
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserContactInfo implements ValueObject, Serializable {

    private Email email;
    private String telephoneNumber;

    /**
     * Method used for creating a new artist contact information object.
     *
     * @param telephoneNumber - artist's telephone number.
     * @param username        - artist's username.
     * @param emailDomain     - artist's email domain.
     * @return the created contact information object for an artist.
     */
    public static UserContactInfo from(String telephoneNumber, String username, EmailDomain emailDomain) {
        return new UserContactInfo(Email.from(username, emailDomain), telephoneNumber);
    }
}
