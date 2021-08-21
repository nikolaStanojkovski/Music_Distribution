package finki.ukim.mk.emtproject.albumpublishing.domain.repository;

import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbum;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbumId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * PublishedAlbumRepository - JPA Repository for a published album
 */
public interface PublishedAlbumRepository extends JpaRepository<PublishedAlbum, PublishedAlbumId> {
}
