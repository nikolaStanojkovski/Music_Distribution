package com.musicdistribution.streamingservice.domain.model.request.core;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * An extended artist request wrapper object used to transfer
 * form data from the front-end to the back-end.
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
