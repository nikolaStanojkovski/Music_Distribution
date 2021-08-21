package finki.ukim.mk.emtproject.albumcatalog.domain.repository;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Album;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.AlbumId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * AlbumRepository - JPA Repository for an album
 */
public interface AlbumRepository extends JpaRepository<Album, AlbumId> {
}
