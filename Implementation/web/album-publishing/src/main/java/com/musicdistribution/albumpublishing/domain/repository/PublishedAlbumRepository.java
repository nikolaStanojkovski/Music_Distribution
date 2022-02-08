package com.musicdistribution.albumpublishing.domain.repository;

import com.musicdistribution.albumpublishing.domain.models.entity.PublishedAlbum;
import com.musicdistribution.albumpublishing.domain.models.entity.PublishedAlbumId;
import com.musicdistribution.albumpublishing.domain.valueobjects.AlbumId;
import com.musicdistribution.albumpublishing.domain.valueobjects.ArtistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for a published album.
 */
@Repository
public interface PublishedAlbumRepository extends JpaRepository<PublishedAlbum, PublishedAlbumId> {

    /**
     * Method for finding a published album by id.
     *
     * @param albumId - album's id
     * @return an optional with the published album.
     */
    Optional<PublishedAlbum> findByAlbumId(AlbumId albumId);

    /**
     * Method for finding the published album list by a particular artist.
     *
     * @param artistId - artist's id
     * @return a list of filtered published albums.
     */
    List<PublishedAlbum> findByArtistId(ArtistId artistId);
}
