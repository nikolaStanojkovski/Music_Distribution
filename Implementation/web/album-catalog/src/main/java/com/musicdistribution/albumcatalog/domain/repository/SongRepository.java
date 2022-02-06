package com.musicdistribution.albumcatalog.domain.repository;

import com.musicdistribution.albumcatalog.domain.models.entity.AlbumId;
import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;
import com.musicdistribution.albumcatalog.domain.models.entity.Song;
import com.musicdistribution.albumcatalog.domain.models.entity.SongId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for a song.
 */
@Repository
public interface SongRepository extends JpaRepository<Song, SongId> {

    /**
     * Method used for reading all songs by particular artist.
     *
     * @param creator_id - artist's id used for filtering.
     * @return the filtered list of songs.
     */
    List<Song> findAllByCreatorId(ArtistId creator_id);

    /**
     * Method used for reading all songs by particular album.
     *
     * @param albumId - album's id used for filtering.
     * @return the filtered list of songs.
     */
    List<Song> findAllByAlbumId(AlbumId albumId);

    /**
     * Method used for searching songs.
     *
     * @param searchTerm - search term used for filtering.
     * @return the filtered list of songs.
     */
    List<Song> findAllBySongNameIgnoreCase(String searchTerm);
}