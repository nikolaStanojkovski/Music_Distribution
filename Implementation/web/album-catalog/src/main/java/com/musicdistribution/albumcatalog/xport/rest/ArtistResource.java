package com.musicdistribution.albumcatalog.xport.rest;

import com.musicdistribution.albumcatalog.domain.models.request.ArtistRequest;
import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;
import com.musicdistribution.albumcatalog.domain.models.response.ArtistResponse;
import com.musicdistribution.albumcatalog.services.ArtistService;
import com.musicdistribution.sharedkernel.util.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Artist Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping("/api/artists")
public class ArtistResource {

    private final ArtistService artistService;

    /**
     * Method for getting information about all artists.
     *
     * @return the list of all artists.
     */
    @GetMapping
    public List<ArtistResponse> getAll() {
        return artistService.findAll().stream().map(ArtistResponse::from).collect(Collectors.toList());
    }

    /**
     * Method for getting information about a specific artist.
     *
     * @param id - artist's id.
     * @return the found artist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> findById(@PathVariable String id) {
        return this.artistService.findById(ArtistId.of(id))
                .map(artist -> ResponseEntity.ok().body(ArtistResponse.from(artist)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Method for authenticating an existing artist.
     *
     * @param artistRequest - dto object containing the login information about an artist.
     * @return the authenticated artist.
     */
    @PostMapping("/login")
    public ResponseEntity<ArtistResponse> loginArtist(@RequestBody @Valid ArtistRequest artistRequest) {
        return this.artistService.loginArtist(artistRequest)
                .map(artist -> ResponseEntity.ok().body(ArtistResponse.from(artist)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Method for registering an existing artist.
     *
     * @param artistRequest - dto object containing the login information about an artist.
     * @return the registered artist.
     */
    @PostMapping("/register")
    public ResponseEntity<ArtistResponse> registerArtist(@RequestBody @Valid ArtistRequest artistRequest) {
        return this.artistService.registerArtist(artistRequest)
                .map(artist -> ResponseEntity.ok().body(ArtistResponse.from(artist)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
