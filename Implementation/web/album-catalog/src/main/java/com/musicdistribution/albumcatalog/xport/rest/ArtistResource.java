package com.musicdistribution.albumcatalog.xport.rest;

import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;
import com.musicdistribution.albumcatalog.domain.models.request.ArtistRequest;
import com.musicdistribution.albumcatalog.domain.models.response.ArtistResponse;
import com.musicdistribution.albumcatalog.services.ArtistService;
import com.musicdistribution.sharedkernel.util.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Artist Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping("/api/resource/artists")
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
     * Method for getting a page of information about all artists.
     *
     * @return the list of all artists.
     */
    @GetMapping("/page")
    public List<ArtistResponse> getAllPage() {
        return artistService.findAllPageable().stream().map(ArtistResponse::from).collect(Collectors.toList());
    }

    /**
     * Method for search artists.
     *
     * @param searchTerm - the search term by which the filtering will be done
     * @return the list of all filtered artists.
     */
    @GetMapping("/search/{searchTerm}")
    public List<ArtistResponse> searchArtists(@PathVariable String searchTerm) {
        return artistService.searchArtists(searchTerm)
                .stream().map(ArtistResponse::from).collect(Collectors.toList());
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
     * Method for getting information about a specific artist.
     *
     * @param artistRequest - dto object containing information about an artist.
     * @return the found artist.
     */
    @PostMapping("/email")
    public ResponseEntity<ArtistResponse> findByEmail(@RequestBody @Valid ArtistRequest artistRequest) {
        return this.artistService.findByEmail(artistRequest)
                .map(artist -> ResponseEntity.ok().body(ArtistResponse.from(artist)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
