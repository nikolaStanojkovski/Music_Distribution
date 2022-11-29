package com.musicdistribution.streamingservice.xport.rest.core;

import com.musicdistribution.sharedkernel.config.ApiController;
import com.musicdistribution.streamingservice.constant.EntityConstants;
import com.musicdistribution.streamingservice.constant.PathConstants;
import com.musicdistribution.streamingservice.domain.model.entity.core.Artist;
import com.musicdistribution.streamingservice.domain.model.entity.id.ArtistId;
import com.musicdistribution.streamingservice.domain.model.response.core.ArtistResponse;
import com.musicdistribution.sharedkernel.domain.response.SearchResultResponse;
import com.musicdistribution.streamingservice.domain.service.IEncryptionSystem;
import com.musicdistribution.streamingservice.service.ArtistService;
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
@RequestMapping(PathConstants.API_ARTISTS)
public class ArtistResource {

    private final ArtistService artistService;

    private final IEncryptionSystem encryptionSystem;

    /**
     * Method used for fetching a page with artists.
     *
     * @param pageable - the wrapper object containing pagination data.
     * @return the page of the filtered artists.
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
     * Method used for searching artists.
     *
     * @param searchParams - the object parameters by which a filtering is to be done.
     * @param searchTerm   - the search term by which the filtering is to be done.
     * @param pageable     - the wrapper object containing pagination data.
     * @return the page with the filtered artists.
     */
    @GetMapping(PathConstants.SEARCH)
    public Page<ArtistResponse> search(@RequestParam String[] searchParams,
                                       @RequestParam String searchTerm,
                                       Pageable pageable) {
        SearchResultResponse<Artist> artistSearchResultResponse = artistService.search(List.of(searchParams),
                (List.of(searchParams).stream().filter(param ->
                        param.contains(EntityConstants.ID)).count() == searchParams.length)
                        ? encryptionSystem.decrypt(searchTerm) : searchTerm, pageable);
        return new PageImpl<>(artistSearchResultResponse.getResultPage()
                .stream()
                .map(album -> ArtistResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId())))
                .collect(Collectors.toList()), pageable, artistSearchResultResponse.getResultSize());
    }

    /**
     * Method used for fetching information about a specific artist.
     *
     * @param id - artist's ID.
     * @return the found artist.
     */
    @GetMapping(PathConstants.FORMATTED_ID)
    public ResponseEntity<ArtistResponse> findById(@PathVariable String id) {
        return this.artistService.findById(ArtistId.of(encryptionSystem.decrypt(id)))
                .map(artist -> ResponseEntity.ok().body(ArtistResponse.from(artist,
                        encryptionSystem.encrypt(artist.getId().getId()))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
