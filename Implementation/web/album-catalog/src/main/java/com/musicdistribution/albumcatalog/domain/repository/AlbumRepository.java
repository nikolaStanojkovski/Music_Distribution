package com.musicdistribution.albumcatalog.domain.repository;

import com.musicdistribution.albumcatalog.domain.models.entity.Album;
import com.musicdistribution.albumcatalog.domain.models.entity.AlbumId;
import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for an album.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, AlbumId> {

    /**
     * Method for finding all albums by artist's id.
     *
     * @param creator_id - artist's id by which the filtering will be done.
     * @return the filtered list of albums.
     */
    List<Album> findAllByCreatorId(ArtistId creator_id);

    /**
     * Method for finding all albums by genre.
     *
     * @param genre - album's genre by which the filtering will be done.
     * @return the filtered list of albums.
     */
    List<Album> findAllByGenre(Genre genre);

    /**
     * Method for searching albums.
     *
     * @param searchTerm - the search term by which the filtering will be done.
     * @return the filtered list of albums.
     */
    List<Album> findAllByAlbumNameIgnoreCase(String searchTerm);
}
