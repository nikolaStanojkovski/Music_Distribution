package com.musicdistribution.albumcatalog.services.implementation;

import com.musicdistribution.albumcatalog.domain.exceptions.ArtistNotFoundException;
import com.musicdistribution.albumcatalog.domain.models.entity.*;
import com.musicdistribution.albumcatalog.domain.models.request.SongRequest;
import com.musicdistribution.albumcatalog.domain.repository.AlbumRepository;
import com.musicdistribution.albumcatalog.domain.repository.ArtistRepository;
import com.musicdistribution.albumcatalog.domain.repository.SongRepository;
import com.musicdistribution.albumcatalog.domain.valueobjects.SongLength;
import com.musicdistribution.albumcatalog.services.SongService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public List<Song> findAllByArtist(ArtistId artistId) {
        return songRepository.findAllByCreatorId(artistId);
    }

    @Override
    public List<Song> findAllByAlbum(AlbumId albumId) {
        return songRepository.findAllByAlbumId(albumId);
    }

    @Override
    public Page<Song> findAllPageable() {
        return songRepository.findAll(PageRequest.of(0, 10));
    }

    @Override
    public List<Song> searchSongs(String searchTerm) {
        return songRepository.findAllBySongNameIgnoreCase(searchTerm);
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
                Album album = albumRepository.findById(albumId).orElse(null);
                song = Optional.of(Song.build(form.getSongName(), creator, album, SongLength.build(form.getLengthInSeconds())));
                songRepository.save(song.get());
                if (album != null) {
                    song.ifPresent(album::addSong);
                    albumRepository.save(album);
                }
            } else {
                song = Optional.of(Song.build(form.getSongName(), creator, null, SongLength.build(form.getLengthInSeconds())));
                songRepository.save(song.get());
            }

            song.ifPresent(s -> {
                creator.addSongToArtist(s);
                artistRepository.save(creator);
            });
        }

        return song;
    }

    @Override
    public Optional<Song> publishSong(SongRequest songRequest) {
        Optional<Song> newSong = createSong(songRequest);
        if (newSong.isPresent() && songRequest.getIsASingle()) {
            Song publishedSong = Song.publishSong(newSong.get());
            return Optional.of(songRepository.save(publishedSong));
        }
        return newSong;
    }

    @Override
    @Transactional
    public Optional<Song> deleteSong(String id) {
        Optional<Song> song = findById(SongId.of(id));
        song.ifPresent(s -> {
            s.getCreator().removeSongFromArtist(s);
            if (s.getAlbum() != null) {
                s.getAlbum().removeSong(s);
                albumRepository.save(s.getAlbum());
            }
            songRepository.delete(s);
            artistRepository.save(s.getCreator());
        });
        return song;
    }
}
