package com.musicdistribution.albumpublishing.services;

import com.musicdistribution.albumpublishing.domain.models.entity.PublishedAlbum;
import com.musicdistribution.albumpublishing.domain.valueobjects.ArtistId;

import java.util.List;

/**
 * Service for the implementation of the main specific business logic for the published albums.
 */
public interface PublishedAlbumService {

    /**
     * Method for getting all the published albums from the database.
     *
     * @return a list of the published albums.
     */
    List<PublishedAlbum> findAll();

    /**
     * Method for getting all the published albums from the database for specific artist.
     *
     * @param artistId - artist's id
     * @return a list of the published albums.
     */
    List<PublishedAlbum> findAllByArtist(ArtistId artistId);
}
