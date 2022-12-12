package com.musicdistribution.streamingservice.xport.rest.core;

import com.musicdistribution.sharedkernel.config.ApiController;
import com.musicdistribution.sharedkernel.domain.response.SearchResultResponse;
import com.musicdistribution.streamingservice.constant.AuthConstants;
import com.musicdistribution.streamingservice.constant.EntityConstants;
import com.musicdistribution.streamingservice.constant.PathConstants;
import com.musicdistribution.streamingservice.constant.ServletConstants;
import com.musicdistribution.streamingservice.domain.model.entity.core.Album;
import com.musicdistribution.streamingservice.domain.model.entity.id.AlbumId;
import com.musicdistribution.streamingservice.domain.model.request.AlbumShortTransactionRequest;
import com.musicdistribution.streamingservice.domain.model.request.AlbumTransactionRequest;
import com.musicdistribution.streamingservice.domain.model.response.core.AlbumResponse;
import com.musicdistribution.streamingservice.domain.service.IEncryptionSystem;
import com.musicdistribution.streamingservice.service.AlbumService;
import com.musicdistribution.streamingservice.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping(PathConstants.API_ALBUMS)
public class AlbumResource {

    private final AlbumService albumService;

    private final JwtUtil jwtUtil;
    private final IEncryptionSystem encryptionSystem;

    /**
     * Method used for fetching a page with albums.
     *
     * @param pageable - the wrapper object containing pagination data.
     * @return the page with the filtered albums.
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
     * Method used for searching albums.
     *
     * @param searchParams - the object parameters by which a filtering is to be done.
     * @param searchTerm   - the search term by which the filtering is to be done.
     * @param pageable     - the wrapper object containing pagination data.
     * @return the page with the filtered albums.
     */
    @GetMapping(PathConstants.SEARCH)
    public Page<AlbumResponse> search(@RequestParam String[] searchParams,
                                      @RequestParam String searchTerm,
                                      Pageable pageable) {
        SearchResultResponse<Album> albumSearchResultResponse = albumService.search(List.of(searchParams),
                (List.of(searchParams).stream().filter(param ->
                        param.contains(EntityConstants.ID)).count() == searchParams.length)
                        ? encryptionSystem.decrypt(searchTerm) : searchTerm, pageable);
        return new PageImpl<>(albumSearchResultResponse.getResultPage().stream()
                .map(album -> AlbumResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId()),
                        encryptionSystem.encrypt(album.getCreator().getId().getId())))
                .collect(Collectors.toList()), pageable, albumSearchResultResponse.getResultSize());
    }

    /**
     * Method used for fetching information about a specific album.
     *
     * @param id - album's ID.
     * @return the found album.
     */
    @GetMapping(PathConstants.FORMATTED_ID)
    public ResponseEntity<AlbumResponse> findById(@PathVariable String id) {
        return this.albumService.findById(AlbumId.of(encryptionSystem.decrypt(id)))
                .map(album -> ResponseEntity.ok().body(AlbumResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId()),
                        encryptionSystem.encrypt(album.getCreator().getId().getId()))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Method used for publishing an album.
     *
     * @param authToken               - the access token of the user which is being authenticated.
     * @param cover                   - the cover picture of the album that is to be published.
     * @param albumTransactionRequest - an object wrapper containing information for the album to be published.
     * @return the published album.
     */
    @PostMapping(PathConstants.PUBLISH)
    public ResponseEntity<AlbumResponse> publish(
            @RequestHeader(value = ServletConstants.AUTH_HEADER) String authToken,
            @RequestPart MultipartFile cover,
            @RequestPart @Valid AlbumTransactionRequest albumTransactionRequest) {
        String username = jwtUtil.getUserNameFromJwtToken(authToken
                .replace(String.format("%s ", AuthConstants.JWT_TOKEN_PREFIX), StringUtils.EMPTY));
        return this.albumService.publish(albumTransactionRequest, cover,
                username, getDecryptedSongIds(albumTransactionRequest.getSongIdList()))
                .map(album -> ResponseEntity.ok().body(AlbumResponse.from(album,
                        encryptionSystem.encrypt(album.getId().getId()),
                        encryptionSystem.encrypt(album.getCreator().getId().getId()))))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Method used for decrypting the song IDs from a given list.
     *
     * @param songIdList - the list of the IDs to be decrypted.
     * @return a list of decrypted song IDs.
     */
    private List<String> getDecryptedSongIds(List<String> songIdList) {
        return songIdList.stream().map(encryptionSystem::decrypt).collect(Collectors.toList());
    }

    /**
     * Method used for raising an album's tier.
     *
     * @param albumShortTransactionRequest - an object wrapper containing information for the album to be updated.
     * @return the updated album.
     */
    @PostMapping(PathConstants.RAISE_TIER)
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
