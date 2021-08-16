package finki.ukim.mk.emtproject.albumcatalog.services.implementation;

import finki.ukim.mk.emtproject.albumcatalog.domain.exceptions.ArtistNotFoundException;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.Album;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.AlbumId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.Artist;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.ArtistId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.dto.AlbumDto;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.dto.ArtistDto;
import finki.ukim.mk.emtproject.albumcatalog.domain.repository.AlbumRepository;
import finki.ukim.mk.emtproject.albumcatalog.domain.repository.ArtistRepository;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.AlbumInfo;
import finki.ukim.mk.emtproject.albumcatalog.services.AlbumService;
import finki.ukim.mk.emtproject.albumcatalog.services.form.AlbumForm;
import finki.ukim.mk.emtproject.albumcatalog.services.form.AlbumPublishForm;
import finki.ukim.mk.emtproject.sharedkernel.infra.DomainEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final DomainEventPublisher domainEventPublisher;
    private final Validator validator;


    @Override
    public List<AlbumDto> findAll() {
        return albumRepository.findAll()
                .stream().map(i -> new AlbumDto(i.getId().getId(), i.getAlbumName(), i.getTotalLength(),
                        i.getIsPublished(), i.getGenre(), i.getAlbumInfo(),
                        new ArtistDto(i.getCreator().getId().getId(), i.getCreator().getArtistContactInfo(), i.getCreator().getArtistPersonalInfo(), i.getCreator().getPassword())))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Album> findById(AlbumId id) {
        return albumRepository.findById(id);
    }

    @Override
    public Optional<Album> createAlbum(AlbumForm form) {
        ArtistId creatorId = ArtistId.of(form.getCreatorId());
        Artist creator = artistRepository.findById(creatorId).orElseThrow(() -> new ArtistNotFoundException(creatorId));

        Album newAlbum = Album.build(form.getAlbumName(), form.getGenre(), AlbumInfo.build(form.getArtistName(),
                form.getProducerName(), form.getComposerName()), creator);
        albumRepository.save(newAlbum);

        creator.createAlbum(newAlbum);
        artistRepository.save(creator);

        return Optional.of(newAlbum);
    }
}
