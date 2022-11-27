package com.musicdistribution.streamingservice.domain.repository;

import com.musicdistribution.streamingservice.domain.model.entity.Listener;
import com.musicdistribution.streamingservice.domain.model.entity.id.ListenerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
