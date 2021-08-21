package finki.ukim.mk.emtproject.albumpublishing.services;

import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbum;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbumId;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.dto.PublishedAlbumDto;
import finki.ukim.mk.emtproject.albumpublishing.services.form.PublishedAlbumForm;

import java.util.List;

/**
 * PublishedAlbumService - Service for the implementation of the main specific business logic for the published albums
 */
public interface PublishedAlbumService {

    List<PublishedAlbumDto> findAll();
    PublishedAlbum findById(PublishedAlbumId id);
}
