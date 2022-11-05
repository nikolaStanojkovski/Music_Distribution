package com.musicdistribution.albumcatalog.xport.rest.core;

import com.musicdistribution.albumcatalog.domain.models.entity.AlbumId;
import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;
import com.musicdistribution.albumcatalog.domain.models.request.AlbumShortTransactionRequest;
import com.musicdistribution.albumcatalog.domain.models.request.AlbumTransactionRequest;
import com.musicdistribution.albumcatalog.domain.models.response.AlbumResponse;
import com.musicdistribution.albumcatalog.domain.services.IEncryptionSystem;
import com.musicdistribution.albumcatalog.security.jwt.JwtUtils;
import com.musicdistribution.albumcatalog.services.AlbumService;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import com.musicdistribution.sharedkernel.util.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Album Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping("/api/resource/albums")
public class AlbumResource {

    private final JwtUtils jwtUtils;
    private final AlbumService albumService;
    private final IEncryptionSystem encryptionSystem;

    /**
     * Method for getting information about all albums.
     *
     * @return the list of all albums.
     */
    @GetMapping
    public List<AlbumResponse> getAll() {
        return albumService.findAll().stream()
                .map(album -> AlbumResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId()),
                        encryptionSystem.encrypt(album.getCreator().getId().getId())))
                .collect(Collectors.toList());
    }

    /**
     * Method for getting a page of information about published albums.
     *
     * @return the list of all albums.
     */
    @GetMapping("/page")
    public List<AlbumResponse> getAllPage() {
        return albumService.findAllPageable().stream()
                .map(album -> AlbumResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId()),
                        encryptionSystem.encrypt(album.getCreator().getId().getId())))
                .collect(Collectors.toList());
    }

    /**
     * Method for getting information about all albums by a particular artist.
     *
     * @param artistId - artist's id
     * @return the list of all filtered albums.
     */
    @GetMapping("/artist/{artistId}")
    public List<AlbumResponse> getAllByArtist(@PathVariable String artistId) {
        return albumService.findAllByArtist(ArtistId.of(encryptionSystem.decrypt(artistId)))
                .stream().map(album -> AlbumResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId()),
                        encryptionSystem.encrypt(album.getCreator().getId().getId())))
                .collect(Collectors.toList());
    }

    /**
     * Method for getting information about all albums filtered by a particular genre.
     *
     * @param genre - the genre by which the filtering will be done
     * @return the list of all filtered albums.
     */
    @GetMapping("/genre/{genre}")
    public List<AlbumResponse> getAllByGenre(@PathVariable String genre) {
        return albumService.findAllByGenre(Genre.valueOf(genre)).stream()
                .map(album -> AlbumResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId()),
                        encryptionSystem.encrypt(album.getCreator().getId().getId())))
                .collect(Collectors.toList());
    }

    /**
     * Method for search albums.
     *
     * @param searchTerm - the search term by which the filtering will be done
     * @return the list of all filtered albums.
     */
    @GetMapping("/search/{searchTerm}")
    public List<AlbumResponse> searchAlbums(@PathVariable String searchTerm) {
        return albumService.searchAlbums(searchTerm).stream()
                .map(album -> AlbumResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId()),
                        encryptionSystem.encrypt(album.getCreator().getId().getId())))
                .collect(Collectors.toList());
    }

    /**
     * Method for getting information about a specific album.
     *
     * @param id - album's id.
     * @return the found album.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponse> findById(@PathVariable String id) {
        return this.albumService.findById(AlbumId.of(encryptionSystem.decrypt(id)))
                .map(album -> ResponseEntity.ok().body(AlbumResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId()),
                        encryptionSystem.encrypt(album.getCreator().getId().getId()))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Method for creating a new album.
     *
     * @param albumTransactionRequest - dto object containing information for the album to be created.
     * @return the created album.
     */
    @PostMapping("/publish")
    public ResponseEntity<AlbumResponse> publishAlbum(
            @RequestHeader(value = "Authorization") String authToken,
            @RequestPart MultipartFile cover,
            @RequestPart @Valid AlbumTransactionRequest albumTransactionRequest) {
        String username = jwtUtils.getUserNameFromJwtToken(authToken.replace("Bearer ", ""));
        return this.albumService.publishAlbum(albumTransactionRequest, cover,
                username, getDecryptedSongIds(albumTransactionRequest.getSongIdList()))
                .map(album -> ResponseEntity.ok().body(AlbumResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId()),
                        encryptionSystem.encrypt(album.getCreator().getId().getId()))))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    private List<String> getDecryptedSongIds(List<String> songIdList) {
        return songIdList.stream().map(encryptionSystem::decrypt).collect(Collectors.toList());
    }

    /**
     * Method for raising an existing album's tier.
     *
     * @param albumShortTransactionRequest - dto object containing information for the album to be updated.
     * @return the created album.
     */
    @PostMapping("/raise-tier")
    public ResponseEntity<AlbumResponse> raiseTierAlbum(
            @RequestBody @Valid AlbumShortTransactionRequest albumShortTransactionRequest) {
        return this.albumService.raiseTierAlbum(albumShortTransactionRequest,
                AlbumId.of(encryptionSystem.decrypt(albumShortTransactionRequest.getAlbumId())))
                .map(album -> ResponseEntity.ok().body(AlbumResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId()),
                        encryptionSystem.encrypt(album.getCreator().getId().getId()))))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
