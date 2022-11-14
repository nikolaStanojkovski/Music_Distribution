package com.musicdistribution.storageservice.xport.rest.core;

import com.musicdistribution.storageservice.domain.model.SearchResult;
import com.musicdistribution.storageservice.domain.model.entity.Album;
import com.musicdistribution.storageservice.domain.model.entity.AlbumId;
import com.musicdistribution.storageservice.domain.model.request.AlbumShortTransactionRequest;
import com.musicdistribution.storageservice.domain.model.request.AlbumTransactionRequest;
import com.musicdistribution.storageservice.domain.model.response.AlbumResponse;
import com.musicdistribution.storageservice.domain.service.IEncryptionSystem;
import com.musicdistribution.storageservice.security.jwt.JwtUtil;
import com.musicdistribution.storageservice.service.AlbumService;
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

    private final JwtUtil jwtUtil;
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
        SearchResult<Album> albumSearchResult = albumService.search(List.of(searchParams),
                (List.of(searchParams).stream().filter(param ->
                        param.contains("id")).count() == searchParams.length)
                        ? encryptionSystem.decrypt(searchTerm) : searchTerm, pageable);
        return new PageImpl<>( albumSearchResult.getResultPage().stream()
                .map(album -> AlbumResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId()),
                        encryptionSystem.encrypt(album.getCreator().getId().getId())))
                .collect(Collectors.toList()), pageable, albumSearchResult.getResultSize());
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
        String username = jwtUtil.getUserNameFromJwtToken(authToken.replace("Bearer ", ""));
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
