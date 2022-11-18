package com.musicdistribution.storageservice.domain.repository;

import com.musicdistribution.sharedkernel.domain.valueobjects.Email;
import com.musicdistribution.storageservice.domain.model.entity.Artist;
import com.musicdistribution.storageservice.domain.model.entity.ArtistId;
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
    Optional<Artist> findByArtistUserInfo_Username(String username);

    /**
     * Method used for fetching a user by his email.
     *
     * @param artistMail - the email used for filtering
     * @return an optional with an artist.
     */
    Optional<Artist> findByArtistContactInfo_Email(Email artistMail);
}
