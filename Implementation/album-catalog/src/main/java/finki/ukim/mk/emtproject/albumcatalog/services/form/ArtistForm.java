package finki.ukim.mk.emtproject.albumcatalog.services.form;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Album;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.Song;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.ArtistContactInfo;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.ArtistPersonalInfo;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Email;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import lombok.Data;

import java.util.List;

@Data
public class ArtistForm {

    // Artist contact info

    private String username;

    private EmailDomain emailDomain;

    private String telephoneNumber;

    // Artist personal info

    private String firstName;

    private String lastName;

    private String artName;


    private String password;

    private ArtistForm() {

    }

    public static ArtistForm build(String username, EmailDomain emailDomain, String telephoneNumber, String firstName, String lastName, String artName, String password) {
        ArtistForm artistForm = new ArtistForm();

        artistForm.username = username;
        artistForm.emailDomain = emailDomain;
        artistForm.telephoneNumber = telephoneNumber;
        artistForm.firstName = firstName;
        artistForm.lastName = lastName;
        artistForm.artName = artName;
        artistForm.password = password;

        return artistForm;
    }
}
