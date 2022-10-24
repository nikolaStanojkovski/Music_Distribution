package com.musicdistribution.albumcatalog.xport.rest;

import com.musicdistribution.albumcatalog.domain.models.entity.AlbumId;
import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;
import com.musicdistribution.albumcatalog.domain.models.entity.SongId;
import com.musicdistribution.albumcatalog.domain.models.request.SongRequest;
import com.musicdistribution.albumcatalog.domain.models.response.SongResponse;
import com.musicdistribution.albumcatalog.domain.services.IEncryptionSystem;
import com.musicdistribution.albumcatalog.domain.services.IFileSystemStorage;
import com.musicdistribution.albumcatalog.security.jwt.JwtUtils;
import com.musicdistribution.albumcatalog.services.SongService;
import com.musicdistribution.sharedkernel.util.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Song Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping("/api/resource/songs")
public class SongResource {

    private final JwtUtils jwtUtils;
    private final SongService songService;
    private final IFileSystemStorage systemStorage;
    private final IEncryptionSystem encryptionSystem;

    /**
     * Method for getting information about all songs.
     *
     * @return the list of all songs.
     */
    @GetMapping
    public List<SongResponse> getAll() {
        return songService.findAll().stream()
                .map(song -> SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        encryptionSystem.encrypt(Optional.ofNullable(
                                song.getAlbum()).map(album -> album.getId().getId())
                                .orElse(""))))
                .collect(Collectors.toList());
    }

    /**
     * Method for getting information about all songs by a particular artist.
     *
     * @return the list of all albums.
     */
    @GetMapping("/artist/{artistId}")
    public List<SongResponse> getAllByArtist(@PathVariable String artistId) {
        return songService.findAllByArtist(ArtistId.of(encryptionSystem.decrypt(artistId)))
                .stream().map(song -> SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        encryptionSystem.encrypt(Optional.ofNullable(
                                song.getAlbum()).map(album -> album.getId().getId())
                                .orElse(""))))
                .collect(Collectors.toList());
    }

    /**
     * Method for getting information about all songs by a particular artist.
     *
     * @return the list of all albums.
     */
    @GetMapping("/album/{albumId}")
    public List<SongResponse> getAllByAlbum(@PathVariable String albumId) {
        return songService.findAllByAlbum(AlbumId.of(encryptionSystem.decrypt(albumId)))
                .stream().map(song -> SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        encryptionSystem.encrypt(Optional.ofNullable(
                                song.getAlbum()).map(album -> album.getId().getId())
                                .orElse(""))))
                .collect(Collectors.toList());
    }

    /**
     * Method for getting a page of information about all songs.
     *
     * @return the list of all songs.
     */
    @GetMapping("/page")
    public List<SongResponse> getAllPage() {
        return songService.findAllPageable().stream()
                .map(song -> SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        encryptionSystem.encrypt(Optional.ofNullable(
                                song.getAlbum()).map(album -> album.getId().getId())
                                .orElse(""))))
                .collect(Collectors.toList());
    }

    /**
     * Method for search songs.
     *
     * @param searchTerm - the search term by which the filtering will be done
     * @return the list of all filtered songs.
     */
    @GetMapping("/search/{searchTerm}")
    public List<SongResponse> searchSongs(@PathVariable String searchTerm) {
        return songService.searchSongs(searchTerm).stream()
                .map(song -> SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        encryptionSystem.encrypt(Optional.ofNullable(
                                song.getAlbum()).map(album -> album.getId().getId())
                                .orElse(""))))
                .collect(Collectors.toList());
    }


    /**
     * Method for getting information about a specific song.
     *
     * @param id - song's id.
     * @return the found song.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SongResponse> findById(@PathVariable String id) {
        return this.songService.findById(SongId.of(encryptionSystem.decrypt(id)))
                .map(song -> ResponseEntity.ok().body(SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        encryptionSystem.encrypt(Optional.ofNullable(
                                song.getAlbum()).map(album -> album.getId().getId())
                                .orElse("")))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/file/{id}", produces = "audio/mpeg")
    public ResponseEntity<?> findFileById(@PathVariable String id) {
        return this.songService.findById(SongId.of(encryptionSystem.decrypt(id)))
                .map(song -> {
                    String fileName = String.format("%s.mp3", song.getId().getId());
                    byte[] resource = systemStorage.loadFile(fileName);

                    String contentType = "audio/mpeg";
                    String headerValue = "attachment; filename=\"" + song.getSongName() + ".mp3\"";

                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(contentType))
                            .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                            .body(resource);
                }).orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Method for creating a new song.
     *
     * @param songRequest - dto object containing information for the song to be created.
     * @return the created song.
     */
    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<SongResponse> createSong(@RequestHeader(value = "Authorization") String authToken, @RequestPart MultipartFile file, @RequestPart @Valid SongRequest songRequest) {
        String username = jwtUtils.getUserNameFromJwtToken(authToken.replace("Bearer ", ""));
        return this.songService.createSong(songRequest, file, username)
                .map(song -> ResponseEntity.ok().body(SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        encryptionSystem.encrypt(Optional.ofNullable(
                                song.getAlbum().getId().getId()).orElse("")))))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Method for publishing a song.
     *
     * @param songRequest - dto object for the song to be published.
     * @return the published song.
     */
    @PostMapping("/publish")
    public ResponseEntity<SongResponse> publishSong(@RequestBody @Valid SongRequest songRequest) {
        return this.songService.publishSong(songRequest)
                .map(song -> ResponseEntity.ok().body(SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        encryptionSystem.encrypt(Optional.ofNullable(
                                song.getAlbum().getId().getId()).orElse("")))))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Method for unpublishing a song.
     *
     * @param id - the id of the song to be unpublished.
     * @return the unpublished song.
     */
    @GetMapping("/unpublish/{id}")
    public ResponseEntity<SongResponse> unpublishSong(@PathVariable String id) {
        return this.songService.deleteSong(SongId.of(encryptionSystem.decrypt(id)))
                .map(song -> ResponseEntity.ok().body(SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        encryptionSystem.encrypt(Optional.ofNullable(
                                song.getAlbum().getId().getId()).orElse("")))))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
