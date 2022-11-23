package com.musicdistribution.streamingservice.xport.rest.core;

import com.musicdistribution.sharedkernel.util.ApiController;
import com.musicdistribution.streamingservice.constant.PathConstants;
import com.musicdistribution.streamingservice.domain.model.request.ArtistRequest;
import com.musicdistribution.streamingservice.domain.model.response.ArtistJwtResponse;
import com.musicdistribution.streamingservice.domain.model.response.ArtistResponse;
import com.musicdistribution.streamingservice.domain.service.IEncryptionSystem;
import com.musicdistribution.streamingservice.service.ArtistService;
import com.musicdistribution.streamingservice.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * Authentication Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping(PathConstants.API_AUTH)
public class AuthResource {

    private final ArtistService artistService;

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final IEncryptionSystem encryptionSystem;

    /**
     * Method used for authenticating an existing artist.
     *
     * @param artistRequest - an object wrapper containing the login information about an artist.
     * @return the authenticated artist.
     */
    @PostMapping(PathConstants.LOGIN)
    public ResponseEntity<ArtistJwtResponse> login(@RequestBody @Valid ArtistRequest artistRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(artistRequest.getUsername(), artistRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        return this.artistService.loginArtist(artistRequest)
                .map(artist -> ResponseEntity.ok().body(ArtistJwtResponse.from(artist, jwt,
                        encryptionSystem.encrypt(artist.getId().getId()))))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Method used for registering a new artist.
     *
     * @param artistRequest - an object wrapper containing the registration information about the new artist.
     * @return the registered artist.
     */
    @PostMapping(PathConstants.REGISTER)
    public ResponseEntity<ArtistResponse> register(@RequestPart MultipartFile profilePicture,
                                                   @RequestPart @Valid ArtistRequest artistRequest) {
        return this.artistService.registerArtist(profilePicture, artistRequest)
                .map(artist -> ResponseEntity.ok().body(ArtistResponse.from(artist,
                        encryptionSystem.encrypt(artist.getId().getId()))))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
