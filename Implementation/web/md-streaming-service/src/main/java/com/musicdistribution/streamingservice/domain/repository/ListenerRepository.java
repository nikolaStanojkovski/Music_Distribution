package com.musicdistribution.streamingservice.domain.repository;

import com.musicdistribution.sharedkernel.domain.valueobjects.Email;
import com.musicdistribution.streamingservice.domain.model.entity.Listener;
import com.musicdistribution.streamingservice.domain.model.entity.id.ArtistId;
import com.musicdistribution.streamingservice.domain.model.entity.id.ListenerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for a listener entity.
 */
@Repository
public interface ListenerRepository extends JpaRepository<Listener, ListenerId> {

    /**
     * Method used for fetching a listener by his username.
     *
     * @param username - the username used for filtering.
     * @return an optional with a listener.
     */
    Optional<Listener> findByUserRegistrationInfo_Username(String username);

    /**
     * Method used for fetching a listener by his email.
     *
     * @param email - the email used for filtering.
     * @return an optional with a listener.
     */
    Optional<Listener> findByUserEmail(Email email);

    /**
     * Method used for fetching the list of favourite artists.
     *
     * @param artistId - the artist ID used for filtering.
     * @return a list with the favourite artists.
     */
    List<Listener> findAllByFavouriteArtists_Id(ArtistId artistId);
}
