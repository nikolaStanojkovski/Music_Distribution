package com.musicdistribution.storageservice.xport.rest.core;

import com.musicdistribution.sharedkernel.util.ApiController;
import com.musicdistribution.storageservice.domain.model.SearchResult;
import com.musicdistribution.storageservice.domain.model.entity.Artist;
import com.musicdistribution.storageservice.domain.model.entity.ArtistId;
import com.musicdistribution.storageservice.domain.model.response.ArtistResponse;
import com.musicdistribution.storageservice.domain.service.IEncryptionSystem;
import com.musicdistribution.storageservice.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    public Page<ArtistResponse> findAll(Pageable pageable) {
        List<ArtistResponse> artists = artistService.findAll(pageable).stream()
                .map(artist -> ArtistResponse.from(artist,
                        encryptionSystem.encrypt(artist.getId().getId())))
                .collect(Collectors.toList());
        return new PageImpl<>(artists, pageable, artistService.findTotalSize());
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
    public Page<ArtistResponse> search(@RequestParam String[] searchParams,
                                       @RequestParam String searchTerm,
                                       Pageable pageable) {
        SearchResult<Artist> artistSearchResult = artistService.search(List.of(searchParams),
                (List.of(searchParams).stream().filter(param ->
                        param.contains("id")).count() == searchParams.length)
                        ? encryptionSystem.decrypt(searchTerm) : searchTerm, pageable);
        return new PageImpl<>(artistSearchResult.getResultPage()
                .stream()
                .map(album -> ArtistResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId())))
                .collect(Collectors.toList()), pageable, artistSearchResult.getResultSize());
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
