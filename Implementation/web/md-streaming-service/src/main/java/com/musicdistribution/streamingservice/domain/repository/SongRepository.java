package com.musicdistribution.streamingservice.domain.repository;

import com.musicdistribution.streamingservice.domain.model.entity.Song;
import com.musicdistribution.streamingservice.domain.model.entity.SongId;
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