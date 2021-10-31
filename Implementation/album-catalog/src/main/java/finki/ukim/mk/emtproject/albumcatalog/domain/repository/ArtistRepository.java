package finki.ukim.mk.emtproject.albumcatalog.domain.repository;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Artist;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.ArtistId;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Email;
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
