package com.musicdistribution.albumcatalog.xport.rest.core;

import com.musicdistribution.albumcatalog.domain.models.entity.AlbumId;
import com.musicdistribution.albumcatalog.domain.models.request.AlbumShortTransactionRequest;
import com.musicdistribution.albumcatalog.domain.models.request.AlbumTransactionRequest;
import com.musicdistribution.albumcatalog.domain.models.response.AlbumResponse;
import com.musicdistribution.albumcatalog.domain.services.IEncryptionSystem;
import com.musicdistribution.albumcatalog.security.jwt.JwtUtils;
import com.musicdistribution.albumcatalog.services.AlbumService;
import com.musicdistribution.sharedkernel.util.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    private final AlbumService albumService;

    private final JwtUtils jwtUtils;
    private final IEncryptionSystem encryptionSystem;

    /**
     * Method for getting information about all albums.
     *
     * @return the list of all albums.
     */
    @GetMapping
    public Page<AlbumResponse> findAll(Pageable pageable) {
        List<AlbumResponse> albums = albumService.findAll(pageable).stream()
                .map(album -> AlbumResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId()),
                        encryptionSystem.encrypt(album.getCreator().getId().getId())))
                .collect(Collectors.toList());
        return new PageImpl<>(albums, pageable, albumService.findTotalSize());
    }

    /**
     * Method for searching albums.
     *
     * @param searchParams - the object parameters by which a filtering will be done
     * @param searchTerm   - the search term by which the filtering will be done
     * @param pageable     - the wrapper for paging/sorting/filtering
     * @return the page of the filtered albums.
     */
    @GetMapping("/search")
    public Page<AlbumResponse> search(@RequestParam String[] searchParams,
                                      @RequestParam String searchTerm,
                                      Pageable pageable) {
        List<AlbumResponse> filteredAlbums = albumService.search(List.of(searchParams),
                (List.of(searchParams).stream().filter(param ->
                        param.contains("id")).count() == searchParams.length)
                        ? encryptionSystem.decrypt(searchTerm) : searchTerm, pageable)
                .stream()
                .map(album -> AlbumResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId()),
                        encryptionSystem.encrypt(album.getCreator().getId().getId())))
                .collect(Collectors.toList());
        return new PageImpl<>(filteredAlbums, pageable, albumService.findTotalSize());
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
    public ResponseEntity<AlbumResponse> publish(
            @RequestHeader(value = "Authorization") String authToken,
            @RequestPart MultipartFile cover,
            @RequestPart @Valid AlbumTransactionRequest albumTransactionRequest) {
        String username = jwtUtils.getUserNameFromJwtToken(authToken.replace("Bearer ", ""));
        return this.albumService.publish(albumTransactionRequest, cover,
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
    public ResponseEntity<AlbumResponse> raiseTier(
            @RequestBody @Valid AlbumShortTransactionRequest albumShortTransactionRequest) {
        return this.albumService.raiseTier(albumShortTransactionRequest,
                AlbumId.of(encryptionSystem.decrypt(albumShortTransactionRequest.getAlbumId())))
                .map(album -> ResponseEntity.ok().body(AlbumResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId()),
                        encryptionSystem.encrypt(album.getCreator().getId().getId()))))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
