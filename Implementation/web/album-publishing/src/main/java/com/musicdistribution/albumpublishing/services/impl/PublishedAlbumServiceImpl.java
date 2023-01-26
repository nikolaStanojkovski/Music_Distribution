package com.musicdistribution.albumpublishing.services.impl;

import com.musicdistribution.albumpublishing.domain.models.entity.PublishedAlbum;
import com.musicdistribution.albumpublishing.domain.repository.PublishedAlbumRepository;
import com.musicdistribution.albumpublishing.domain.valueobjects.ArtistId;
import com.musicdistribution.albumpublishing.services.PublishedAlbumService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * PublishedAlbumServiceImpl - Service for the implementation of the main specific business logic for the published albums
 */
@Service
@Transactional
@AllArgsConstructor
public class PublishedAlbumServiceImpl implements PublishedAlbumService {

    private final PublishedAlbumRepository publishedAlbumRepository;

    @Override
    public List<PublishedAlbum> findAll() {
        return publishedAlbumRepository.findAll();
    }

    @Override
    public List<PublishedAlbum> findAllByArtist(ArtistId artistId) {
        return publishedAlbumRepository.findByArtistId(artistId);
    }
}
