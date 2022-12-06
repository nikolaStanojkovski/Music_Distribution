package com.musicdistribution.streamingservice.service;

import com.musicdistribution.streamingservice.domain.model.entity.core.Song;
import com.musicdistribution.streamingservice.domain.model.entity.id.SongId;
import com.musicdistribution.streamingservice.domain.model.request.core.SongRequest;
import com.musicdistribution.streamingservice.domain.model.request.SongShortTransactionRequest;
import com.musicdistribution.streamingservice.domain.model.request.SongTransactionRequest;
import com.musicdistribution.sharedkernel.domain.response.SearchResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * A service that contains the specific business logic for song entities.
 */
public interface SongService {

    /**
     * Method used for fetching a page of songs from the database.
     *
     * @param pageable              - the wrapper object containing pagination data.
     * @param shouldFilterPublished - a flag determining whether a filtering should be done by publishing status.
     * @return a page of the songs.
     */
    Page<Song> findAll(Pageable pageable, Boolean shouldFilterPublished);

    /**
     * Method used for reading the total number of entities from the database.
     *
     * @param shouldFilterPublished - a flag determining whether a filtering should be done by publishing status.
     * @return the total number of songs from the database.
     */
    Long findSize(Boolean shouldFilterPublished);

    /**
     * Method used for searching songs.
     *
     * @param searchParams          - the object parameters by which the filtering is to be done.
     * @param shouldFilterPublished - a flag determining whether a filtering should be done by publishing status.
     * @param searchTerm            - the search term by which the filtering is to be done.
     * @param pageable              - the wrapper object containing pagination data.
     * @return a list of the filtered songs.
     */
    SearchResultResponse<Song> search(List<String> searchParams, Boolean shouldFilterPublished,
                                      String searchTerm, Pageable pageable);

    /**
     * Method used for fetching a song with the specified ID.
     *
     * @param id - song's ID.
     * @return an optional with the found song.
     */
    Optional<Song> findById(SongId id);

    /**
     * Method used for creating a new song in the database.
     *
     * @param song     - song's data object containing the new song's information.
     * @param username - the username of the user which is creating the song.
     * @return an optional with the created song.
     */
    Optional<Song> create(SongRequest song, MultipartFile file, String username);

    /**
     * Method used for publishing a new song.
     *
     * @param songRequest - song's data object containing the new song's information.
     * @param cover       - song's cover picture.
     * @param username    - the username of the artist who is publishing the song.
     * @param id          - the ID of the song which is to be published.
     * @return an optional with the published song.
     */
    Optional<Song> publish(SongTransactionRequest songRequest, MultipartFile cover, String username, String id);

    /**
     * Method used for raising an existing song's tier.
     *
     * @param song - song's data object containing the required song information.
     * @param id   - song's id.
     * @return an optional with the updated song.
     */
    Optional<Song> raiseTier(SongShortTransactionRequest song, SongId id);
}
