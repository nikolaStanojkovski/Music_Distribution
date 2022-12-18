package com.musicdistribution.streamingservice.service.implementation;

import com.musicdistribution.sharedkernel.domain.repository.SearchRepository;
import com.musicdistribution.sharedkernel.domain.response.SearchResultResponse;
import com.musicdistribution.sharedkernel.domain.valueobjects.Email;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import com.musicdistribution.streamingservice.constant.EntityConstants;
import com.musicdistribution.streamingservice.domain.model.entity.core.Listener;
import com.musicdistribution.streamingservice.domain.model.entity.id.AlbumId;
import com.musicdistribution.streamingservice.domain.model.entity.id.ArtistId;
import com.musicdistribution.streamingservice.domain.model.entity.id.ListenerId;
import com.musicdistribution.streamingservice.domain.model.entity.id.SongId;
import com.musicdistribution.streamingservice.domain.model.enums.EntityType;
import com.musicdistribution.streamingservice.domain.model.request.AuthRequest;
import com.musicdistribution.streamingservice.domain.repository.core.AlbumRepository;
import com.musicdistribution.streamingservice.domain.repository.core.ArtistRepository;
import com.musicdistribution.streamingservice.domain.repository.core.ListenerRepository;
import com.musicdistribution.streamingservice.domain.repository.core.SongRepository;
import com.musicdistribution.streamingservice.domain.valueobject.core.UserRegistrationInfo;
import com.musicdistribution.streamingservice.service.ListenerService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of the listener service.
 */
@Service
@Transactional
@AllArgsConstructor
public class ListenerServiceImpl implements ListenerService {

    private final EntityManager entityManager;

    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final ListenerRepository listenerRepository;
    private final SearchRepository<Listener> searchRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Method used for fetching a page of listeners from the database.
     *
     * @param pageable - the wrapper object containing pagination data.
     * @return a page of the listeners.
     */
    @Override
    public Page<Listener> findAll(Pageable pageable) {
        return listenerRepository.findAll(pageable);
    }

    /**
     * Method used for reading the total number of entities from the database.
     *
     * @return the total number of listeners from the database.
     */
    @Override
    public Long findTotalSize() {
        return listenerRepository.count();
    }

    /**
     * Method used for searching listeners.
     *
     * @param searchParams - the object parameters by which the filtering is to be done.
     * @param searchTerm   - the search term by which the filtering is to be done.
     * @param pageable     - the wrapper object containing pagination data.
     * @return a list of the filtered listeners.
     */
    @Override
    public SearchResultResponse<Listener> search(List<String> searchParams, String searchTerm, Pageable pageable) {
        return searchRepository.search(searchParams, false, searchTerm, pageable);
    }

    /**
     * Method used for fetching a listener with the specified ID.
     *
     * @param id         - listener's ID.
     * @param entityType - the entity type which should be eagerly included with the found object.
     * @return an optional with the found listener.
     */
    @Override
    public Optional<Listener> findById(ListenerId id, EntityType entityType) {
        EntityGraph<Listener> graph = this.entityManager.createEntityGraph(Listener.class);
        String attributeNode = getAttributeNodes(entityType);
        if (!attributeNode.isEmpty()) {
            graph.addAttributeNodes(attributeNode);
        }
        return Optional.ofNullable(
                this.entityManager.find(Listener.class,
                        id,
                        Map.of(QueryHints.LOADGRAPH, graph)));
    }

    /**
     * Method used for reading the attribute nodes of the entity graph, given the entity fetch type.
     *
     * @param entityType - the type of the entity that is to be fetched by the entity graph.
     * @return the chosen attribute node.
     */
    private String getAttributeNodes(EntityType entityType) {
        switch (entityType) {
            case ARTISTS:
                return EntityConstants.FAVOURITE_ARTISTS;
            case ALBUMS:
                return EntityConstants.FAVOURITE_ALBUMS;
            case SONGS:
                return EntityConstants.FAVOURITE_SONGS;
            default:
                return StringUtils.EMPTY;
        }
    }

    /**
     * Method used for authenticating an existing listener from the database.
     *
     * @param authRequest - a wrapper object containing listener's information needed for authentication.
     * @return an optional with the authenticated listener.
     */
    @Override
    public Optional<Listener> login(AuthRequest authRequest) {
        return findByEmail(authRequest.getUsername(), authRequest.getEmailDomain());
    }

    /**
     * Method used for registering a new listener in the database.
     *
     * @param listenerRequest - a wrapper object containing listener's information needed for registration.
     * @return an optional with the registered listener.
     */
    @Override
    public Optional<Listener> register(AuthRequest listenerRequest) {
        if (findByEmail(listenerRequest.getUsername(), listenerRequest.getEmailDomain()).isPresent() ||
                findByUsername(listenerRequest.getUsername()).isPresent())
            return Optional.empty();

        Listener listener = listenerRepository.save(Listener.build(
                UserRegistrationInfo.from(listenerRequest.getUsername(), passwordEncoder.encode(listenerRequest.getPassword())),
                Email.from(listenerRequest.getUsername(), listenerRequest.getEmailDomain())
        ));

        return Optional.of(listener);
    }

    /**
     * Method used for filtering an listener by the specified username.
     *
     * @param username - the username by which the filtering is to be done.
     * @return an optional with the found listener.
     */
    private Optional<Listener> findByUsername(String username) {
        return listenerRepository.findByUserRegistrationInfo_Username(username);
    }

    /**
     * Method used for filtering a listener by the specified email.
     *
     * @param username    - the username by which the filtering is to be done.
     * @param emailDomain - the email domain by which the filtering is to be done.
     * @return an optional with the found listener.
     */
    private Optional<Listener> findByEmail(String username, EmailDomain emailDomain) {
        return listenerRepository.findByUserEmail(Email.from(username, emailDomain));
    }

    /**
     * Method used for adding an object to the list of favourites.
     *
     * @param objectId - a wrapper object containing object's identifier.
     * @param type     - a wrapper object containing entity's type.
     * @return a flag determining whether the object was added to the favourites list.
     */
    @Override
    public Optional<Boolean> addToFavourite(ListenerId listenerId, String objectId, EntityType type) {
        return findById(listenerId, EntityType.NONE)
                .map(listener -> {
                    switch (type) {
                        case SONGS:
                            return songRepository.findById(SongId.of(objectId))
                                    .map(song -> {
                                        listener.addFavouriteSong(song);
                                        listenerRepository.save(listener);
                                        listenerRepository.flush();
                                        return true;
                                    }).orElse(false);
                        case ALBUMS:
                            return albumRepository.findById(AlbumId.of(objectId))
                                    .map(album -> {
                                        listener.addFavouriteAlbum(album);
                                        listenerRepository.save(listener);
                                        listenerRepository.flush();
                                        return true;
                                    }).orElse(false);
                        case ARTISTS:
                            return artistRepository.findById(ArtistId.of(objectId))
                                    .map(artist -> {
                                        listener.addFavouriteArtist(artist);
                                        listenerRepository.save(listener);
                                        listenerRepository.flush();
                                        return true;
                                    }).orElse(false);
                        default:
                            return false;
                    }
                });
    }

    /**
     * Method used for removing an object to the list of favourites.
     *
     * @param listenerId - a wrapper object containing listener's identifier.
     * @param objectId   - a string object containing object's identifier.
     * @param type       - a wrapper object containing entity's type.
     * @return a flag determining whether the object was removed from the favourites list.
     */
    @Override
    public Optional<Boolean> removeFromFavourite(ListenerId listenerId, String objectId, EntityType type) {
        return findById(listenerId, EntityType.NONE)
                .map(listener -> {
                    switch (type) {
                        case SONGS:
                            return songRepository.findById(SongId.of(objectId))
                                    .map(song -> {
                                        listener.removeFavouriteSong(song);
                                        listenerRepository.save(listener);
                                        listenerRepository.flush();
                                        return true;
                                    }).orElse(false);
                        case ALBUMS:
                            return albumRepository.findById(AlbumId.of(objectId))
                                    .map(album -> {
                                        listener.removeFavouriteAlbum(album);
                                        listenerRepository.save(listener);
                                        listenerRepository.flush();
                                        return true;
                                    }).orElse(false);
                        case ARTISTS:
                            return artistRepository.findById(ArtistId.of(objectId))
                                    .map(artist -> {
                                        listener.removeFavouriteArtist(artist);
                                        listenerRepository.save(listener);
                                        listenerRepository.flush();
                                        return true;
                                    }).orElse(false);
                        default:
                            return false;
                    }
                });
    }
}
