package com.musicdistribution.albumcatalog.services.implementation;

import com.musicdistribution.albumcatalog.domain.exceptions.AlbumNotFoundException;
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
import org.springframework.data.domain.Pageable;
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
    public Page<Song> findAllPageable() {
        long totalQuantity = songRepository.count();
        int index = (int)(Math.random() * totalQuantity);
        Pageable pageable = (totalQuantity > 10) ? PageRequest.of(index, 10) : PageRequest.of(0, 10);
        return songRepository.findAll(pageable);
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
