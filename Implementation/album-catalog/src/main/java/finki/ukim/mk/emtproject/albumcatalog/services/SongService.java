package finki.ukim.mk.emtproject.albumcatalog.services;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Artist;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.ArtistId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.Song;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.SongId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.dto.SongDto;
import finki.ukim.mk.emtproject.albumcatalog.services.form.ArtistForm;
import finki.ukim.mk.emtproject.albumcatalog.services.form.SongForm;

import java.util.List;
import java.util.Optional;

/**
 * SongService - Service for the implementation of the main specific business logic for the songs
 */
public interface SongService {

    List<SongDto> findAll();
    Optional<Song> findById(SongId id);
    Optional<Song> createSong(SongForm form);

}
