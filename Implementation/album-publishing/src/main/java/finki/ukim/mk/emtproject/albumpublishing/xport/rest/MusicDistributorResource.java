package finki.ukim.mk.emtproject.albumpublishing.xport.rest;

import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbum;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbumId;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.dto.MusicDistributorDto;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.dto.PublishedAlbumDto;
import finki.ukim.mk.emtproject.albumpublishing.services.MusicDistributorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MusicDistributorResource - Rest Controller for the music distributor methods that communicate with the front-end app
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/distributors")
@AllArgsConstructor
public class MusicDistributorResource {

    private final MusicDistributorService musicDistributorService;

    @GetMapping
    public List<MusicDistributorDto> getAll() {
        return musicDistributorService.findAll();
    }

    @PostMapping("/publish")
    public ResponseEntity<PublishedAlbum> publishAlbum(@RequestBody PublishedAlbumDto publishedAlbumDto) {
        return this.musicDistributorService.publishAlbum(publishedAlbumDto)
                .map(album -> ResponseEntity.ok().body(album))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/unpublish")
    public ResponseEntity<PublishedAlbum> unpublishAlbum(@RequestBody PublishedAlbumId publishedAlbumId) {
        return this.musicDistributorService.unpublishAlbum(publishedAlbumId)
                .map(album -> ResponseEntity.ok().body(album))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/raiseAlbumTier")
    public ResponseEntity<PublishedAlbum> raiseAlbumTier(@RequestBody PublishedAlbumDto publishedAlbumDto) {
        return this.musicDistributorService.raiseAlbumTier(publishedAlbumDto)
                .map(album -> ResponseEntity.ok().body(album))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
