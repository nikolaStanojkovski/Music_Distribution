package finki.ukim.mk.emtproject.albumcatalog.domain.models.request;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Artist;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * An artist object used to transfer data from the front-end user form to the backend.
 */
@Data
@NoArgsConstructor
public class ArtistRequest {

    @NotBlank
    private String username;
    @NotNull
    private EmailDomain emailDomain;
    @NotBlank
    private String telephoneNumber;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String artName;

    @Min(4)
    @NotBlank
    private String password;

    /**
     * Static method used for creation of a new artist form object.
     *
     * @return the new artist form object.
     */
    public static ArtistRequest build(Artist artist) {
        ArtistRequest artistRequest = new ArtistRequest();
        artistRequest.setUsername(artist.getArtistContactInfo().getEmail().getUsername());
        artistRequest.setEmailDomain(artist.getArtistContactInfo().getEmail().getDomainName());
        artistRequest.setTelephoneNumber(artist.getArtistContactInfo().getTelephoneNumber());
        artistRequest.setFirstName(artist.getArtistPersonalInfo().getFirstName());
        artistRequest.setLastName(artist.getArtistPersonalInfo().getLastName());
        artistRequest.setArtName(artist.getArtistPersonalInfo().getArtName());
        artistRequest.setPassword(artist.getPassword());

        return artistRequest;
    }
}
