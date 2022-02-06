package com.musicdistribution.albumcatalog.xport.rest;

import com.musicdistribution.albumcatalog.domain.models.entity.AlbumId;
import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;
import com.musicdistribution.albumcatalog.domain.models.entity.SongId;
import com.musicdistribution.albumcatalog.domain.models.request.SongRequest;
import com.musicdistribution.albumcatalog.domain.models.response.SongResponse;
import com.musicdistribution.albumcatalog.services.SongService;
import com.musicdistribution.sharedkernel.util.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
     * Method for getting information about all songs by a particular artist.
     *
     * @return the list of all albums.
     */
    @GetMapping("/artist/{artistId}")
    public List<SongResponse> getAllByArtist(@PathVariable String artistId) {
        return songService.findAllByArtist(ArtistId.of(artistId))
                .stream().map(SongResponse::from).collect(Collectors.toList());
    }

    /**
     * Method for getting information about all songs by a particular artist.
     *
     * @return the list of all albums.
     */
    @GetMapping("/album/{albumId}")
    public List<SongResponse> getAllByAlbum(@PathVariable String albumId) {
        return songService.findAllByAlbum(AlbumId.of(albumId))
                .stream().map(SongResponse::from).collect(Collectors.toList());
    }

    /**
     * Method for getting a page of information about all songs.
     *
     * @return the list of all songs.
     */
    @GetMapping("/page")
    public List<SongResponse> getAllPage() {
        return songService.findAllPageable().stream().map(SongResponse::from).collect(Collectors.toList());
    }

    /**
     * Method for search songs.
     *
     * @param searchTerm - the search term by which the filtering will be done
     * @return the list of all filtered songs.
     */
    @GetMapping("/search/{searchTerm}")
    public List<SongResponse> searchSongs(@PathVariable String searchTerm) {
        return songService.searchSongs(searchTerm)
                .stream().map(SongResponse::from).collect(Collectors.toList());
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
    public ResponseEntity<SongResponse> createSong(@RequestBody @Valid SongRequest songRequest) {
        return this.songService.createSong(songRequest)
                .map(song -> ResponseEntity.ok().body(SongResponse.from(song)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
