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

    /**
     * Method used to filters songs by their publishing status.
     *
     * @param isPublished - a flag determining whether a filtering should be done by the publishing status.
     * @param pageable    - the wrapper object containing pagination data.
     * @return a page of the filtered songs.
     */
    Page<Song> findByIsPublished(Boolean isPublished, Pageable pageable);

    /**
     * Method used for reading the total number of entities from the database.
     *
     * @param isPublished - a flag determining whether a filtering should be done by the publishing status.
     * @return the total number of albums from the database.
     */
    Long countByIsPublished(Boolean isPublished);
}