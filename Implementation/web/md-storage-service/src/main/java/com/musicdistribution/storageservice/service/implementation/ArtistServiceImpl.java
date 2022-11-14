package com.musicdistribution.storageservice.service.implementation;

import com.musicdistribution.storageservice.domain.model.SearchResult;
import com.musicdistribution.storageservice.domain.model.entity.Artist;
import com.musicdistribution.storageservice.domain.model.entity.ArtistId;
import com.musicdistribution.storageservice.domain.model.enums.FileLocationType;
import com.musicdistribution.storageservice.domain.model.request.ArtistRequest;
import com.musicdistribution.storageservice.domain.repository.ArtistRepository;
import com.musicdistribution.storageservice.domain.repository.SearchRepository;
import com.musicdistribution.storageservice.domain.service.IFileSystemStorage;
import com.musicdistribution.storageservice.domain.valueobject.ArtistContactInfo;
import com.musicdistribution.storageservice.domain.valueobject.ArtistPersonalInfo;
import com.musicdistribution.storageservice.service.ArtistService;
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
    private final SearchRepository<Artist> searchRepository;

    private final IFileSystemStorage fileSystemStorage;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<Artist> findAll(Pageable pageable) {
        return artistRepository.findAll(pageable);
    }

    @Override
    public Long findTotalSize() {
        return artistRepository.count();
    }

    @Override
    public SearchResult<Artist> search(List<String> searchParams, String searchTerm, Pageable pageable) {
        return searchRepository.search(searchParams, searchTerm, pageable);
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
