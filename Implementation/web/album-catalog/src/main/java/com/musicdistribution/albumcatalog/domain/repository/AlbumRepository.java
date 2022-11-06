package com.musicdistribution.albumcatalog.domain.repository;

import com.musicdistribution.albumcatalog.domain.models.entity.Album;
import com.musicdistribution.albumcatalog.domain.models.entity.AlbumId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for an album.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, AlbumId> {

    /**
     * Method used for searching albums.
     *
     * @param searchParams - the parameters by which a search will be made.
     * @param searchTerm   - the search term that will be used for filtering.
     * @return a list of the found albums that match the search criteria.
     */
    @Query("SELECT a FROM Album a WHERE CONCAT(:params) LIKE %:term%")
    Page<Album> search(@Param("params") String searchParams,
                       @Param("term") String searchTerm,
                       Pageable pageable);
}
