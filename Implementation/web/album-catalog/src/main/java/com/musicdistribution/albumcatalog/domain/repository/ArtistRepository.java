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
     * Method for reading an artist by email.
     *
     * @param artistMail - artist's email used for filtering
     * @return an optional with artist.
     */
    Optional<Artist> findByArtistContactInfo_Email(Email artistMail);

    /**
     * Method used for searching artists.
     *
     * @param artistPersonalInfo_artName   - used for filtering
     * @param artistPersonalInfo_firstName - used for filtering
     * @param artistPersonalInfo_lastName  - used for filtering
     * @return the filtered list of artists.
     */
    List<Artist> findAllByArtistPersonalInfo_ArtNameIgnoreCaseOrArtistPersonalInfo_FirstNameIgnoreCaseOrArtistPersonalInfo_LastNameIgnoreCase(String artistPersonalInfo_artName, String artistPersonalInfo_firstName, String artistPersonalInfo_lastName);
}
