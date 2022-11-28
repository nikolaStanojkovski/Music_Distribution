package com.musicdistribution.streamingservice.service;

import com.musicdistribution.streamingservice.domain.model.entity.Artist;
import com.musicdistribution.streamingservice.domain.model.entity.id.ArtistId;
import com.musicdistribution.streamingservice.domain.model.request.ArtistRequest;
import com.musicdistribution.streamingservice.domain.model.request.AuthRequest;
import com.musicdistribution.streamingservice.domain.model.response.SearchResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * A service that contains the specific business logic for artist entities.
 */
public interface ArtistService {

    /**
     * Method used for fetching a page of artists from the database.
     *
     * @param pageable - the wrapper object containing pagination data.
     * @return a page of the artists.
     */
    Page<Artist> findAll(Pageable pageable);

    /**
     * Method used for reading the total number of entities from the database.
     *
     * @return the total number of artists from the database.
     */
    Long findTotalSize();

    /**
     * Method used for searching artists.
     *
     * @param searchParams - the object parameters by which the filtering is to be done.
     * @param searchTerm   - the search term by which the filtering is to be done.
     * @param pageable     - the wrapper object containing pagination data.
     * @return a list of the filtered artists.
     */
    SearchResultResponse<Artist> search(List<String> searchParams, String searchTerm, Pageable pageable);

    /**
     * Method used for fetching an artist with the specified ID.
     *
     * @param id - artist's ID.
     * @return an optional with the found artist.
     */
    Optional<Artist> findById(ArtistId id);

    /**
     * Method used for authenticating an existing artist from the database.
     *
     * @param artist - a wrapper object containing artist's information needed for authentication.
     * @return an optional with the authenticated artist.
     */
    Optional<Artist> login(AuthRequest artist);

    /**
     * Method used for registering a new artist in the database.
     *
     * @param profilePicture - the profile picture of the artist to be registered.
     * @param artist         - a wrapper object containing artist's information needed for registration.
     * @return an optional with the registered artist.
     */
    Optional<Artist> register(MultipartFile profilePicture, ArtistRequest artist);
}
