package com.musicdistribution.sharedkernel.domain.valueobjects;

import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;

/**
 * Value object for a user email.
 */
@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Email implements ValueObject, Serializable {

    private final String domainUsername;

    @Enumerated(value = EnumType.STRING)
    private final EmailDomain domainName;

    /**
     * Protected no-args constructor used for creating an email entity.
     */
    protected Email() {
        this.domainUsername = StringUtils.EMPTY;
        this.domainName = null;
    }

    /**
     * Static method used for creation of an email.
     *
     * @param username   - user's username.
     * @param domainName - user's domain name.
     * @return the created email.
     */
    public static Email from(String username, EmailDomain domainName) {
        return new Email(username, domainName);
    }

    /**
     * Method used to generate a string representation of the email address format.
     *
     * @return the formatted email address.
     */
    public String getFullAddress() {
        return String.format("%s@%s.com", getDomainUsername(), getDomainName());
    }

    /**
     * Method used for comparing whether two emails are identical..
     *
     * @param o - the other object that is compared to {this}.
     * @return a flag representing whether the two objects are identical or not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return domainUsername.equals(email.domainUsername) && domainName == email.domainName;
    }

    /**
     * Method used for generating a hash code for an email.
     *
     * @return the integer value of the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(domainUsername, domainName);
    }
}
