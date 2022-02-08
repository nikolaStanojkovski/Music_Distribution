package com.musicdistribution.albumpublishing.services;

import com.musicdistribution.albumpublishing.domain.models.entity.MusicDistributor;
import com.musicdistribution.albumpublishing.domain.models.entity.PublishedAlbum;
import com.musicdistribution.albumpublishing.domain.models.entity.PublishedAlbumId;
import com.musicdistribution.albumpublishing.domain.models.request.MusicDistributorRequest;
import com.musicdistribution.albumpublishing.domain.models.request.PublishedAlbumRequest;
import com.musicdistribution.albumpublishing.domain.valueobjects.AlbumId;

import java.util.List;
import java.util.Optional;

/**
 * Service for the implementation of the main specific business logic for the music distributors.
 */
public interface MusicDistributorService {

    /**
     * Method for getting all the music distributors from the database.
     *
     * @return a list of the music distributors.
     */
    List<MusicDistributor> findAll();

    /**
     * Method for creating a new music distributor in the database.
     *
     * @param musicDistributor - distributor's dto object containing new music distributor's information.
     */
    void createDistributor(MusicDistributorRequest musicDistributor);

    /**
     * Method used for publishing an album.
     *
     * @param publishedAlbumRequest - published album's information.
     * @return an optional with the published album.
     */
    Optional<PublishedAlbum> publishAlbum(PublishedAlbumRequest publishedAlbumRequest);

    /**
     * Method used for making an album unpublished.
     *
     * @param publishedAlbumId - published album's id.
     * @return an optional with the unpublished album.
     */
    Optional<PublishedAlbum> unPublishAlbum(PublishedAlbumId publishedAlbumId);

    /**
     * Method used for making an album unpublished.
     *
     * @param albumId - album's id.
     * @return an optional with the unpublished album.
     */
    Optional<PublishedAlbum> unPublishAlbumByAlbumId(AlbumId albumId);

    /**
     * Method used for raising an album's tier.
     *
     * @param publishedAlbumResponse - published album's information.
     * @return an optional with the unpublished album.
     */
    Optional<PublishedAlbum> raiseAlbumTier(PublishedAlbumRequest publishedAlbumResponse);
}
