package com.musicdistribution.albumpublishing.xport.rest;

import com.musicdistribution.albumpublishing.domain.models.request.PublishedAlbumRequest;
import com.musicdistribution.albumpublishing.domain.valueobjects.ArtistId;
import com.musicdistribution.albumpublishing.services.PublishedAlbumService;
import com.musicdistribution.sharedkernel.util.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Published Albums Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping("/api/publishedAlbums")
public class PublishedAlbumResource {

    private final PublishedAlbumService publishedAlbumService;

    /**
     * Method for getting all published albums.
     *
     * @return a list of published albums.
     */
    @GetMapping
    public List<PublishedAlbumRequest> getAll() {
        return publishedAlbumService.findAll().stream()
                .map(PublishedAlbumRequest::from).collect(Collectors.toList());
    }

    /**
     * Method for getting all published albums for specific artist.
     *
     * @return a list of published albums.
     */
    @GetMapping("/artist/{artistId}")
    public List<PublishedAlbumRequest> getAllByArtist(@PathVariable String artistId) {
        return publishedAlbumService.findAllByArtist(ArtistId.of(artistId)).stream()
                .map(PublishedAlbumRequest::from).collect(Collectors.toList());
    }
}
