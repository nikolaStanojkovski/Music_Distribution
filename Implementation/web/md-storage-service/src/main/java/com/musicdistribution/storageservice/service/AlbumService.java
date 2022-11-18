package com.musicdistribution.storageservice.service;

import com.musicdistribution.storageservice.domain.model.entity.Album;
import com.musicdistribution.storageservice.domain.model.entity.AlbumId;
import com.musicdistribution.storageservice.domain.model.request.AlbumShortTransactionRequest;
import com.musicdistribution.storageservice.domain.model.request.AlbumTransactionRequest;
import com.musicdistribution.storageservice.domain.model.response.SearchResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * A service that contains the specific business logic for album entities.
 */
public interface AlbumService {

    /**
     * Method used for fetching a page of albums from the database.
     *
     * @param pageable - the wrapper object containing pagination data.
     * @return a page with albums.
     */
    Page<Album> findAll(Pageable pageable);

    /**
     * Method used for reading the total number of entities from the database.
     *
     * @return the total number of albums from the database.
     */
    Long findTotalSize();

    /**
     * Method used for searching albums.
     *
     * @param searchParams - the object parameters by which the filtering is to be done.
     * @param searchTerm   - the search term by which the filtering is to be done.
     * @param pageable     - the wrapper object containing pagination data.
     * @return a list of the filtered albums.
     */
    SearchResultResponse<Album> search(List<String> searchParams, String searchTerm, Pageable pageable);

    /**
     * Method used for fetching an album with the specified ID.
     *
     * @param id - album's ID.
     * @return an optional with the found album.
     */
    Optional<Album> findById(AlbumId id);

    /**
     * Method used for publishing a new album.
     *
     * @param album    - album's data object containing the new album's information.
     * @param cover    - album's cover picture.
     * @param username - the username of the artist who is publishing the album.
     * @param songIds  - the IDs of the songs from which the album is consisted of.
     * @return an optional with the published album.
     */
    Optional<Album> publish(AlbumTransactionRequest album, MultipartFile cover,
                            String username, List<String> songIds);

    /**
     * Method used for raising an existing album's tier.
     *
     * @param album - album's data object containing album's transaction information.
     * @param id    - album's id.
     * @return an optional with the updated album.
     */
    Optional<Album> raiseTier(AlbumShortTransactionRequest album, AlbumId id);
}
