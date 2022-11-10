package com.musicdistribution.albumcatalog.services;

import com.musicdistribution.albumcatalog.domain.models.entity.Song;
import com.musicdistribution.albumcatalog.domain.models.entity.SongId;
import com.musicdistribution.albumcatalog.domain.models.request.SongRequest;
import com.musicdistribution.albumcatalog.domain.models.request.SongShortTransactionRequest;
import com.musicdistribution.albumcatalog.domain.models.request.SongTransactionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Service for the implementation of the main specific business logic for the songs.
 */
public interface SongService {

    /**
     * Method for getting all the songs from the database.
     *
     * @return a list of the songs.
     */
    Page<Song> findAll(Pageable pageable);

    /**
     * Method for searching songs.
     *
     * @param searchParams - the object parameters by which a filtering will be done
     * @param searchTerm   - the search term by which the filtering will be done
     * @param pageable     - the wrapper for paging/sorting/filtering
     * @return a list of the filtered songs.
     */
    Page<Song> search(List<String> searchParams, String searchTerm, Pageable pageable);

    /**
     * Method for reading the total number of entities in the database.
     *
     * @return the total number of songs in the database.
     */
    Long findTotalSize();

    /**
     * Method for getting a song from the database.
     *
     * @param id - song's id.
     * @return an optional with the found song.
     */
    Optional<Song> findById(SongId id);

    /**
     * Method for creating a new song in the database.
     *
     * @param song     - song's dto object containing new song's information.
     * @param username - the username of the user which is creating the song.
     * @return an optional with the found song.
     */
    Optional<Song> create(SongRequest song, MultipartFile file, String username);

    /**
     * Method for publishing a song.
     *
     * @param songRequest - song's dto object containing new published song's information.
     * @param cover       - the cover picture of the selected song.
     * @param username    - the username of the user which is publishing the song.
     * @param id          - the id of the selected song.
     * @return an optional with the published song.
     */
    Optional<Song> publish(SongTransactionRequest songRequest, MultipartFile cover, String username, String id);

    /**
     * Method for raising an existing song tier in the database.
     *
     * @param song - song's dto object containing song's information.
     * @param id   - song's id.
     * @return an optional with the updated song.
     */
    Optional<Song> raiseTier(SongShortTransactionRequest song, SongId id);
}
