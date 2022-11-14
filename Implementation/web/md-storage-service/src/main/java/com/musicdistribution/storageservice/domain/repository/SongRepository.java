package com.musicdistribution.storageservice.domain.repository;

import com.musicdistribution.storageservice.domain.model.entity.Song;
import com.musicdistribution.storageservice.domain.model.entity.SongId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for a song.
 */
@Repository
public interface SongRepository extends JpaRepository<Song, SongId> {
}