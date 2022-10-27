package com.musicdistribution.albumcatalog.domain.models.request;

import com.musicdistribution.albumcatalog.domain.models.entity.Artist;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
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

    private String telephoneNumber;
    private String firstName;
    private String lastName;
    private String artName;

    @Min(4)
    @NotBlank
    private String password;
}
