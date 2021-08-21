package finki.ukim.mk.emtproject.albumcatalog.xport.rest;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Album;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.AlbumId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.Song;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.SongId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.dto.SongDto;
import finki.ukim.mk.emtproject.albumcatalog.services.AlbumService;
import finki.ukim.mk.emtproject.albumcatalog.services.SongService;
import finki.ukim.mk.emtproject.albumcatalog.services.form.AlbumForm;
import finki.ukim.mk.emtproject.albumcatalog.services.form.SongForm;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SongResource - Rest Controller for the song methods that communicate with the front-end app
 */
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/songs")
public class SongResource {

    private final SongService songService;

    @GetMapping
    public List<SongDto> getAll() {
        return songService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> findById(@PathVariable String id) {
        return this.songService.findById(SongId.of(id))
                .map(album -> ResponseEntity.ok().body(album))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Song> createSong(@RequestBody SongForm songForm) {
        return this.songService.createSong(songForm)
                .map(song -> ResponseEntity.ok().body(song))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
