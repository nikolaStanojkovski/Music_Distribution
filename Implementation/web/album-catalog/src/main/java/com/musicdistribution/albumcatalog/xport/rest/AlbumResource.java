package com.musicdistribution.albumcatalog.xport.rest;

import com.musicdistribution.albumcatalog.domain.models.entity.AlbumId;
import com.musicdistribution.albumcatalog.domain.models.request.AlbumRequest;
import com.musicdistribution.albumcatalog.domain.models.response.AlbumResponse;
import com.musicdistribution.albumcatalog.services.AlbumService;
import com.musicdistribution.sharedkernel.util.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Album Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping("/api/albums")
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
     * Method for getting a page of information about all albums.
     *
     * @return the list of all albums.
     */
    @GetMapping("/page")
    public List<AlbumResponse> getAllPage() {
        return albumService.findAllPageable().stream().map(AlbumResponse::from).collect(Collectors.toList());
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
