package com.musicdistribution.storageservice.service;

import com.musicdistribution.storageservice.domain.model.SearchResult;
import com.musicdistribution.storageservice.domain.model.entity.Artist;
import com.musicdistribution.storageservice.domain.model.entity.ArtistId;
import com.musicdistribution.storageservice.domain.model.request.ArtistRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Service for the implementation of the main specific business logic for the artists.
 */
public interface ArtistService {

    /**
     * Method for getting all the artists from the database.
     *
     * @param pageable - the wrapper for paging/sorting/filtering
     * @return a list of the artists.
     */
    Page<Artist> findAll(Pageable pageable);

    /**
     * Method for reading the total number of entities in the database.
     *
     * @return the total number of artists in the database.
     */
    Long findTotalSize();

    /**
     * Method for searching artists.
     *
     * @param searchParams - the object parameters by which a filtering will be done
     * @param searchTerm   - the search term by which the filtering will be done
     * @param pageable     - the wrapper for paging/sorting/filtering
     * @return a search result of the filtered artists.
     */
    SearchResult<Artist> search(List<String> searchParams, String searchTerm, Pageable pageable);



    /**
     * Method for getting an artist from the database.
     *
     * @param id - artist's id.
     * @return an optional with the found artist.
     */
    Optional<Artist> findById(ArtistId id);

    /**
     * Method for authenticating an existing artist from the database.
     *
     * @param artist - artist's login form object containing artist's information needed for authentication.
     * @return the authenticated artist.
     */
    Optional<Artist> loginArtist(ArtistRequest artist);

    /**
     * Method for registering a new artist in the database.
     *
     * @param artist         - artist's login form object containing artist's information needed for authentication.
     * @param profilePicture - the profile picture of the new artist to be registered.
     * @return the new registered artist.
     */
    Optional<Artist> registerArtist(MultipartFile profilePicture, ArtistRequest artist);
}
