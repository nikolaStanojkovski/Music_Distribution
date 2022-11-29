package com.musicdistribution.streamingservice.service;

import com.musicdistribution.streamingservice.domain.model.entity.core.Listener;
import com.musicdistribution.streamingservice.domain.model.entity.id.ListenerId;
import com.musicdistribution.streamingservice.domain.model.enums.EntityType;
import com.musicdistribution.streamingservice.domain.model.request.AuthRequest;
import com.musicdistribution.sharedkernel.domain.response.SearchResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * A service that contains the specific business logic for listener entities.
 */
public interface ListenerService {

    /**
     * Method used for fetching a page of listeners from the database.
     *
     * @param pageable - the wrapper object containing pagination data.
     * @return a page of the listeners.
     */
    Page<Listener> findAll(Pageable pageable);

    /**
     * Method used for reading the total number of entities from the database.
     *
     * @return the total number of listeners from the database.
     */
    Long findTotalSize();

    /**
     * Method used for searching listeners.
     *
     * @param searchParams - the object parameters by which the filtering is to be done.
     * @param searchTerm   - the search term by which the filtering is to be done.
     * @param pageable     - the wrapper object containing pagination data.
     * @return a list of the filtered listeners.
     */
    SearchResultResponse<Listener> search(List<String> searchParams, String searchTerm, Pageable pageable);

    /**
     * Method used for fetching a listener with the specified ID.
     *
     * @param id - listener's ID.
     * @return an optional with the found listener.
     */
    Optional<Listener> findById(ListenerId id);

    /**
     * Method used for authenticating an existing listener from the database.
     *
     * @param listener - a wrapper object containing listener's information needed for authentication.
     * @return an optional with the authenticated listener.
     */
    Optional<Listener> login(AuthRequest listener);

    /**
     * Method used for registering a new listener in the database.
     *
     * @param listener - a wrapper object containing listener's information needed for registration.
     * @return an optional with the registered listener.
     */
    Optional<Listener> register(AuthRequest listener);

    /**
     * Method used for adding an object to the list of favourites.
     *
     * @param listenerId - a wrapper object containing listener's identifier.
     * @param objectId   - a string object containing object's identifier.
     * @param type       - a wrapper object containing entity's type.
     * @return a flag determining whether the object was added to the favourites list.
     */
    Optional<Boolean> addToFavourite(ListenerId listenerId, String objectId, EntityType type);
}
