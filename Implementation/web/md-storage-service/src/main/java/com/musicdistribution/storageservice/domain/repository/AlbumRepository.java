package com.musicdistribution.storageservice.domain.repository;

import com.musicdistribution.storageservice.domain.model.entity.Album;
import com.musicdistribution.storageservice.domain.model.entity.AlbumId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for an album.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, AlbumId> {
}
