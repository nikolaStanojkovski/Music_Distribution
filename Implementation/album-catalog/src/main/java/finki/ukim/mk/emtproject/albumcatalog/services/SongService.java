package finki.ukim.mk.emtproject.albumcatalog.services;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Song;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.SongId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.request.SongRequest;

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
