package com.musicdistribution.albumcatalog.services.implementation;

import com.musicdistribution.albumcatalog.domain.models.entity.*;
import com.musicdistribution.albumcatalog.domain.models.enums.FileLocationType;
import com.musicdistribution.albumcatalog.domain.models.request.AlbumShortTransactionRequest;
import com.musicdistribution.albumcatalog.domain.models.request.AlbumTransactionRequest;
import com.musicdistribution.albumcatalog.domain.repository.AlbumRepository;
import com.musicdistribution.albumcatalog.domain.repository.ArtistRepository;
import com.musicdistribution.albumcatalog.domain.repository.SongRepository;
import com.musicdistribution.albumcatalog.domain.services.IFileSystemStorage;
import com.musicdistribution.albumcatalog.domain.valueobjects.AlbumInfo;
import com.musicdistribution.albumcatalog.domain.valueobjects.PaymentInfo;
import com.musicdistribution.albumcatalog.services.AlbumService;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    private final IFileSystemStorage fileSystemStorage;

    @Override
    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    @Override
    public List<Album> findAllByArtist(ArtistId artistId) {
        return albumRepository.findAllByCreatorId(artistId);
    }

    @Override
    public List<Album> findAllByGenre(Genre genre) {
        return albumRepository.findAllByGenre(genre);
    }

    @Override
    public List<Album> searchAlbums(String searchTerm) {
        return albumRepository.findAllByAlbumNameIgnoreCase(searchTerm);
    }

    @Override
    public Page<Album> findAllPageable() {
        return albumRepository.findAll(PageRequest.of(0, 10));
    }

    @Override
    public Optional<Album> findById(AlbumId id) {
        return albumRepository.findById(id);
    }

    @Override
    public Optional<Album> publishAlbum(AlbumTransactionRequest albumTransactionRequest, MultipartFile cover,
                                        String username, List<String> songIds) {
        Optional<Artist> artist = artistRepository.findByArtistUserInfo_Username(username);
        List<Song> songs = songIds.stream()
                .map(songId -> songRepository.findById(SongId.of(songId))
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

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

            return saveAlbum(album, cover);
        }
        return Optional.empty();
    }

    private Optional<Album> saveAlbum(Album album, MultipartFile cover) {
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
    public Optional<Album> raiseTierAlbum(AlbumShortTransactionRequest albumShortTransactionRequest, AlbumId id) {
        return albumRepository.findById(id).map(album -> {
            PaymentInfo paymentInfo = PaymentInfo.build(albumShortTransactionRequest.getSubscriptionFee(),
                    albumShortTransactionRequest.getTransactionFee(),
                    albumShortTransactionRequest.getAlbumTier());
            album.raiseTier(paymentInfo);
            return albumRepository.save(album);
        });
    }
}
