package com.musicdistribution.albumcatalog.domain.repository;

import com.musicdistribution.albumcatalog.domain.models.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for a song.
 */
@Repository
public interface SongRepository extends JpaRepository<Song, SongId> {

    List<Song> findAllByCreatorId(ArtistId creator_id);
    List<Song> findAllByAlbumId(AlbumId albumId);
}