package finki.ukim.mk.emtproject.albumcatalog.services;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Album;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.AlbumId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.request.AlbumRequest;

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
     * Method for getting an album from the database.
     *
     * @param id - album's id.
     * @return an optional with the found album.
     */
    Optional<Album> findById(AlbumId id);

    /**
     * Method for creating a new album in the database.
     *
     * @param album - album's dto object containing new album's information.
     * @return an optional with the found album.
     */
    Optional<Album> createAlbum(AlbumRequest album);

    /**
     * Method for making an album published.
     *
     * @param id - album's id.
     */
    void albumPublished(AlbumId id);

    /**
     * Method for making an album unpublished.
     *
     * @param id - album's id.
     */
    void albumUnpublished(AlbumId id);
}
