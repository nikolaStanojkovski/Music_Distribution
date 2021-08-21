package finki.ukim.mk.emtproject.albumcatalog.domain.repository;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Album;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.Song;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.SongId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * SongRepository - JPA Repository for a song
 */
public interface SongRepository extends JpaRepository<Song, SongId> {
}
