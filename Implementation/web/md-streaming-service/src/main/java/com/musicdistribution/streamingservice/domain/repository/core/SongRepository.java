package com.musicdistribution.streamingservice.domain.repository.core;

import com.musicdistribution.streamingservice.domain.model.entity.core.Song;
import com.musicdistribution.streamingservice.domain.model.entity.id.SongId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for a song entity.
 */
@Repository
public interface SongRepository extends JpaRepository<Song, SongId> {

    Page<Song> findByIsPublished(Boolean isPublished, Pageable pageable);
}