package finki.ukim.mk.emtproject.albumcatalog.services;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Album;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.AlbumId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.Artist;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.ArtistId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.dto.ArtistDto;
import finki.ukim.mk.emtproject.albumcatalog.services.form.AlbumForm;
import finki.ukim.mk.emtproject.albumcatalog.services.form.ArtistForm;
import finki.ukim.mk.emtproject.albumcatalog.services.form.ArtistLoginForm;

import java.util.List;
import java.util.Optional;

public interface ArtistService {

    List<ArtistDto> findAll();
    Optional<Artist> findById(ArtistId id);
    Optional<Artist> createArtist(ArtistForm form);

    ArtistDto loginArtist(ArtistLoginForm artistLoginForm);
    ArtistDto registerArtist(ArtistForm artistForm);

}
