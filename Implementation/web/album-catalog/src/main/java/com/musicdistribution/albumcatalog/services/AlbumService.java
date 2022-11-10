package com.musicdistribution.albumcatalog.services;

import com.musicdistribution.albumcatalog.domain.models.entity.Album;
import com.musicdistribution.albumcatalog.domain.models.entity.AlbumId;
import com.musicdistribution.albumcatalog.domain.models.request.AlbumShortTransactionRequest;
import com.musicdistribution.albumcatalog.domain.models.request.AlbumTransactionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Service for the implementation of the main specific business logic for the albums.
 */
public interface AlbumService {

    /**
     * Method for getting all the albums from the database.
     *
     * @return a list of the albums.
     */
    Page<Album> findAll(Pageable pageable);

    /**
     * Method for searching albums.
     *
     * @param searchParams - the object parameters by which a filtering will be done
     * @param searchTerm   - the search term by which the filtering will be done
     * @param pageable     - the wrapper for paging/sorting/filtering
     * @return a list of the filtered albums.
     */
    Page<Album> search(List<String> searchParams, String searchTerm, Pageable pageable);

    /**
     * Method for reading the total number of entities in the database.
     *
     * @return the total number of albums in the database.
     */
    Long findTotalSize();

    /**
     * Method for getting an album from the database.
     *
     * @param id - album's id.
     * @return an optional with the found album.
     */
    Optional<Album> findById(AlbumId id);

    /**
     * Method for creating a new album in the database.
     *
     * @param album    - album's dto object containing new album's information.
     * @param cover    - the album cover picture.
     * @param username - the username of the artist who is publishing the album.
     * @param songIds  - the IDs of the songs from which the album is consisted of.
     * @return an optional with the published album.
     */
    Optional<Album> publish(AlbumTransactionRequest album, MultipartFile cover,
                            String username, List<String> songIds);

    /**
     * Method for raising an existing album tier in the database.
     *
     * @param album - album's dto object containing album's information.
     * @param id    - album's id.
     * @return an optional with the updated album.
     */
    Optional<Album> raiseTier(AlbumShortTransactionRequest album, AlbumId id);
}
