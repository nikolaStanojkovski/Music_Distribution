package com.musicdistribution.streamingservice.domain.repository.core;

import com.musicdistribution.sharedkernel.domain.valueobjects.Email;
import com.musicdistribution.streamingservice.domain.model.entity.core.Artist;
import com.musicdistribution.streamingservice.domain.model.entity.id.ArtistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository for an artist entity.
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, ArtistId> {

    /**
     * Method used for fetching an artist by his username.
     *
     * @param username - the username used for filtering.
     * @return an optional with an artist.
     */
    Optional<Artist> findByUserRegistrationInfo_Username(String username);

    /**
     * Method used for fetching an artist by his email.
     *
     * @param email - the email used for filtering.
     * @return an optional with an artist.
     */
    Optional<Artist> findByUserContactInfo_Email(Email email);
}
