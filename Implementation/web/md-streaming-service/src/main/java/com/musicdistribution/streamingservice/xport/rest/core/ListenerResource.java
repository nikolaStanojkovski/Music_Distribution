package com.musicdistribution.streamingservice.xport.rest.core;

import com.musicdistribution.sharedkernel.config.ApiController;
import com.musicdistribution.streamingservice.constant.EntityConstants;
import com.musicdistribution.streamingservice.constant.PathConstants;
import com.musicdistribution.streamingservice.domain.model.entity.core.Listener;
import com.musicdistribution.streamingservice.domain.model.entity.id.ListenerId;
import com.musicdistribution.streamingservice.domain.model.enums.EntityType;
import com.musicdistribution.streamingservice.domain.model.response.core.ListenerResponse;
import com.musicdistribution.sharedkernel.domain.response.SearchResultResponse;
import com.musicdistribution.streamingservice.domain.service.IEncryptionSystem;
import com.musicdistribution.streamingservice.service.ListenerService;
import lombok.AllArgsConstructor;
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
                        encryptionSystem.encrypt(listener.getId().getId())))
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
                        encryptionSystem.encrypt(listener.getId().getId())))
                .collect(Collectors.toList()), pageable, listenerSearchResultResponse.getResultSize());
    }

    /**
     * Method used for fetching information about a specific listener.
     *
     * @param id - listener's ID.
     * @return the found listener.
     */
    @GetMapping(PathConstants.FORMATTED_ID)
    public ResponseEntity<ListenerResponse> findById(@PathVariable String id) {
        return this.listenerService.findById(ListenerId.of(encryptionSystem.decrypt(id)))
                .map(listener -> ResponseEntity.ok().body(ListenerResponse.from(listener,
                        encryptionSystem.encrypt(listener.getId().getId()))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Method used for adding an entity to a specific listener favourite list.
     *
     * @param id           - listener's ID.
     * @param publishingId - ID of the entity which is being added to favourites.
     * @param entityType   - type of the entity which is being added to favourites.
     * @return a flag determining whether the entity was added to the favourites list or not.
     */
    @PutMapping(PathConstants.API_FAVOURITE)
    public ResponseEntity<Boolean> addToFavourite(@PathVariable String id,
                                                  @RequestParam String publishingId,
                                                  @RequestParam EntityType entityType) {

        return this.listenerService.addToFavourite(ListenerId.of(encryptionSystem.decrypt(id)),
                encryptionSystem.decrypt(publishingId), entityType)
                .map(entityValue -> ResponseEntity.ok().body(entityValue))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
