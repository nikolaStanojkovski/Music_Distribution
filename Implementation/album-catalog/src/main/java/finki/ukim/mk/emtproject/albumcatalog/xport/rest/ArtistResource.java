package finki.ukim.mk.emtproject.albumcatalog.xport.rest;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Artist;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.ArtistId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.dto.ArtistDto;
import finki.ukim.mk.emtproject.albumcatalog.services.ArtistService;
import finki.ukim.mk.emtproject.albumcatalog.services.form.ArtistForm;
import finki.ukim.mk.emtproject.albumcatalog.services.form.ArtistLoginForm;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/artists")
public class ArtistResource {

    private final ArtistService artistService;

    @GetMapping
    public List<ArtistDto> getAll() {
        return artistService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> findById(@PathVariable String id) {
        return this.artistService.findById(ArtistId.of(id))
                .map(artist -> ResponseEntity.ok().body(artist))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Artist> createArtist(@RequestBody ArtistForm artistForm) {
        return this.artistService.createArtist(artistForm)
                .map(artist -> ResponseEntity.ok().body(artist))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }


    @PostMapping("/login")
    public ResponseEntity<ArtistDto> loginArtist(@RequestBody ArtistLoginForm artistLoginForm) {
        ArtistDto artist = this.artistService.loginArtist(artistLoginForm);
        return ResponseEntity.ok(artist);
    }

    @PostMapping("/register")
    public ResponseEntity<ArtistDto> registerArtist(@RequestBody ArtistForm artistForm) {
        ArtistDto artist = this.artistService.registerArtist(artistForm);
        return ResponseEntity.ok(artist);
    }
}
