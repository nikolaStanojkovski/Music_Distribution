package finki.ukim.mk.emtproject.albumcatalog.domain.repository;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Song;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.SongId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for a song.
 */
@Repository
public interface SongRepository extends JpaRepository<Song, SongId> {
}