package com.musicdistribution.streamingservice.xport.rest.core;

import com.musicdistribution.sharedkernel.util.ApiController;
import com.musicdistribution.streamingservice.constant.AuthConstants;
import com.musicdistribution.streamingservice.constant.EntityConstants;
import com.musicdistribution.streamingservice.constant.PathConstants;
import com.musicdistribution.streamingservice.constant.ServletConstants;
import com.musicdistribution.streamingservice.domain.model.entity.Song;
import com.musicdistribution.streamingservice.domain.model.entity.SongId;
import com.musicdistribution.streamingservice.domain.model.request.SongRequest;
import com.musicdistribution.streamingservice.domain.model.request.SongShortTransactionRequest;
import com.musicdistribution.streamingservice.domain.model.request.SongTransactionRequest;
import com.musicdistribution.streamingservice.domain.model.response.SearchResultResponse;
import com.musicdistribution.streamingservice.domain.model.response.SongResponse;
import com.musicdistribution.streamingservice.domain.service.IEncryptionSystem;
import com.musicdistribution.streamingservice.service.SongService;
import com.musicdistribution.streamingservice.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(PathConstants.API_SONGS)
public class SongResource {

    private final SongService songService;

    private final JwtUtil jwtUtil;
    private final IEncryptionSystem encryptionSystem;

    /**
     * Method used for fetching a page with songs.
     *
     * @param pageable - the wrapper object containing pagination data.
     * @return the page with the found songs.
     */
    @GetMapping
    public Page<SongResponse> findAll(@RequestHeader(value = ServletConstants.AUTH_HEADER) String authToken,
                                      Pageable pageable) {
        boolean isTokenValid = jwtUtil.validateJwtToken(authToken
                .replace(String.format("%s ", AuthConstants.JWT_TOKEN_PREFIX),
                        StringUtils.EMPTY));
        List<SongResponse> songs = songService.findAll(pageable, !isTokenValid)
                .stream()
                .map(song -> SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        encryptionSystem.encrypt(Optional.ofNullable(
                                song.getAlbum()).map(album -> album.getId().getId())
                                .orElse(StringUtils.EMPTY))))
                .collect(Collectors.toList());
        return new PageImpl<>(songs, pageable, songService.findTotalSize());
    }

    /**
     * Method used for searching songs.
     *
     * @param searchParams - the object parameters by which a filtering is to be done.
     * @param searchTerm   - the search term by which the filtering is to be done.
     * @param pageable     - the wrapper object containing pagination data.
     * @return the page with the filtered songs.
     */
    @GetMapping(PathConstants.SEARCH)
    public Page<SongResponse> search(@RequestHeader(value = ServletConstants.AUTH_HEADER) String authToken,
                                     @RequestParam String[] searchParams,
                                     @RequestParam String searchTerm,
                                     Pageable pageable) {
        boolean isTokenValid = jwtUtil.validateJwtToken(authToken
                .replace(String.format("%s ", AuthConstants.JWT_TOKEN_PREFIX),
                        StringUtils.EMPTY));
        SearchResultResponse<Song> songSearchResultResponse = songService.search(List.of(searchParams),
                !isTokenValid,
                (List.of(searchParams).stream().filter(param ->
                        param.contains(EntityConstants.ID)).count() == searchParams.length)
                        ? encryptionSystem.decrypt(searchTerm) : searchTerm, pageable);
        return new PageImpl<>(songSearchResultResponse.getResultPage()
                .stream()
                .map(song -> SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        encryptionSystem.encrypt(Optional.ofNullable(
                                song.getAlbum()).map(album -> album.getId().getId())
                                .orElse(StringUtils.EMPTY))))
                .collect(Collectors.toList()), pageable, songSearchResultResponse.getResultSize());
    }

    /**
     * Method for getting information about a specific song.
     *
     * @param id - song's id.
     * @return the found song.
     */
    @GetMapping(PathConstants.FORMATTED_ID)
    public ResponseEntity<SongResponse> findById(@PathVariable String id) {
        return this.songService.findById(SongId.of(encryptionSystem.decrypt(id)))
                .map(song -> ResponseEntity.ok().body(SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        encryptionSystem.encrypt(Optional.ofNullable(
                                song.getAlbum()).map(album -> album.getId().getId())
                                .orElse(StringUtils.EMPTY)))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Method USED for creating a new song.
     *
     * @param songRequest - an object wrapper containing information for the song to be created.
     * @return the created song.
     */
    @PostMapping(value = PathConstants.CREATE, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SongResponse> create(@RequestHeader(value = ServletConstants.AUTH_HEADER) String authToken,
                                               @RequestPart MultipartFile file,
                                               @RequestPart @Valid SongRequest songRequest) {
        String username = jwtUtil.getUserNameFromJwtToken(authToken.replace(String.format("%s ", AuthConstants.JWT_TOKEN_PREFIX), StringUtils.EMPTY));
        return this.songService.create(songRequest, file, username)
                .map(song -> ResponseEntity.ok().body(SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        null)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Method used for publishing a new song.
     *
     * @param songTransactionRequest - an object wrapper containing information for the song to be published.
     * @return the published song.
     */
    @PostMapping(PathConstants.PUBLISH)
    public ResponseEntity<SongResponse> publish(@RequestHeader(value = ServletConstants.AUTH_HEADER) String authToken,
                                                @RequestPart MultipartFile cover,
                                                @RequestPart @Valid SongTransactionRequest songTransactionRequest) {
        String username = jwtUtil.getUserNameFromJwtToken(authToken.replace(String.format("%s ",
                AuthConstants.JWT_TOKEN_PREFIX), StringUtils.EMPTY));
        return this.songService.publish(songTransactionRequest, cover,
                username, encryptionSystem.decrypt(songTransactionRequest.getSongId()))
                .map(song -> ResponseEntity.ok().body(SongResponse.from(song,
                        encryptionSystem.encrypt(song.getId().getId()),
                        encryptionSystem.encrypt(song.getCreator().getId().getId()),
                        null)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Method used for raising a song's tier.
     *
     * @param songShortTransactionRequest - an object wrapper containing information for the song to be updated.
     * @return the song whose tier was raised.
     */
    @PostMapping(PathConstants.RAISE_TIER)
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
