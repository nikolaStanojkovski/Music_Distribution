package com.musicdistribution.streamingservice.application.web;

import com.musicdistribution.sharedkernel.infrastructure.ApiController;
import com.musicdistribution.streamingservice.constant.AuthConstants;
import com.musicdistribution.streamingservice.constant.PathConstants;
import com.musicdistribution.streamingservice.domain.model.request.AuthRequest;
import com.musicdistribution.streamingservice.domain.model.request.core.ArtistRequest;
import com.musicdistribution.streamingservice.domain.model.response.auth.ArtistJwtResponse;
import com.musicdistribution.streamingservice.domain.model.response.auth.JwtResponse;
import com.musicdistribution.streamingservice.domain.model.response.auth.ListenerJwtResponse;
import com.musicdistribution.streamingservice.domain.model.response.core.ArtistResponse;
import com.musicdistribution.streamingservice.domain.model.response.core.ListenerResponse;
import com.musicdistribution.streamingservice.domain.service.IEncryptionSystem;
import com.musicdistribution.streamingservice.application.service.ArtistService;
import com.musicdistribution.streamingservice.application.service.ListenerService;
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
import java.util.List;

/**
 * Authentication Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping(PathConstants.API_AUTH)
public class AuthResource {

    private final ArtistService artistService;
    private final ListenerService listenerService;

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
    public ResponseEntity<? extends JwtResponse> login(@RequestHeader(value = AuthConstants.AUTH_ROLE) String authRole,
                                                       @RequestBody @Valid AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        AuthUtil.formatUsernamePrincipal(authRequest.getUsername(), authRole),
                        authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        switch (AuthUtil.parseAuthRole(authRole)) {
            case ARTIST:
                return buildArtistResponse(authRequest, jwt);
            case LISTENER:
                return buildListenerResponse(authRequest, jwt);
            default:
                return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Method used for building an artist authentication response.
     *
     * @param authRequest - an object wrapper containing the login information about an artist.
     * @param jwtToken    - the JWT token to be sent out as a successful authentication response.
     * @return the authenticated artist.
     */
    private ResponseEntity<ArtistJwtResponse> buildArtistResponse(AuthRequest authRequest, String jwtToken) {
        return this.artistService.login(authRequest)
                .map(artist -> ResponseEntity.ok().body(ArtistJwtResponse.from(artist, jwtToken,
                        encryptionSystem.encrypt(artist.getId().getId()))))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Method used for building a listener authentication response.
     *
     * @param authRequest - an object wrapper containing the login information about a listener.
     * @param jwtToken    - the JWT token to be sent out as a successful authentication response.
     * @return the authenticated listener.
     */
    private ResponseEntity<ListenerJwtResponse> buildListenerResponse(AuthRequest authRequest, String jwtToken) {
        return this.listenerService.login(authRequest)
                .map(listener -> ResponseEntity.ok().body(ListenerJwtResponse.from(listener, jwtToken,
                        encryptionSystem.encrypt(listener.getId().getId()))))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Method used for registering a new artist.
     *
     * @param artistRequest - an object wrapper containing the registration information about a new artist.
     * @return the registered artist.
     */
    @PostMapping(PathConstants.REGISTER_ARTIST)
    public ResponseEntity<ArtistResponse> registerArtist(@RequestPart MultipartFile profilePicture,
                                                         @RequestPart @Valid ArtistRequest artistRequest) {
        return this.artistService.register(profilePicture, artistRequest)
                .map(artist -> ResponseEntity.ok().body(ArtistResponse.from(artist,
                        encryptionSystem.encrypt(artist.getId().getId()))))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Method used for registering a new listener.
     *
     * @param listenerRequest - an object wrapper containing the registration information about the new listener.
     * @return the registered listener.
     */
    @PostMapping(PathConstants.REGISTER_LISTENER)
    public ResponseEntity<ListenerResponse> registerListener(@RequestBody @Valid AuthRequest listenerRequest) {
        return this.listenerService.register(listenerRequest)
                .map(listener -> ResponseEntity.ok().body(ListenerResponse.from(listener,
                        encryptionSystem.encrypt(listener.getId().getId()), List.of(), List.of(), List.of())))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
