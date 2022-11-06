package com.musicdistribution.albumcatalog.services.implementation;

import com.musicdistribution.albumcatalog.domain.models.entity.Artist;
import com.musicdistribution.albumcatalog.domain.models.entity.ArtistId;
import com.musicdistribution.albumcatalog.domain.models.enums.FileLocationType;
import com.musicdistribution.albumcatalog.domain.models.request.ArtistRequest;
import com.musicdistribution.albumcatalog.domain.repository.ArtistRepository;
import com.musicdistribution.albumcatalog.domain.services.IFileSystemStorage;
import com.musicdistribution.albumcatalog.domain.valueobjects.ArtistContactInfo;
import com.musicdistribution.albumcatalog.domain.valueobjects.ArtistPersonalInfo;
import com.musicdistribution.albumcatalog.services.ArtistService;
import com.musicdistribution.sharedkernel.domain.valueobjects.Email;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of the artist service.
 */
@Service
@Transactional
@AllArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final IFileSystemStorage fileSystemStorage;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<Artist> findAll(Pageable pageable) {
        return artistRepository.findAll(pageable);
    }

    @Override
    public Page<Artist> search(List<String> searchParams, String searchTerm, Pageable pageable) {
        // TODO: Implement the search logic
        return findAll(pageable);
    }

    @Override
    public Optional<Artist> findById(ArtistId id) {
        return artistRepository.findById(id);
    }

    @Override
    public Optional<Artist> loginArtist(ArtistRequest artistRequest) {
        return findByEmail(artistRequest);
    }

    @Override
    public Optional<Artist> registerArtist(MultipartFile profilePicture, ArtistRequest artistRequest) {
        if (findByEmail(artistRequest).isPresent() ||
                findByUsername(artistRequest.getUsername()).isPresent())
            return Optional.empty();

        Artist artist = artistRepository.save(Artist.build(ArtistContactInfo.build(artistRequest.getTelephoneNumber(), artistRequest.getUsername(), artistRequest.getEmailDomain()),
                ArtistPersonalInfo.build(artistRequest.getFirstName(), artistRequest.getLastName(), artistRequest.getArtName()), passwordEncoder.encode(artistRequest.getPassword())));

        if (Objects.nonNull(profilePicture) && !profilePicture.isEmpty()) {
            String fileName = String.format("%s.png", artist.getId().getId());
            fileSystemStorage.saveFile(profilePicture, fileName, FileLocationType.ARTIST_PROFILE_PICTURE);
        }

        return Optional.of(artist);
    }

    private Optional<Artist> findByUsername(String username) {
        return artistRepository.findByArtistUserInfo_Username(username);
    }

    private Optional<Artist> findByEmail(ArtistRequest artistRequest) {
        return artistRepository.findByArtistContactInfo_Email(Email.createEmail(artistRequest.getUsername(), artistRequest.getEmailDomain()));
    }
}
