package com.musicdistribution.storageservice.service.implementation;

import com.musicdistribution.storageservice.constant.FileConstants;
import com.musicdistribution.storageservice.domain.model.entity.*;
import com.musicdistribution.storageservice.domain.model.enums.FileLocationType;
import com.musicdistribution.storageservice.domain.model.request.AlbumShortTransactionRequest;
import com.musicdistribution.storageservice.domain.model.request.AlbumTransactionRequest;
import com.musicdistribution.storageservice.domain.model.response.SearchResultResponse;
import com.musicdistribution.storageservice.domain.repository.AlbumRepository;
import com.musicdistribution.storageservice.domain.repository.ArtistRepository;
import com.musicdistribution.storageservice.domain.repository.SearchRepository;
import com.musicdistribution.storageservice.domain.repository.SongRepository;
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

    /**
     * Method used for fetching a page of albums from the database.
     *
     * @param pageable - the wrapper object containing pagination data.
     * @return a page of the albums.
     */
    @Override
    public Page<Album> findAll(Pageable pageable) {
        return albumRepository.findAll(pageable);
    }

    /**
     * Method used for searching albums.
     *
     * @param searchParams - the object parameters by which the filtering is to be done.
     * @param searchTerm   - the search term by which the filtering is to be done.
     * @param pageable     - the wrapper object containing pagination data.
     * @return a list of the filtered albums.
     */
    @Override
    public SearchResultResponse<Album> search(List<String> searchParams, String searchTerm, Pageable pageable) {
        return searchRepository.search(searchParams, searchTerm, pageable);
    }

    /**
     * Method used for reading the total number of entities from the database.
     *
     * @return the total number of albums from the database.
     */
    @Override
    public Long findTotalSize() {
        return albumRepository.count();
    }

    /**
     * Method used for fetching an album with the specified ID.
     *
     * @param id - album's ID.
     * @return an optional with the found album.
     */
    @Override
    public Optional<Album> findById(AlbumId id) {
        return albumRepository.findById(id);
    }

    /**
     * Method used for publishing a new album.
     *
     * @param albumTransactionRequest - album's data object containing the new album's information.
     * @param cover                   - album's cover picture.
     * @param username                - the username of the artist who is publishing the album.
     * @param songIds                 - the IDs of the songs from which the album is consisted of.
     * @return an optional with the published album.
     */
    @Override
    public Optional<Album> publish(AlbumTransactionRequest albumTransactionRequest, MultipartFile cover,
                                   String username, List<String> songIds) {
        Optional<Artist> artist = artistRepository.findByArtistUserInfo_Username(username);
        List<Song> songs = filterSongs(songIds);

        if (artist.isPresent() && songs.size() > 0) {
            Album album = Album.build(albumTransactionRequest.getAlbumName(),
                    albumTransactionRequest.getAlbumGenre(),
                    AlbumInfo.from(albumTransactionRequest.getArtistName(),
                            albumTransactionRequest.getProducerName(),
                            albumTransactionRequest.getComposerName()),
                    artist.get(),
                    PaymentInfo.from(albumTransactionRequest.getSubscriptionFee(),
                            albumTransactionRequest.getTransactionFee(),
                            albumTransactionRequest.getAlbumTier()),
                    songs);

            return this.save(album, cover);
        }
        return Optional.empty();
    }

    /**
     * Method used to filter songs with specified IDs.
     *
     * @param songIds - the list of the IDs by which the songs are to be filtered.
     * @return a list of the filtered songs.
     */
    private List<Song> filterSongs(List<String> songIds) {
        return songIds.stream()
                .map(songId -> songRepository.findById(SongId.of(songId))
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Method used to save a new album with the specified cover picture.
     *
     * @param album - the object to be saved as an album.
     * @param cover - the cover picture of the album.
     * @return an optional with the saved album.
     */
    private Optional<Album> save(Album album, MultipartFile cover) {
        return Optional.of(albumRepository.save(album))
                .map(a -> {
                    if (!cover.isEmpty()) {
                        String fileName = String.format("%s.%s", a.getId().getId(), FileConstants.PNG_EXTENSION);
                        fileSystemStorage.saveFile(cover, fileName, FileLocationType.ALBUM_COVERS);
                    }
                    albumRepository.flush();
                    a.getSongs().forEach(song -> songRepository.save(song.publishAsNonSingle(album)));

                    return a;
                });
    }

    /**
     * Method used for raising an existing album's tier.
     *
     * @param albumShortTransactionRequest - album's data object containing album's transaction information.
     * @param id                           - album's id.
     * @return an optional with the updated album.
     */
    @Override
    public Optional<Album> raiseTier(AlbumShortTransactionRequest albumShortTransactionRequest, AlbumId id) {
        return albumRepository.findById(id).map(album -> {
            PaymentInfo paymentInfo = PaymentInfo.from(albumShortTransactionRequest.getSubscriptionFee(),
                    albumShortTransactionRequest.getTransactionFee(),
                    albumShortTransactionRequest.getAlbumTier());
            album.raiseTier(paymentInfo);
            return albumRepository.save(album);
        });
    }
}
