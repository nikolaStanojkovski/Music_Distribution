package com.musicdistribution.albumcatalog.services;

import com.musicdistribution.albumcatalog.domain.models.entity.Artist;
import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;
import com.musicdistribution.albumcatalog.domain.models.request.ArtistRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service for the implementation of the main specific business logic for the artists.
 */
public interface ArtistService {

    /**
     * Method for getting all the artists from the database.
     *
     * @return a list of the artists.
     */
    List<Artist> findAll();

    /**
     * Method for a page of artists from the database.
     *
     * @return a page of the artists.
     */
    Page<Artist> findAllPageable();

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
     * @param artist - artist's login form object containing artist's information needed for authentication.
     * @return the new registered artist.
     */
    Optional<Artist> registerArtist(ArtistRequest artist);
}
