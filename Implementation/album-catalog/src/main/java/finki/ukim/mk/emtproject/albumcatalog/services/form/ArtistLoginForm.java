package finki.ukim.mk.emtproject.albumcatalog.services.form;

import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Email;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class ArtistLoginForm {

    private String username;

    private EmailDomain domainName;

    private String password;

}
