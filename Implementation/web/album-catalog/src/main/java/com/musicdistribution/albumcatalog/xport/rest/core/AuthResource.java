package com.musicdistribution.albumcatalog.xport.rest.core;

import com.musicdistribution.albumcatalog.domain.models.request.ArtistRequest;
import com.musicdistribution.albumcatalog.domain.models.response.ArtistJwtResponse;
import com.musicdistribution.albumcatalog.domain.models.response.ArtistResponse;
import com.musicdistribution.albumcatalog.domain.services.IEncryptionSystem;
import com.musicdistribution.albumcatalog.security.jwt.JwtUtils;
import com.musicdistribution.albumcatalog.services.ArtistService;
import com.musicdistribution.sharedkernel.util.ApiController;
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
@RequestMapping("/api/auth")
public class AuthResource {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ArtistService artistService;
    private final IEncryptionSystem encryptionSystem;

    /**
     * Method for authenticating an existing artist.
     *
     * @param artistRequest - dto object containing the login information about an artist.
     * @return the authenticated artist.
     */
    @PostMapping("/login")
    public ResponseEntity<ArtistJwtResponse> login(@RequestBody @Valid ArtistRequest artistRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(artistRequest.getUsername(), artistRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return this.artistService.loginArtist(artistRequest)
                .map(artist -> ResponseEntity.ok().body(ArtistJwtResponse.from(artist, jwt,
                        encryptionSystem.encrypt(artist.getId().getId()))))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Method for registering an existing artist.
     *
     * @param artistRequest - dto object containing the login information about an artist.
     * @return the registered artist.
     */
    @PostMapping("/register")
    public ResponseEntity<ArtistResponse> register(@RequestPart MultipartFile profilePicture,
                                                   @RequestPart @Valid ArtistRequest artistRequest) {
        return this.artistService.registerArtist(profilePicture, artistRequest)
                .map(artist -> ResponseEntity.ok().body(ArtistResponse.from(artist,
                        encryptionSystem.encrypt(artist.getId().getId()))))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
