package com.musicdistribution.streamingservice.domain.repository.core;

import com.musicdistribution.streamingservice.domain.model.entity.core.Album;
import com.musicdistribution.streamingservice.domain.model.entity.id.AlbumId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for an album entity.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, AlbumId> {
}
