package com.musicdistribution.streamingservice.domain.repository;

import com.musicdistribution.sharedkernel.domain.valueobjects.Email;
import com.musicdistribution.streamingservice.domain.model.entity.Artist;
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
     * Method used for fetching a user by his username.
     *
     * @param username - the username used for filtering
     * @return an optional with an artist.
     */
    Optional<Artist> findByUserRegistrationInfo_Username(String username);

    /**
     * Method used for fetching a user by his email.
     *
     * @param artistMail - the email used for filtering
     * @return an optional with an artist.
     */
    Optional<Artist> findByUserContactInfo_Email(Email artistMail);
}
