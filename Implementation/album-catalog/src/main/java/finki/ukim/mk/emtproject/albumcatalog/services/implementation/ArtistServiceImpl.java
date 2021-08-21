package finki.ukim.mk.emtproject.albumcatalog.services.implementation;

import finki.ukim.mk.emtproject.albumcatalog.domain.exceptions.ArtistNotFoundException;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.Artist;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.ArtistId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.dto.ArtistDto;
import finki.ukim.mk.emtproject.albumcatalog.domain.repository.ArtistRepository;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.ArtistContactInfo;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.ArtistPersonalInfo;
import finki.ukim.mk.emtproject.albumcatalog.services.ArtistService;
import finki.ukim.mk.emtproject.albumcatalog.services.form.ArtistForm;
import finki.ukim.mk.emtproject.albumcatalog.services.form.ArtistLoginForm;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Email;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ArtistService - Service for the implementation of the main specific business logic for the artists
 */
@Service
@Transactional
@AllArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    @Override
    public List<ArtistDto> findAll() {
        return artistRepository.findAll().stream()
                .map(i -> new ArtistDto(i.getId().getId(), i.getArtistContactInfo(), i.getArtistPersonalInfo(), i.getPassword()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Artist> findById(ArtistId id) {
        return artistRepository.findById(id);
    }

    @Override
    public Optional<Artist> createArtist(ArtistForm form) {
        Artist newArtist = Artist.build(ArtistContactInfo.build(form.getTelephoneNumber(), form.getUsername(), form.getEmailDomain()),
                ArtistPersonalInfo.build(form.getFirstName(), form.getLastName(), form.getArtName()), form.getPassword());
        artistRepository.save(newArtist);
        return Optional.of(newArtist);
    }

    @Override
    public ArtistDto loginArtist(ArtistLoginForm artistLoginForm) {
        Optional<Artist> artist = artistRepository.findByArtistContactInfo_Email(Email.createEmail(artistLoginForm.getUsername(), artistLoginForm.getDomainName()));

        if(artist.isPresent())
            if(artist.get().getPassword().equals(artistLoginForm.getPassword()))
                return new ArtistDto(artist.get().getId().getId(),
                        artist.get().getArtistContactInfo(), artist.get().getArtistPersonalInfo(),
                        artist.get().getPassword());

        return null;
    }

    @Override
    public ArtistDto registerArtist(ArtistForm artistForm) {
        Artist newArtist = Artist.build(ArtistContactInfo.build(artistForm.getTelephoneNumber(), artistForm.getUsername(), artistForm.getEmailDomain()),
                ArtistPersonalInfo.build(artistForm.getFirstName(), artistForm.getLastName(), artistForm.getArtName()), artistForm.getPassword());
        artistRepository.save(newArtist);
        return new ArtistDto(newArtist.getId().getId(),
                newArtist.getArtistContactInfo(), newArtist.getArtistPersonalInfo(),
                newArtist.getPassword());
    }
}
