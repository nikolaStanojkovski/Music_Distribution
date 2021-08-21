package finki.ukim.mk.emtproject.albumcatalog.xport.rest;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Album;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.AlbumId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.dto.AlbumDto;
import finki.ukim.mk.emtproject.albumcatalog.services.AlbumService;
import finki.ukim.mk.emtproject.albumcatalog.services.form.AlbumForm;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AlbumResource - Rest Controller for the album methods that communicate with the front-end app
 */
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/albums")
public class AlbumResource {

    private final AlbumService albumService;

    @GetMapping
    public List<AlbumDto> getAll() {
        return albumService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> findById(@PathVariable String id) {
        return this.albumService.findById(AlbumId.of(id))
                .map(album -> ResponseEntity.ok().body(album))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Album> createAlbum(@RequestBody AlbumForm albumForm) {
        return this.albumService.createAlbum(albumForm)
                .map(album -> ResponseEntity.ok().body(album))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
