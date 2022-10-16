package com.musicdistribution.sharedkernel.domain.valueobjects;

import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

/**
 * Value object for a artist email that contains the domain name and username.
 */
@Getter
@Embeddable
@AllArgsConstructor
public class Email implements ValueObject {

    private final String domainUsername;

    @Enumerated(value = EnumType.STRING)
    private final EmailDomain domainName;

    /**
     * Protected no-args constructor for the email class.
     */
    protected Email() {
        this.domainUsername = "";
        this.domainName = null;
    }

    /**
     * Static method for creation of an email.
     *
     * @param username   - artist's username.
     * @param domainName - artist's domain name.
     * @return the created email.
     */
    public static Email createEmail(String username, EmailDomain domainName) {
        return new Email(username, domainName);
    }

    public String getFullAddress() {
        return String.format("%s@%s.com", getDomainUsername(), getDomainName());
    }

    /**
     * Method used for comparing two objects of type email.
     *
     * @param o - the other object that is compared to this.
     * @return a flag whether the two objects are equal or not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return domainUsername.equals(email.domainUsername) && domainName == email.domainName;
    }

    /**
     * Method used for generating the hash code for an email object.
     *
     * @return the integer value of the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(domainUsername, domainName);
    }
}
