package com.musicdistribution.streamingservice.service.implementation;

import com.musicdistribution.sharedkernel.domain.base.DomainObjectId;
import com.musicdistribution.sharedkernel.domain.valueobjects.Email;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import com.musicdistribution.streamingservice.domain.model.entity.Listener;
import com.musicdistribution.streamingservice.domain.model.entity.id.AlbumId;
import com.musicdistribution.streamingservice.domain.model.entity.id.ArtistId;
import com.musicdistribution.streamingservice.domain.model.entity.id.ListenerId;
import com.musicdistribution.streamingservice.domain.model.entity.id.SongId;
import com.musicdistribution.streamingservice.domain.model.enums.EntityType;
import com.musicdistribution.streamingservice.domain.model.request.AuthRequest;
import com.musicdistribution.streamingservice.domain.model.response.SearchResultResponse;
import com.musicdistribution.streamingservice.domain.repository.*;
import com.musicdistribution.streamingservice.domain.valueobject.UserRegistrationInfo;
import com.musicdistribution.streamingservice.service.ListenerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the listener service.
 */
@Service
@Transactional
@AllArgsConstructor
public class ListenerServiceImpl implements ListenerService {

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
     * @param id - listener's ID.
     * @return an optional with the found listener.
     */
    @Override
    public Optional<Listener> findById(ListenerId id) {
        return listenerRepository.findById(id);
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
    public boolean addToFavourite(ListenerId listenerId, DomainObjectId objectId, EntityType type) {
        return findById(listenerId)
                .map(listener -> {
                    switch (type) {
                        case SONGS:
                            return songRepository.findById(SongId.of(objectId.toString()))
                                    .map(song -> {
                                        listener.addFavouriteSong(song);
                                        listenerRepository.save(listener);
                                        listenerRepository.flush();
                                        return true;
                                    }).orElse(false);
                        case ALBUMS:
                            return albumRepository.findById(AlbumId.of(objectId.toString()))
                                    .map(album -> {
                                        listener.addFavouriteAlbum(album);
                                        listenerRepository.save(listener);
                                        listenerRepository.flush();
                                        return true;
                                    }).orElse(false);
                        case ARTISTS:
                            return artistRepository.findById(ArtistId.of(objectId.toString()))
                                    .map(artist -> {
                                        listener.addFavouriteArtist(artist);
                                        listenerRepository.save(listener);
                                        listenerRepository.flush();
                                        return true;
                                    }).orElse(false);
                        default:
                            return false;
                    }
                }).orElse(false);
    }
}
