package com.musicdistribution.albumcatalog.xport.rest;

import com.musicdistribution.albumcatalog.domain.models.entity.Album;
import com.musicdistribution.albumcatalog.domain.models.entity.AlbumId;
import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;
import com.musicdistribution.albumcatalog.domain.models.request.AlbumRequest;
import com.musicdistribution.albumcatalog.domain.models.response.AlbumResponse;
import com.musicdistribution.albumcatalog.services.AlbumService;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import com.musicdistribution.sharedkernel.util.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Album Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping("/api/resource/albums")
public class AlbumResource {

    private final AlbumService albumService;

    /**
     * Method for getting information about all albums.
     *
     * @return the list of all albums.
     */
    @GetMapping
    public List<AlbumResponse> getAll() {
        return albumService.findAll().stream().map(AlbumResponse::from).collect(Collectors.toList());
    }

    /**
     * Method for getting a page of information about published albums.
     *
     * @return the list of all albums.
     */
    @GetMapping("/page")
    public List<AlbumResponse> getAllPage() {
        return albumService.findAllPageable().stream().filter(Album::getIsPublished).map(AlbumResponse::from).collect(Collectors.toList());
    }

    /**
     * Method for getting information about all albums by a particular artist.
     *
     * @param artistId - artist's id
     * @return the list of all filtered albums.
     */
    @GetMapping("/artist/{artistId}")
    public List<AlbumResponse> getAllByArtist(@PathVariable String artistId) {
        return albumService.findAllByArtist(ArtistId.of(artistId))
                .stream().map(AlbumResponse::from).collect(Collectors.toList());
    }

    /**
     * Method for getting information about all albums filtered by a particular genre.
     *
     * @param genre - the genre by which the filtering will be done
     * @return the list of all filtered albums.
     */
    @GetMapping("/genre/{genre}")
    public List<AlbumResponse> getAllByGenre(@PathVariable String genre) {
        return albumService.findAllByGenre(Genre.valueOf(genre))
                .stream().map(AlbumResponse::from).collect(Collectors.toList());
    }

    /**
     * Method for search albums.
     *
     * @param searchTerm - the search term by which the filtering will be done
     * @return the list of all filtered albums.
     */
    @GetMapping("/search/{searchTerm}")
    public List<AlbumResponse> searchAlbums(@PathVariable String searchTerm) {
        return albumService.searchAlbums(searchTerm)
                .stream().map(AlbumResponse::from).collect(Collectors.toList());
    }

    /**
     * Method for getting information about a specific album.
     *
     * @param id - album's id.
     * @return the found album.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponse> findById(@PathVariable String id) {
        return this.albumService.findById(AlbumId.of(id))
                .map(album -> ResponseEntity.ok().body(AlbumResponse.from(album)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Method for creating a new album.
     *
     * @param albumRequest - dto object containing information for the album to be created.
     * @return the created album.
     */
    @PostMapping("/create")
    public ResponseEntity<AlbumResponse> createAlbum(@RequestBody @Valid AlbumRequest albumRequest) {
        return this.albumService.createAlbum(albumRequest)
                .map(album -> ResponseEntity.ok().body(AlbumResponse.from(album)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
