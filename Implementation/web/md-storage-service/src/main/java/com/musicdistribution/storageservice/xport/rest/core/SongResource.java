package com.musicdistribution.storageservice.xport.rest.core;

import com.musicdistribution.sharedkernel.util.ApiController;
import com.musicdistribution.storageservice.domain.model.SearchResult;
import com.musicdistribution.storageservice.domain.model.entity.Song;
import com.musicdistribution.storageservice.domain.model.entity.SongId;
import com.musicdistribution.storageservice.domain.model.request.SongRequest;
import com.musicdistribution.storageservice.domain.model.request.SongShortTransactionRequest;
import com.musicdistribution.storageservice.domain.model.request.SongTransactionRequest;
import com.musicdistribution.storageservice.domain.model.response.SongResponse;
import com.musicdistribution.storageservice.domain.service.IEncryptionSystem;
import com.musicdistribution.storageservice.security.jwt.JwtUtil;
import com.musicdistribution.storageservice.service.SongService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    private final SongService songService;

    private final JwtUtil jwtUtil;
    private final IEncryptionSystem encryptionSystem;

    /**
     * Method for getting information about all songs.
     *
     * @return a page of the filtered songs.
     */
    @GetMapping
    public Page<SongResponse> findAll(Pageable pageable) {
        List<SongResponse> songs = songService.findAll(pageable).stream()
                .map(song -> SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        encryptionSystem.encrypt(Optional.ofNullable(
                                song.getAlbum()).map(album -> album.getId().getId())
                                .orElse(""))))
                .collect(Collectors.toList());
        return new PageImpl<>(songs, pageable, songService.findTotalSize());
    }

    /**
     * Method for searching songs.
     *
     * @param searchParams - the object parameters by which a filtering will be done
     * @param searchTerm   - the search term by which the filtering will be done
     * @param pageable     - the wrapper for paging/sorting/filtering
     * @return the page of the filtered songs.
     */
    @GetMapping("/search")
    public Page<SongResponse> search(@RequestParam String[] searchParams,
                                     @RequestParam String searchTerm,
                                     Pageable pageable) {
        SearchResult<Song> songSearchResult = songService.search(List.of(searchParams),
                (List.of(searchParams).stream().filter(param ->
                        param.contains("id")).count() == searchParams.length)
                        ? encryptionSystem.decrypt(searchTerm) : searchTerm, pageable);
        return new PageImpl<>(songSearchResult.getResultPage()
                .stream()
                .map(song -> SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        encryptionSystem.encrypt(Optional.ofNullable(
                                song.getAlbum()).map(album -> album.getId().getId())
                                .orElse(""))))
                .collect(Collectors.toList()), pageable, songSearchResult.getResultSize());
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

    /**
     * Method for creating a new song.
     *
     * @param songRequest - dto object containing information for the song to be created.
     * @return the created song.
     */
    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<SongResponse> create(@RequestHeader(value = "Authorization") String authToken,
                                               @RequestPart MultipartFile file,
                                               @RequestPart @Valid SongRequest songRequest) {
        String username = jwtUtil.getUserNameFromJwtToken(authToken.replace("Bearer ", ""));
        return this.songService.create(songRequest, file, username)
                .map(song -> ResponseEntity.ok().body(SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        null)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Method for publishing a song.
     *
     * @param songTransactionRequest - dto object for the song to be published.
     * @return the published song.
     */
    @PostMapping("/publish")
    public ResponseEntity<SongResponse> publish(@RequestHeader(value = "Authorization") String authToken,
                                                @RequestPart MultipartFile cover,
                                                @RequestPart @Valid SongTransactionRequest songTransactionRequest) {
        String username = jwtUtil.getUserNameFromJwtToken(authToken.replace("Bearer ", ""));
        return this.songService.publish(songTransactionRequest, cover,
                username, encryptionSystem.decrypt(songTransactionRequest.getSongId()))
                .map(song -> ResponseEntity.ok().body(SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        null)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Method for raising an existing song's tier.
     *
     * @param songShortTransactionRequest - dto object containing information for the song to be updated.
     * @return the created song.
     */
    @PostMapping("/raise-tier")
    public ResponseEntity<SongResponse> raiseTier(
            @RequestBody @Valid SongShortTransactionRequest songShortTransactionRequest) {
        return this.songService.raiseTier(songShortTransactionRequest,
                SongId.of(encryptionSystem.decrypt(songShortTransactionRequest.getSongId())))
                .map(song -> ResponseEntity.ok().body(SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        null)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
