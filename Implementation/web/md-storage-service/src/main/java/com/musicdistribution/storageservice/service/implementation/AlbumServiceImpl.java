package com.musicdistribution.storageservice.service.implementation;

import com.musicdistribution.storageservice.domain.model.SearchResult;
import com.musicdistribution.storageservice.domain.model.entity.*;
import com.musicdistribution.storageservice.domain.model.enums.FileLocationType;
import com.musicdistribution.storageservice.domain.model.request.AlbumShortTransactionRequest;
import com.musicdistribution.storageservice.domain.model.request.AlbumTransactionRequest;
import com.musicdistribution.storageservice.domain.repository.AlbumRepository;
import com.musicdistribution.storageservice.domain.repository.ArtistRepository;
import com.musicdistribution.storageservice.domain.repository.SongRepository;
import com.musicdistribution.storageservice.domain.repository.SearchRepository;
import com.musicdistribution.storageservice.domain.service.IFileSystemStorage;
import com.musicdistribution.storageservice.domain.valueobject.AlbumInfo;
import com.musicdistribution.storageservice.domain.valueobject.PaymentInfo;
import com.musicdistribution.storageservice.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the album service.
 */
@Service
@Transactional
@AllArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final SearchRepository<Album> searchRepository;

    private final IFileSystemStorage fileSystemStorage;

    @Override
    public Page<Album> findAll(Pageable pageable) {
        return albumRepository.findAll(pageable);
    }

    @Override
    public SearchResult<Album> search(List<String> searchParams, String searchTerm, Pageable pageable) {
        return searchRepository.search(searchParams, searchTerm, pageable);
    }

    @Override
    public Long findTotalSize() {
        return albumRepository.count();
    }

    @Override
    public Optional<Album> findById(AlbumId id) {
        return albumRepository.findById(id);
    }

    @Override
    public Optional<Album> publish(AlbumTransactionRequest albumTransactionRequest, MultipartFile cover,
                                   String username, List<String> songIds) {
        Optional<Artist> artist = artistRepository.findByArtistUserInfo_Username(username);
        List<Song> songs = filterSongs(songIds);

        if (artist.isPresent() && songs.size() > 0) {
            Album album = Album.build(albumTransactionRequest.getAlbumName(),
                    albumTransactionRequest.getAlbumGenre(),
                    AlbumInfo.build(albumTransactionRequest.getArtistName(),
                            albumTransactionRequest.getProducerName(),
                            albumTransactionRequest.getComposerName()),
                    artist.get(),
                    PaymentInfo.build(albumTransactionRequest.getSubscriptionFee(),
                            albumTransactionRequest.getTransactionFee(),
                            albumTransactionRequest.getAlbumTier()),
                    songs);

            return this.save(album, cover);
        }
        return Optional.empty();
    }

    private List<Song> filterSongs(List<String> songIds) {
        return songIds.stream()
                .map(songId -> songRepository.findById(SongId.of(songId))
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Optional<Album> save(Album album, MultipartFile cover) {
        return Optional.of(albumRepository.save(album))
                .map(a -> {
                    if (!cover.isEmpty()) {
                        String fileName = String.format("%s.png", a.getId().getId());
                        fileSystemStorage.saveFile(cover, fileName, FileLocationType.ALBUM_COVERS);
                    }
                    albumRepository.flush();
                    a.getSongs().forEach(song -> songRepository.save(song.publishAsNonSingle(album)));

                    return a;
                });
    }

    @Override
    public Optional<Album> raiseTier(AlbumShortTransactionRequest albumShortTransactionRequest, AlbumId id) {
        return albumRepository.findById(id).map(album -> {
            PaymentInfo paymentInfo = PaymentInfo.build(albumShortTransactionRequest.getSubscriptionFee(),
                    albumShortTransactionRequest.getTransactionFee(),
                    albumShortTransactionRequest.getAlbumTier());
            album.raiseTier(paymentInfo);
            return albumRepository.save(album);
        });
    }
}
