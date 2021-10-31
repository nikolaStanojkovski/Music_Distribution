package finki.ukim.mk.emtproject.albumcatalog.services.implementation;

import finki.ukim.mk.emtproject.albumcatalog.domain.exceptions.AlbumNotFoundException;
import finki.ukim.mk.emtproject.albumcatalog.domain.exceptions.ArtistNotFoundException;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.*;
import finki.ukim.mk.emtproject.albumcatalog.domain.repository.AlbumRepository;
import finki.ukim.mk.emtproject.albumcatalog.domain.repository.ArtistRepository;
import finki.ukim.mk.emtproject.albumcatalog.domain.repository.SongRepository;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.SongLength;
import finki.ukim.mk.emtproject.albumcatalog.services.SongService;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.request.SongRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of the song service.
 */
@Service
@Transactional
@AllArgsConstructor
public class SongServiceImpl implements SongService {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    @Override
    public List<Song> findAll() {
        return songRepository.findAll();
    }

    @Override
    public Optional<Song> findById(SongId id) {
        return songRepository.findById(id);
    }

    @Override
    public Optional<Song> createSong(SongRequest form) {
        Optional<Song> song = Optional.empty();

        if (Objects.nonNull(form.getCreatorId())) {
            ArtistId creatorId = ArtistId.of(form.getCreatorId());
            Artist creator = artistRepository.findById(creatorId).orElseThrow(() -> new ArtistNotFoundException(creatorId));

            if (Objects.nonNull(form.getAlbumId())) {
                AlbumId albumId = AlbumId.of(form.getAlbumId());
                Album album = albumRepository.findById(albumId).orElseThrow(() -> new AlbumNotFoundException(albumId));
                song = Optional.of(Song.build(form.getSongName(), creator, album, SongLength.build(form.getLengthInSeconds())));
                song.ifPresent(album::addSong);
                albumRepository.save(album);
            } else {
                song = Optional.of(Song.build(form.getSongName(), creator, null, SongLength.build(form.getLengthInSeconds())));
            }

            song.ifPresent(s -> {
                creator.addSongToArtist(s);
                songRepository.save(s);
                artistRepository.save(creator);
            });
        }

        return song;
    }
}
