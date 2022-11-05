package com.musicdistribution.albumcatalog.services;

import com.musicdistribution.albumcatalog.domain.models.entity.*;
import com.musicdistribution.albumcatalog.domain.models.request.AlbumShortTransactionRequest;
import com.musicdistribution.albumcatalog.domain.models.request.SongRequest;
import com.musicdistribution.albumcatalog.domain.models.request.SongShortTransactionRequest;
import com.musicdistribution.albumcatalog.domain.models.request.SongTransactionRequest;
import org.springframework.data.domain.Page;
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
    List<Song> findAll();

    /**
     * Method for getting all the songs with the specified artist id from the database.
     *
     * @return a list of the songs.
     */
    List<Song> findAllByArtist(ArtistId artistId);

    /**
     * Method for getting all the songs with the specified album id from the database.
     *
     * @return a list of the songs.
     */
    List<Song> findAllByAlbum(AlbumId artistId);

    /**
     * Method for a page of songs from the database.
     *
     * @return a page of the songs.
     */
    Page<Song> findAllPageable();

    /**
     * Method for searching songs.
     *
     * @param searchTerm - the term used for filtering
     * @return a list of the filtered songs.
     */
    List<Song> searchSongs(String searchTerm);

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
    Optional<Song> createSong(SongRequest song, MultipartFile file, String username);

    /**
     * Method for publishing a song.
     *
     * @param songRequest - song's dto object containing new published song's information.
     * @param cover       - the cover picture of the selected song.
     * @param username    - the username of the user which is publishing the song.
     * @param id          - the id of the selected song.
     * @return an optional with the published song.
     */
    Optional<Song> publishSong(SongTransactionRequest songRequest, MultipartFile cover, String username, String id);

    /**
     * Method for deleting a song.
     *
     * @param id - the id of the song to be deleted.
     * @return an optional with the song that was deleted.
     */
    Optional<Song> deleteSong(SongId id);

    /**
     * Method for raising an existing song tier in the database.
     *
     * @param song - song's dto object containing song's information.
     * @param id    - song's id.
     * @return an optional with the updated song.
     */
    Optional<Song> raiseTierSong(SongShortTransactionRequest song, SongId id);
}
