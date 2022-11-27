package com.musicdistribution.streamingservice.domain.model.request;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * A user request wrapper object used to transfer authentication form
 * data from the front-end to the backend.
 */
@Data
@NoArgsConstructor
public class AuthRequest {

    @NotBlank
    private String username;
    @NotNull
    private EmailDomain emailDomain;
    @Min(4)
    @NotBlank
    private String password;
}
