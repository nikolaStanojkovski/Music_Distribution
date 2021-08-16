package finki.ukim.mk.emtproject.albumcatalog.services.implementation;

import finki.ukim.mk.emtproject.albumcatalog.domain.exceptions.AlbumNotFoundException;
import finki.ukim.mk.emtproject.albumcatalog.domain.exceptions.ArtistNotFoundException;
import finki.ukim.mk.emtproject.albumcatalog.domain.exceptions.SongNotFoundException;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.*;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.dto.AlbumDto;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.dto.ArtistDto;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.dto.SongDto;
import finki.ukim.mk.emtproject.albumcatalog.domain.repository.AlbumRepository;
import finki.ukim.mk.emtproject.albumcatalog.domain.repository.ArtistRepository;
import finki.ukim.mk.emtproject.albumcatalog.domain.repository.SongRepository;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.SongLength;
import finki.ukim.mk.emtproject.albumcatalog.services.SongService;
import finki.ukim.mk.emtproject.albumcatalog.services.form.SongForm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class SongServiceImpl implements SongService {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    @Override
    public List<SongDto> findAll() {
        return songRepository.findAll()
                .stream().map(i -> new SongDto(i.getId().getId(),
                        i.getSongName(), i.getIsASingle(), i.getSongLength(),
                        new ArtistDto(i.getId().getId(), i.getCreator().getArtistContactInfo(), i.getCreator().getArtistPersonalInfo(), i.getCreator().getPassword()),
                        new AlbumDto(i.getAlbum()))).collect(Collectors.toList());
    }

    @Override
    public Optional<Song> findById(SongId id) {
        return songRepository.findById(id);
    }

    @Override
    public Optional<Song> createSong(SongForm form) {
        Song newSong = null;

        if(form.getAlbumId() != null && form.getCreatorId() != null) {
            ArtistId creatorId = ArtistId.of(form.getCreatorId());
            Artist creator = artistRepository.findById(creatorId).orElseThrow(() -> new ArtistNotFoundException(creatorId));

            AlbumId albumId = AlbumId.of(form.getAlbumId());
            Album album = albumRepository.findById(albumId).orElseThrow(() -> new AlbumNotFoundException(albumId));

            newSong = Song.build(form.getSongName(), creator, album, SongLength.build(form.getLengthInSeconds()));
            songRepository.save(newSong);

            album.addSongToAlbum(newSong);
            creator.addSongToArtist(newSong);
            albumRepository.save(album);
            artistRepository.save(creator);
        } else if(form.getCreatorId() != null) {
            ArtistId creatorId = ArtistId.of(form.getCreatorId());
            Artist creator = artistRepository.findById(creatorId).orElseThrow(() -> new ArtistNotFoundException(creatorId));

            newSong = Song.build(form.getSongName(), creator, null, SongLength.build(form.getLengthInSeconds()));
            songRepository.save(newSong);

            creator.addSongToArtist(newSong);
            artistRepository.save(creator);
        } // both can not be null becasue artist needs to get authenticated before adding songs

        return Optional.of(newSong);
    }
}
