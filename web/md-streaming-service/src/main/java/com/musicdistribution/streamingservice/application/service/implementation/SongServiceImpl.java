package com.musicdistribution.streamingservice.application.service.implementation;

import com.musicdistribution.sharedkernel.domain.repository.SearchRepository;
import com.musicdistribution.sharedkernel.domain.response.SearchResultResponse;
import com.musicdistribution.streamingservice.constant.ExceptionConstants;
import com.musicdistribution.streamingservice.constant.FileConstants;
import com.musicdistribution.streamingservice.domain.exception.FileStorageException;
import com.musicdistribution.streamingservice.domain.model.entity.core.Artist;
import com.musicdistribution.streamingservice.domain.model.entity.core.Notification;
import com.musicdistribution.streamingservice.domain.model.entity.core.Song;
import com.musicdistribution.streamingservice.domain.model.entity.id.SongId;
import com.musicdistribution.streamingservice.domain.model.enums.EntityType;
import com.musicdistribution.streamingservice.domain.model.enums.FileLocationType;
import com.musicdistribution.streamingservice.domain.model.request.core.SongTransactionRequest;
import com.musicdistribution.streamingservice.domain.model.request.core.SongRequest;
import com.musicdistribution.streamingservice.domain.repository.core.ArtistRepository;
import com.musicdistribution.streamingservice.domain.repository.core.ListenerRepository;
import com.musicdistribution.streamingservice.domain.repository.core.NotificationRepository;
import com.musicdistribution.streamingservice.domain.repository.core.SongRepository;
import com.musicdistribution.streamingservice.domain.service.IFileSystemStorage;
import com.musicdistribution.streamingservice.domain.valueobject.PaymentInfo;
import com.musicdistribution.streamingservice.domain.valueobject.core.SongLength;
import com.musicdistribution.streamingservice.application.service.SongService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final NotificationRepository notificationRepository;
    private final ListenerRepository listenerRepository;
    private final SearchRepository<Song> searchRepository;

    private final IFileSystemStorage fileSystemStorage;

    /**
     * Method used for fetching a page of songs from the database.
     *
     * @param pageable              - the wrapper object containing pagination data.
     * @param shouldFilterPublished S- a flag determining whether a filtering should be done by publishing status.
     * @return a page of the songs.
     */
    @Override
    public Page<Song> findAll(Pageable pageable, Boolean shouldFilterPublished) {
        return (shouldFilterPublished)
                ? songRepository.findByIsASingle(true, pageable)
                : songRepository.findAll(pageable);
    }

    /**
     * Method used for reading the total number of entities from the database.
     *
     * @param shouldFilterPublished - a flag determining whether a filtering should be done by publishing status.
     * @return the total number of songs from the database.
     */
    @Override
    public Long findSize(Boolean shouldFilterPublished) {
        return (shouldFilterPublished)
                ? songRepository.countByIsASingle(true)
                : songRepository.count();
    }

    /**
     * Method used for searching songs.
     *
     * @param searchParams          - the object parameters by which the filtering is to be done.
     * @param shouldFilterPublished - a flag determining whether a filtering should be done by publishing status.
     * @param searchTerm            - the search term by which the filtering is to be done.
     * @param pageable              - the wrapper object containing pagination data.
     * @return a list of the filtered songs.
     */
    @Override
    public SearchResultResponse<Song> search(List<String> searchParams, Boolean shouldFilterPublished,
                                             String searchTerm, Pageable pageable) {
        return searchRepository.search(searchParams, shouldFilterPublished, searchTerm, pageable);
    }

    /**
     * Method used for fetching a song with the specified ID.
     *
     * @param id - song's ID.
     * @return an optional with the found song.
     */
    @Override
    public Optional<Song> findById(SongId id) {
        return songRepository.findById(id);
    }

    /**
     * Method used for creating a new song in the database.
     *
     * @param songRequest - song's data object containing the new song's information.
     * @param username    - the username of the user which is creating the song.
     * @return an optional with the created song.
     */
    @Override
    public Optional<Song> create(SongRequest songRequest, MultipartFile file, String username) {
        Optional<Song> song = Optional.empty();
        Optional<Artist> artist = artistRepository.findByUserRegistrationInfo_Username(username);

        if (artist.isPresent()) {
            song = Optional.of(Song.build(songRequest.getSongName(),
                    artist.get(),
                    SongLength.from(songRequest.getLengthInSeconds()),
                    songRequest.getSongGenre()));
            song = Optional.of(songRepository.save(song.get()));
            this.save(song.get(), file);

            song.ifPresent(s -> {
                artist.get().addSong(s);
                artistRepository.save(artist.get());
            });
        }

        return song;
    }

    /**
     * Method used to save a new song with the specified audio file.
     *
     * @param song - the object to be saved as an song.
     * @param file - the audio file for the song.
     */
    private void save(Song song, MultipartFile file) {
        String songId = song.getId().getId();
        try {
            if (!file.isEmpty()) {
                String fileName = String.format("%s.%s", songId, FileConstants.MPEG_EXTENSION);
                fileSystemStorage.saveFile(file, fileName, FileLocationType.SONGS);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new FileStorageException(String.format(ExceptionConstants.SONG_NOT_SAVED, songId));
        }
    }

    /**
     * Method used for publishing a new song.
     *
     * @param songTransactionRequest - song's data object containing the new song's information.
     * @param cover                  - song's cover picture.
     * @param username               - the username of the artist who is publishing the song.
     * @param id                     - the ID of the song which is to be published.
     * @return an optional with the published song.
     */
    @Override
    public Optional<Song> publish(SongTransactionRequest songTransactionRequest, MultipartFile cover, String username, String id) {
        Optional<Artist> artist = artistRepository.findByUserRegistrationInfo_Username(username);
        Optional<Song> song = findById(SongId.of(id));
        if (artist.isPresent() && song.isPresent()) {
            song = song.map(s -> {
                if (!cover.isEmpty()) {
                    String fileName = String.format("%s.%s", s.getId().getId(), FileConstants.PNG_EXTENSION);
                    fileSystemStorage.saveFile(cover, fileName, FileLocationType.SONG_COVERS);
                }

                createNotifications(s, artist.get());
                return s.publishAsSingle(PaymentInfo.from(songTransactionRequest.getSubscriptionFee(),
                        songTransactionRequest.getTransactionFee(),
                        songTransactionRequest.getSongTier()));
            });
        }
        return song;
    }

    /**
     * Method used to create notifications for the fans of the artist who is publishing.
     *
     * @param song   - the song which is being published.
     * @param artist - the artist who is publishing the song.
     */
    private void createNotifications(Song song, Artist artist) {
        listenerRepository.findAllByFavouriteArtists_Id(artist.getId())
                .forEach(listener -> notificationRepository.save(
                        Notification.build(song.getId().getId(), artist, listener, EntityType.SONGS)
                ));
    }

    /**
     * Method used for raising an existing song's tier.
     *
     * @param songShortTransactionRequest - song's data object containing the required song information.
     * @param id                          - song's id.
     * @return an optional with the updated song.
     */
    @Override
    public Optional<Song> raiseTier(SongTransactionRequest songShortTransactionRequest, SongId id) {
        return songRepository.findById(id).map(song -> {
            PaymentInfo paymentInfo = PaymentInfo.from(songShortTransactionRequest.getSubscriptionFee(),
                    songShortTransactionRequest.getTransactionFee(),
                    songShortTransactionRequest.getSongTier());
            return Optional.ofNullable(song.raiseTier(paymentInfo))
                    .map(songRepository::save)
                    .orElse(null);
        });
    }
}
