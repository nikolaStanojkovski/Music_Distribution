package com.musicdistribution.albumcatalog.services;

import com.musicdistribution.albumcatalog.domain.models.entity.Album;
import com.musicdistribution.albumcatalog.domain.models.entity.AlbumId;
import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;
import com.musicdistribution.albumcatalog.domain.models.request.AlbumShortTransactionRequest;
import com.musicdistribution.albumcatalog.domain.models.request.AlbumTransactionRequest;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import org.springframework.data.domain.Page;
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
    List<Album> findAll();

    /**
     * Method for getting a page of published albums from the database.
     *
     * @return a page of the filtered albums.
     */
    Page<Album> findAllPageable();

    /**
     * Method for getting all the albums by a particular artist from the database.
     *
     * @param artistId - artist's id
     * @return a list of the filtered albums.
     */
    List<Album> findAllByArtist(ArtistId artistId);

    /**
     * Method for getting all the albums filtered by genre from the database.
     *
     * @param genre - album's genre used for filtering
     * @return a list of the filtered albums.
     */
    List<Album> findAllByGenre(Genre genre);

    /**
     * Method for searching albums.
     *
     * @param searchTerm - the term used for filtering
     * @return a list of the filtered albums.
     */
    List<Album> searchAlbums(String searchTerm);

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
    Optional<Album> publishAlbum(AlbumTransactionRequest album, MultipartFile cover,
                                 String username, List<String> songIds);

    /**
     * Method for raising an existing album tier in the database.
     *
     * @param album - album's dto object containing album's information.
     * @param id    - album's id.
     * @return an optional with the updated album.
     */
    Optional<Album> raiseTierAlbum(AlbumShortTransactionRequest album, AlbumId id);
}
