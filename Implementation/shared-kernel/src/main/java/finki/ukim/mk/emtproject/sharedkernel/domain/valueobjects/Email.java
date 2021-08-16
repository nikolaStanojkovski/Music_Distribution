package finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.ValueObject;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Currency;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Embeddable
@Getter
public class Email implements ValueObject {

    private final String username;

    @Enumerated(value = EnumType.STRING)
    private final EmailDomain domainName;


    public static Email createEmail(String username, EmailDomain domainName) {
        return new Email(username, domainName);
    }


    protected Email() {
        this.username = "";
        this.domainName = null;
    }

    public Email(@NonNull String username, @NonNull EmailDomain domainName) {
        this.username = username;
        this.domainName = domainName;
    }

    public String getUsername() {
        return username;
    }

    public EmailDomain getDomainName() {
        if(domainName != null)
            return domainName;

        return null;
    }

    public String getFullAddress() {
        return String.format("%s@%s.com", getUsername(), getDomainName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return username.equals(email.username) && domainName == email.domainName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, domainName);
    }
}
