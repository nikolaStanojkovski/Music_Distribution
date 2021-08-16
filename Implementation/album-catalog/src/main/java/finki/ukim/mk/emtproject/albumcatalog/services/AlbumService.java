package finki.ukim.mk.emtproject.albumcatalog.services;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Album;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.AlbumId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.ArtistId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.dto.AlbumDto;
import finki.ukim.mk.emtproject.albumcatalog.services.form.AlbumForm;
import finki.ukim.mk.emtproject.albumcatalog.services.form.AlbumPublishForm;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface AlbumService {

    List<AlbumDto> findAll();
    Optional<Album> findById(AlbumId id);
    Optional<Album> createAlbum(AlbumForm form);

}
