package finki.ukim.mk.emtproject.albumpublishing.services;

import finki.ukim.mk.emtproject.albumpublishing.domain.models.MusicDistributor;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.MusicDistributorId;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbum;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbumId;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.dto.MusicDistributorDto;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.dto.PublishedAlbumDto;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.Album;
import finki.ukim.mk.emtproject.albumpublishing.services.form.AlbumPublishForm;
import finki.ukim.mk.emtproject.albumpublishing.services.form.MusicDistributorForm;
import finki.ukim.mk.emtproject.albumpublishing.services.form.PublishedAlbumForm;

import java.util.List;
import java.util.Optional;

/**
 * MusicDistributorService - Service for the implementation of the main specific business logic for the music distributors
 */
public interface MusicDistributorService {

    List<MusicDistributorDto> findAll();
    MusicDistributor findById(MusicDistributorId id);
    MusicDistributor createDistributor(MusicDistributorForm form);

    Optional<PublishedAlbum> publishAlbum(PublishedAlbumDto publishedAlbumDto);
    Optional<PublishedAlbum> unpublishAlbum(PublishedAlbumId publishedAlbumId);
    Optional<PublishedAlbum> raiseAlbumTier(PublishedAlbumDto form);

    PublishedAlbum subscribeAlbum(PublishedAlbumDto form);
    PublishedAlbum unsubscribeAlbum(PublishedAlbumId publishedAlbumId);
}
