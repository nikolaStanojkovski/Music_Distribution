package com.musicdistribution.albumcatalog.domain.repository;

import com.musicdistribution.albumcatalog.domain.models.entity.Album;
import com.musicdistribution.albumcatalog.domain.models.entity.AlbumId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for an album.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, AlbumId> {
}
