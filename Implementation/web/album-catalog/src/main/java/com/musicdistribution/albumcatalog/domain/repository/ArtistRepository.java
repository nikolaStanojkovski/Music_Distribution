package com.musicdistribution.albumcatalog.domain.repository;

import com.musicdistribution.albumcatalog.domain.models.entity.Artist;
import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;
import com.musicdistribution.sharedkernel.domain.valueobjects.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for an artist.
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, ArtistId> {

    /**
     * Method for reading a user by his username.
     *
     * @param username - artist's username used for filtering
     * @return an optional with artist.
     */
    Optional<Artist> findByArtistUserInfo_Username(String username);

    /**
     * Method for reading an artist by email.
     *
     * @param artistMail - artist's email used for filtering
     * @return an optional with artist.
     */
    Optional<Artist> findByArtistContactInfo_Email(Email artistMail);
}
