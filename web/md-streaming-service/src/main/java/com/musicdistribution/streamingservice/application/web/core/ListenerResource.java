package com.musicdistribution.streamingservice.application.web.core;

import com.musicdistribution.sharedkernel.infrastructure.ApiController;
import com.musicdistribution.sharedkernel.domain.response.SearchResultResponse;
import com.musicdistribution.streamingservice.constant.EntityConstants;
import com.musicdistribution.streamingservice.constant.PathConstants;
import com.musicdistribution.streamingservice.domain.model.entity.core.Listener;
import com.musicdistribution.streamingservice.domain.model.entity.id.ListenerId;
import com.musicdistribution.streamingservice.domain.model.enums.EntityType;
import com.musicdistribution.streamingservice.domain.model.response.core.AlbumResponse;
import com.musicdistribution.streamingservice.domain.model.response.core.ArtistResponse;
import com.musicdistribution.streamingservice.domain.model.response.core.ListenerResponse;
import com.musicdistribution.streamingservice.domain.model.response.core.SongResponse;
import com.musicdistribution.streamingservice.domain.service.IEncryptionSystem;
import com.musicdistribution.streamingservice.application.service.ListenerService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Listener Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping(PathConstants.API_LISTENERS)
public class ListenerResource {

    private final ListenerService listenerService;

    private final IEncryptionSystem encryptionSystem;

    /**
     * Method used for fetching a page with listeners.
     *
     * @param pageable - the wrapper object containing pagination data.
     * @return the page of the filtered listeners.
     */
    @GetMapping
    public Page<ListenerResponse> findAll(Pageable pageable) {
        List<ListenerResponse> listeners = listenerService.findAll(pageable).stream()
                .map(listener -> ListenerResponse.from(listener,
                        encryptionSystem.encrypt(listener.getId().getId()),
                        List.of(), List.of(), List.of()))
                .collect(Collectors.toList());
        return new PageImpl<>(listeners, pageable, listenerService.findTotalSize());
    }

    /**
     * Method used for searching listeners.
     *
     * @param searchParams - the object parameters by which a filtering is to be done.
     * @param searchTerm   - the search term by which the filtering is to be done.
     * @param pageable     - the wrapper object containing pagination data.
     * @return the page with the filtered listeners.
     */
    @GetMapping(PathConstants.SEARCH)
    public Page<ListenerResponse> search(@RequestParam String[] searchParams,
                                         @RequestParam String searchTerm,
                                         Pageable pageable) {
        SearchResultResponse<Listener> listenerSearchResultResponse = listenerService.search(List.of(searchParams),
                (List.of(searchParams).stream().filter(param ->
                        param.contains(EntityConstants.ID)).count() == searchParams.length)
                        ? encryptionSystem.decrypt(searchTerm) : searchTerm, pageable);
        return new PageImpl<>(listenerSearchResultResponse.getResultPage()
                .stream()
                .map(listener -> ListenerResponse.from(listener,
                        encryptionSystem.encrypt(listener.getId().getId()),
                        List.of(), List.of(), List.of()))
                .collect(Collectors.toList()), pageable, listenerSearchResultResponse.getResultSize());
    }

    /**
     * Method used for fetching information about a specific listener.
     *
     * @param id         - listener's ID.
     * @param entityType - the entity type to be eagerly fetched with the listener.
     * @return the found listener.
     */
    @GetMapping(PathConstants.FORMATTED_ID_ENTITY_TYPE)
    public ResponseEntity<ListenerResponse> findById(@PathVariable String id,
                                                     @PathVariable EntityType entityType) {
        return this.listenerService.findById(ListenerId.of(encryptionSystem.decrypt(id)), entityType)
                .map(listener -> ResponseEntity.ok().body(ListenerResponse.from(listener,
                        encryptionSystem.encrypt(listener.getId().getId()),
                        (entityType == EntityType.ARTISTS) ? convertArtistResponses(listener) : List.of(),
                        (entityType == EntityType.ALBUMS) ? convertAlbumResponses(listener) : List.of(),
                        (entityType == EntityType.SONGS) ? convertSongResponses(listener) : List.of())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Method used for converting listener favourite songs to adequate song response objects.
     *
     * @param listener - the listener from which the favourite songs will be taken.
     * @return a list of the created song responses.
     */
    private List<SongResponse> convertSongResponses(Listener listener) {
        if (listener.getFavouriteSongs() == null || listener.getFavouriteSongs().isEmpty()) {
            return List.of();
        }
        return listener.getFavouriteSongs().stream()
                .map(s -> SongResponse.from(s, encryptionSystem.encrypt(s.getId().getId()),
                        StringUtils.EMPTY, StringUtils.EMPTY))
                .collect(Collectors.toList());
    }

    /**
     * Method used for converting listener favourite albums to adequate album response objects.
     *
     * @param listener - the listener from which the favourite albums will be taken.
     * @return a list of the created album responses.
     */
    private List<AlbumResponse> convertAlbumResponses(Listener listener) {
        if (listener.getFavouriteAlbums() == null || listener.getFavouriteAlbums().isEmpty()) {
            return List.of();
        }
        return listener.getFavouriteAlbums().stream()
                .map(s -> AlbumResponse.from(s, encryptionSystem.encrypt(s.getId().getId()),
                        StringUtils.EMPTY))
                .collect(Collectors.toList());
    }

    /**
     * Method used for converting listener favourite artists to adequate artist response objects.
     *
     * @param listener - the listener from which the favourite artists will be taken.
     * @return a list of the created artist responses.
     */
    private List<ArtistResponse> convertArtistResponses(Listener listener) {
        if (listener.getFavouriteArtists() == null || listener.getFavouriteArtists().isEmpty()) {
            return List.of();
        }
        return listener.getFavouriteArtists().stream()
                .map(s -> ArtistResponse.from(s, encryptionSystem.encrypt(s.getId().getId())))
                .collect(Collectors.toList());
    }

    /**
     * Method used for adding an entity to a specific listener favourite list.
     *
     * @param id           - listener's ID.
     * @param publishingId - ID of the entity which is being added to favourites.
     * @param entityType   - type of the entity which is being added to favourites.
     * @return a flag determining whether the entity was added to the favourites list or not.
     */
    @PostMapping(PathConstants.API_FAVOURITE_ADD)
    public ResponseEntity<Boolean> addToFavourite(@PathVariable String id,
                                                  @RequestParam String publishingId,
                                                  @RequestParam EntityType entityType) {
        return this.listenerService.addToFavourite(ListenerId.of(encryptionSystem.decrypt(id)),
                encryptionSystem.decrypt(publishingId), entityType)
                .map(entityValue -> ResponseEntity.ok().body(entityValue))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Method used for removing an entity from a specific listener favourite list.
     *
     * @param id           - listener's ID.
     * @param publishingId - ID of the entity which is being removed from favourites.
     * @param entityType   - type of the entity which is being removed from favourites.
     * @return a flag determining whether the entity was removed from the favourites list or not.
     */
    @PostMapping(PathConstants.API_FAVOURITE_REMOVE)
    public ResponseEntity<Boolean> removeFromFavourite(@PathVariable String id,
                                                       @RequestParam String publishingId,
                                                       @RequestParam EntityType entityType) {
        return this.listenerService.removeFromFavourite(ListenerId.of(encryptionSystem.decrypt(id)),
                encryptionSystem.decrypt(publishingId), entityType)
                .map(entityValue -> ResponseEntity.ok().body(entityValue))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
