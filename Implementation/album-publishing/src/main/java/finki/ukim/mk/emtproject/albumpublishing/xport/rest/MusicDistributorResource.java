package finki.ukim.mk.emtproject.albumpublishing.xport.rest;

import finki.ukim.mk.emtproject.albumpublishing.domain.models.MusicDistributor;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbum;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbumId;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.dto.MusicDistributorDto;
import finki.ukim.mk.emtproject.albumpublishing.services.MusicDistributorService;
import finki.ukim.mk.emtproject.albumpublishing.services.form.AlbumPublishForm;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<PublishedAlbum> publishAlbum(@RequestBody AlbumPublishForm albumPublishForm) {
        return this.musicDistributorService.publishAlbum(albumPublishForm)
                .map(album -> ResponseEntity.ok().body(album))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/unpublish")
    public ResponseEntity<PublishedAlbum> unpublishAlbum(@RequestBody PublishedAlbumId publishedAlbumId) {
        return this.musicDistributorService.unpublishAlbum(publishedAlbumId)
                .map(album -> ResponseEntity.ok().body(album))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
