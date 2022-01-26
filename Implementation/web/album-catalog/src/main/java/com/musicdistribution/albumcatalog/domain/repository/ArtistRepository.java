package com.musicdistribution.albumcatalog.domain.repository;

import com.musicdistribution.albumcatalog.domain.models.entity.Artist;
import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;
import com.musicdistribution.sharedkernel.domain.valueobjects.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository for an artist.
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, ArtistId> {

    Optional<Artist> findByArtistContactInfo_Email(Email artistMail);
}
