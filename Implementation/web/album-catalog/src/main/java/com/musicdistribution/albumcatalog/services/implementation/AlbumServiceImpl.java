package com.musicdistribution.albumcatalog.services.implementation;

import com.musicdistribution.albumcatalog.domain.exceptions.AlbumNotFoundException;
import com.musicdistribution.albumcatalog.domain.exceptions.ArtistNotFoundException;
import com.musicdistribution.albumcatalog.domain.models.entity.*;
import com.musicdistribution.albumcatalog.domain.models.request.AlbumRequest;
import com.musicdistribution.albumcatalog.domain.repository.AlbumRepository;
import com.musicdistribution.albumcatalog.domain.repository.ArtistRepository;
import com.musicdistribution.albumcatalog.domain.repository.SongRepository;
import com.musicdistribution.albumcatalog.domain.valueobjects.AlbumInfo;
import com.musicdistribution.albumcatalog.services.AlbumService;
import com.musicdistribution.albumcatalog.services.SongService;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    private final SongService songService;

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
    public Optional<Album> createAlbum(AlbumRequest form) {
        ArtistId creatorId = ArtistId.of(form.getCreatorId());
        Artist creator = artistRepository.findById(creatorId).orElseThrow(() -> new ArtistNotFoundException(creatorId));

        Album newAlbum = Album.build(form.getAlbumName(), form.getGenre(), AlbumInfo.build(form.getArtistName(),
                form.getProducerName(), form.getComposerName()), creator);
        albumRepository.save(newAlbum);

        creator.createAlbum(newAlbum);
        artistRepository.save(creator);

        return Optional.of(newAlbum);
    }

    @Override
    public void albumPublished(AlbumId id) {
        Album album = findById(id).orElseThrow(() -> new AlbumNotFoundException(id));
        album.publish();

        albumRepository.save(album);
    }

    @Override
    public void albumUnpublished(AlbumId id) {
        Album album = findById(id).orElseThrow(() -> new AlbumNotFoundException(id));
        List<Song> albumSongs = songRepository.findAllByAlbumId(album.getId());
        if (albumSongs.size() != 0) {
            albumSongs.forEach(v -> songService.deleteSong(v.getId().getId()));
        }
        album.unPublish();

        albumRepository.save(album);
    }
}
