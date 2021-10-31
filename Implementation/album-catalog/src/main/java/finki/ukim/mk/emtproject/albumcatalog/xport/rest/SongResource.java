package finki.ukim.mk.emtproject.albumcatalog.xport.rest;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.SongId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.request.SongRequest;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.response.SongResponse;
import finki.ukim.mk.emtproject.albumcatalog.services.SongService;
import finki.ukim.mk.emtproject.sharedkernel.util.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Song Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping("/api/songs")
public class SongResource {

    private final SongService songService;

    /**
     * Method for getting information about all songs.
     *
     * @return the list of all songs.
     */
    @GetMapping
    public List<SongResponse> getAll() {
        return songService.findAll().stream().map(SongResponse::from).collect(Collectors.toList());
    }

    /**
     * Method for getting information about a specific song.
     *
     * @param id - song's id.
     * @return the found song.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SongResponse> findById(@PathVariable String id) {
        return this.songService.findById(SongId.of(id))
                .map(song -> ResponseEntity.ok().body(SongResponse.from(song)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Method for creating a new song.
     *
     * @param songRequest - dto object containing information for the song to be created.
     * @return the created song.
     */
    @PostMapping("/create")
    public ResponseEntity<SongResponse> createSong(@RequestBody SongRequest songRequest) {
        return this.songService.createSong(songRequest)
                .map(song -> ResponseEntity.ok().body(SongResponse.from(song)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}