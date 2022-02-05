package com.musicdistribution.albumcatalog.services;

import com.musicdistribution.albumcatalog.domain.models.entity.AlbumId;
import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;
import com.musicdistribution.albumcatalog.domain.models.entity.Song;
import com.musicdistribution.albumcatalog.domain.models.entity.SongId;
import com.musicdistribution.albumcatalog.domain.models.request.SongRequest;
import org.springframework.data.domain.Page;

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
     * Method for getting a song from the database.
     *
     * @param id - song's id.
     * @return an optional with the found song.
     */
    Optional<Song> findById(SongId id);

    /**
     * Method for creating a new song in the database.
     *
     * @param song - artist's dto object containing new artist's information.
     * @return an optional with the found song.
     */
    Optional<Song> createSong(SongRequest song);
}
