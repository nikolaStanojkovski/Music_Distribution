package com.musicdistribution.albumcatalog.domain.repository;

import com.musicdistribution.albumcatalog.domain.models.entity.Song;
import com.musicdistribution.albumcatalog.domain.models.entity.SongId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for a song.
 */
@Repository
public interface SongRepository extends JpaRepository<Song, SongId> {
}