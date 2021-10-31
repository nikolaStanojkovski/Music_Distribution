package finki.ukim.mk.emtproject.albumcatalog.xport.rest;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.AlbumId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.request.AlbumRequest;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.response.AlbumResponse;
import finki.ukim.mk.emtproject.albumcatalog.services.AlbumService;
import finki.ukim.mk.emtproject.sharedkernel.util.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AlbumResponse> createAlbum(@RequestBody AlbumRequest albumRequest) {
        return this.albumService.createAlbum(albumRequest)
                .map(album -> ResponseEntity.ok().body(AlbumResponse.from(album)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
