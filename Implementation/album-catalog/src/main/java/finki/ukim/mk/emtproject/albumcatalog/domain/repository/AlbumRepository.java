package finki.ukim.mk.emtproject.albumcatalog.domain.repository;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Album;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.AlbumId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for an album.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, AlbumId> {
}
