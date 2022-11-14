package com.musicdistribution.storageservice.service.implementation;

import com.musicdistribution.storageservice.domain.exception.FileStorageException;
import com.musicdistribution.storageservice.domain.model.SearchResult;
import com.musicdistribution.storageservice.domain.model.entity.Artist;
import com.musicdistribution.storageservice.domain.model.entity.Song;
import com.musicdistribution.storageservice.domain.model.entity.SongId;
import com.musicdistribution.storageservice.domain.model.enums.FileLocationType;
import com.musicdistribution.storageservice.domain.model.request.SongRequest;
import com.musicdistribution.storageservice.domain.model.request.SongShortTransactionRequest;
import com.musicdistribution.storageservice.domain.model.request.SongTransactionRequest;
import com.musicdistribution.storageservice.domain.repository.ArtistRepository;
import com.musicdistribution.storageservice.domain.repository.SearchRepository;
import com.musicdistribution.storageservice.domain.repository.SongRepository;
import com.musicdistribution.storageservice.domain.service.IFileSystemStorage;
import com.musicdistribution.storageservice.domain.valueobject.PaymentInfo;
import com.musicdistribution.storageservice.domain.valueobject.SongLength;
import com.musicdistribution.storageservice.service.SongService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final SearchRepository<Song> searchRepository;

    private final IFileSystemStorage fileSystemStorage;

    @Override
    public Page<Song> findAll(Pageable pageable) {
        return songRepository.findAll(pageable);
    }

    @Override
    public Long findTotalSize() {
        return songRepository.count();
    }

    @Override
    public SearchResult<Song> search(List<String> searchParams, String searchTerm, Pageable pageable) {
        return searchRepository.search(searchParams, searchTerm, pageable);
    }

    @Override
    public Optional<Song> findById(SongId id) {
        return songRepository.findById(id);
    }

    @Override
    public Optional<Song> create(SongRequest form, MultipartFile file, String username) {
        Optional<Song> song = Optional.empty();
        Optional<Artist> artist = artistRepository.findByArtistUserInfo_Username(username);

        if (artist.isPresent()) {
            song = Optional.of(Song.build(form.getSongName(), artist.get(), SongLength.build(form.getLengthInSeconds()), form.getSongGenre()));
            song = Optional.of(songRepository.save(song.get()));
            this.save(song.get(), file);

            song.ifPresent(s -> {
                artist.get().addSongToArtist(s);
                artistRepository.save(artist.get());
            });
        }

        return song;
    }

    private void save(Song song, MultipartFile file) {
        String songId = song.getId().getId();
        try {
            if (!file.isEmpty()) {
                String fileName = String.format("%s.mp3", songId);
                fileSystemStorage.saveFile(file, fileName, FileLocationType.SONGS);
            }
        } catch (Exception exception) {
            throw new FileStorageException("Could not save the compressed version of the song with id " + songId);
        }
    }

    @Override
    public Optional<Song> publish(SongTransactionRequest songTransactionRequest, MultipartFile cover, String username, String id) {
        Optional<Artist> artist = artistRepository.findByArtistUserInfo_Username(username);
        Optional<Song> song = findById(SongId.of(id));
        if (artist.isPresent() && song.isPresent()) {
            song = song.map(s -> {
                if (!cover.isEmpty()) {
                    String fileName = String.format("%s.png", s.getId().getId());
                    fileSystemStorage.saveFile(cover, fileName, FileLocationType.SONG_COVERS);
                }

                return s.publishAsSingle(PaymentInfo.build(songTransactionRequest.getSubscriptionFee(),
                        songTransactionRequest.getTransactionFee(),
                        songTransactionRequest.getSongTier()));
            });
        }
        return song;
    }

    @Override
    public Optional<Song> raiseTier(SongShortTransactionRequest songShortTransactionRequest, SongId id) {
        return songRepository.findById(id).map(song -> {
            PaymentInfo paymentInfo = PaymentInfo.build(songShortTransactionRequest.getSubscriptionFee(),
                    songShortTransactionRequest.getTransactionFee(),
                    songShortTransactionRequest.getSongTier());
            song.raiseTier(paymentInfo);
            return songRepository.save(song);
        });
    }
}
