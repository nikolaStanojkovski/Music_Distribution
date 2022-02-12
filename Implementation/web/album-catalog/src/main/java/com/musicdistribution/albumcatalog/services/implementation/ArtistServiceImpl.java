package com.musicdistribution.albumcatalog.services.implementation;

import com.musicdistribution.albumcatalog.domain.models.entity.Artist;
import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;
import com.musicdistribution.albumcatalog.domain.models.request.ArtistRequest;
import com.musicdistribution.albumcatalog.domain.repository.ArtistRepository;
import com.musicdistribution.albumcatalog.domain.valueobjects.ArtistContactInfo;
import com.musicdistribution.albumcatalog.domain.valueobjects.ArtistPersonalInfo;
import com.musicdistribution.albumcatalog.services.ArtistService;
import com.musicdistribution.sharedkernel.domain.valueobjects.Email;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the artist service.
 */
@Service
@Transactional
@AllArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    @Override
    public List<Artist> findAll() {
        return artistRepository.findAll();
    }

    @Override
    public Page<Artist> findAllPageable() {
        return artistRepository.findAll(PageRequest.of(0, 10));
    }

    @Override
    public List<Artist> searchArtists(String searchTerm) {
        return artistRepository.findAllByArtistPersonalInfo_ArtNameIgnoreCaseOrArtistPersonalInfo_FirstNameIgnoreCaseOrArtistPersonalInfo_LastNameIgnoreCase(searchTerm, searchTerm, searchTerm);
    }

    @Override
    public Optional<Artist> findById(ArtistId id) {
        return artistRepository.findById(id);
    }

    @Override
    public Optional<Artist> findByEmail(ArtistRequest artistRequest) {
        return artistRepository.findByArtistContactInfo_Email(Email.createEmail(artistRequest.getUsername(), artistRequest.getEmailDomain()));
    }

    @Override
    public Optional<Artist> loginArtist(ArtistRequest artistRequest) {
        Optional<Artist> artist = findByEmail(artistRequest);

        return (artist.isPresent() && (artist.get().getPassword().equals(artistRequest.getPassword()))) ? artist : Optional.empty();
    }

    @Override
    public Optional<Artist> registerArtist(ArtistRequest artistRequest) {
        Artist artist = Artist.build(ArtistContactInfo.build(artistRequest.getTelephoneNumber(), artistRequest.getUsername(), artistRequest.getEmailDomain()),
                ArtistPersonalInfo.build(artistRequest.getFirstName(), artistRequest.getLastName(), artistRequest.getArtName()), artistRequest.getPassword());

        return Optional.of(artistRepository.save(artist));
    }
}
