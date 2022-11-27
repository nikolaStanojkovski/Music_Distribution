package com.musicdistribution.streamingservice.xport.rest.core;

import com.musicdistribution.sharedkernel.util.ApiController;
import com.musicdistribution.streamingservice.constant.AuthConstants;
import com.musicdistribution.streamingservice.constant.PathConstants;
import com.musicdistribution.streamingservice.domain.model.request.ArtistRequest;
import com.musicdistribution.streamingservice.domain.model.request.AuthRequest;
import com.musicdistribution.streamingservice.domain.model.response.ArtistJwtResponse;
import com.musicdistribution.streamingservice.domain.model.response.ArtistResponse;
import com.musicdistribution.streamingservice.domain.service.IEncryptionSystem;
import com.musicdistribution.streamingservice.service.ArtistService;
import com.musicdistribution.streamingservice.util.AuthUtil;
import com.musicdistribution.streamingservice.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
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
     * Method used for authenticating an existing user.
     *
     * @param authRole    - the authentication role of the user.
     * @param authRequest - an object wrapper containing the login information about a user.
     * @return the authenticated user.
     */
    @PostMapping(PathConstants.LOGIN)
    public ResponseEntity<ArtistJwtResponse> login(@RequestHeader(value = AuthConstants.AUTH_ROLE) String authRole,
                                                   @RequestBody @Valid AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        AuthUtil.formatUsernamePrincipal(authRequest.getUsername(), authRole),
                        authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        return this.artistService.loginArtist(authRequest)
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
    @PostMapping(PathConstants.REGISTER_ARTIST)
    public ResponseEntity<ArtistResponse> registerArtist(@RequestPart MultipartFile profilePicture,
                                                         @RequestPart @Valid ArtistRequest artistRequest) {
        return this.artistService.registerArtist(profilePicture, artistRequest)
                .map(artist -> ResponseEntity.ok().body(ArtistResponse.from(artist,
                        encryptionSystem.encrypt(artist.getId().getId()))))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
