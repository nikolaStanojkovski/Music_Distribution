package com.musicdistribution.albumcatalog.xport.rest.core;

import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;
import com.musicdistribution.albumcatalog.domain.models.response.ArtistResponse;
import com.musicdistribution.albumcatalog.domain.services.IEncryptionSystem;
import com.musicdistribution.albumcatalog.services.ArtistService;
import com.musicdistribution.sharedkernel.util.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private final IEncryptionSystem encryptionSystem;

    /**
     * Method for getting information about all artists.
     *
     * @param pageable - the wrapper for paging/sorting/filtering
     * @return the list of all artists.
     */
    @GetMapping
    public List<ArtistResponse> findAll(Pageable pageable) {
        return artistService.findAll(pageable).stream()
                .map(artist -> ArtistResponse.from(artist,
                        encryptionSystem.encrypt(artist.getId().getId())))
                .collect(Collectors.toList());
    }

    /**
     * Method for searching albums.
     *
     * @param searchParams - the object parameters by which a filtering will be done
     * @param searchTerm   - the search term by which the filtering will be done
     * @param pageable     - the wrapper for paging/sorting/filtering
     * @return the page of the filtered albums.
     */
    @GetMapping("/search")
    public List<ArtistResponse> search(@RequestParam String[] searchParams,
                                       @RequestParam String searchTerm,
                                       Pageable pageable) {
        return artistService.search(List.of(searchParams), searchTerm, pageable).stream()
                .map(album -> ArtistResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId())))
                .collect(Collectors.toList());
    }

    /**
     * Method for getting information about a specific artist.
     *
     * @param id - artist's id.
     * @return the found artist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> findById(@PathVariable String id) {
        return this.artistService.findById(ArtistId.of(encryptionSystem.decrypt(id)))
                .map(artist -> ResponseEntity.ok().body(ArtistResponse.from(artist,
                        encryptionSystem.encrypt(artist.getId().getId()))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
