package com.musicdistribution.albumcatalog.services.implementation;

import com.musicdistribution.albumcatalog.domain.exceptions.FileStorageException;
import com.musicdistribution.albumcatalog.domain.models.entity.*;
import com.musicdistribution.albumcatalog.domain.models.request.SongRequest;
import com.musicdistribution.albumcatalog.domain.repository.AlbumRepository;
import com.musicdistribution.albumcatalog.domain.repository.ArtistRepository;
import com.musicdistribution.albumcatalog.domain.repository.SongRepository;
import com.musicdistribution.albumcatalog.domain.services.IFileSystemStorage;
import com.musicdistribution.albumcatalog.domain.valueobjects.SongLength;
import com.musicdistribution.albumcatalog.services.SongService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    private final IFileSystemStorage fileSystemStorage;

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
    public Optional<Song> createSong(SongRequest form, MultipartFile file, String username) {
        Optional<Song> song = Optional.empty();
        Optional<Artist> artist = artistRepository.findByArtistUserInfo_Username(username);

        if (artist.isPresent()) {
            song = Optional.of(Song.build(form.getSongName(), artist.get(), SongLength.build(form.getLengthInSeconds()), form.getSongGenre()));
            song = Optional.of(songRepository.save(song.get()));
            saveSong(song.get(), file);

            song.ifPresent(s -> {
                artist.get().addSongToArtist(s);
                artistRepository.save(artist.get());
            });
        }

        return song;
    }

    private void saveSong(Song song, MultipartFile file) {
        String songId = song.getId().getId();
        try {
            String fileName = String.format("%s.mp3", songId);
            fileSystemStorage.saveFile(file, fileName);
        } catch (Exception exception) {
            throw new FileStorageException("Could not save the compressed version of the song with id " + songId);
        }
    }

    @Override
    public Optional<Song> publishSong(SongRequest songRequest) {
//        Optional<Song> newSong = createSong(songRequest);
//        if (newSong.isPresent() && songRequest.getIsASingle()) {
//            Song publishedSong = Song.publishSong(newSong.get());
//            return Optional.of(songRepository.save(publishedSong));
//        }
//        return newSong;
        return Optional.empty();
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
