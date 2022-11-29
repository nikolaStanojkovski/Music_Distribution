package com.musicdistribution.streamingservice.service.implementation;

import com.musicdistribution.sharedkernel.domain.valueobjects.Email;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import com.musicdistribution.streamingservice.constant.FileConstants;
import com.musicdistribution.streamingservice.domain.model.entity.core.Artist;
import com.musicdistribution.streamingservice.domain.model.entity.id.ArtistId;
import com.musicdistribution.streamingservice.domain.model.enums.FileLocationType;
import com.musicdistribution.streamingservice.domain.model.request.core.ArtistRequest;
import com.musicdistribution.streamingservice.domain.model.request.AuthRequest;
import com.musicdistribution.sharedkernel.domain.response.SearchResultResponse;
import com.musicdistribution.streamingservice.domain.repository.core.ArtistRepository;
import com.musicdistribution.sharedkernel.domain.repository.SearchRepository;
import com.musicdistribution.streamingservice.domain.service.IFileSystemStorage;
import com.musicdistribution.streamingservice.domain.valueobject.core.UserContactInfo;
import com.musicdistribution.streamingservice.domain.valueobject.core.UserPersonalInfo;
import com.musicdistribution.streamingservice.domain.valueobject.core.UserRegistrationInfo;
import com.musicdistribution.streamingservice.service.ArtistService;
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

    /**
     * Method used for fetching a page of artists from the database.
     *
     * @param pageable - the wrapper object containing pagination data.
     * @return a page of the artists.
     */
    @Override
    public Page<Artist> findAll(Pageable pageable) {
        return artistRepository.findAll(pageable);
    }

    /**
     * Method used for reading the total number of entities from the database.
     *
     * @return the total number of artists from the database.
     */
    @Override
    public Long findTotalSize() {
        return artistRepository.count();
    }

    /**
     * Method used for searching artists.
     *
     * @param searchParams - the object parameters by which the filtering is to be done.
     * @param searchTerm   - the search term by which the filtering is to be done.
     * @param pageable     - the wrapper object containing pagination data.
     * @return a list of the filtered artists.
     */
    @Override
    public SearchResultResponse<Artist> search(List<String> searchParams, String searchTerm, Pageable pageable) {
        return searchRepository.search(searchParams, false, searchTerm, pageable);
    }

    /**
     * Method used for fetching an artist with the specified ID.
     *
     * @param id - artist's ID.
     * @return an optional with the found artist.
     */
    @Override
    public Optional<Artist> findById(ArtistId id) {
        return artistRepository.findById(id);
    }

    /**
     * Method used for authenticating an existing artist from the database.
     *
     * @param authRequest - a wrapper object containing artist's information needed for authentication.
     * @return an optional with the authenticated artist.
     */
    @Override
    public Optional<Artist> login(AuthRequest authRequest) {
        return findByEmail(authRequest.getUsername(), authRequest.getEmailDomain());
    }

    /**
     * Method for registering a new artist in the database.
     *
     * @param profilePicture - the profile picture of the artist to be registered.
     * @param artistRequest  - a wrapper object containing artist's information needed for registration.
     * @return an optional with the registered artist.
     */
    @Override
    public Optional<Artist> register(MultipartFile profilePicture, ArtistRequest artistRequest) {
        if (findByEmail(artistRequest.getUsername(), artistRequest.getEmailDomain()).isPresent() ||
                findByUsername(artistRequest.getUsername()).isPresent())
            return Optional.empty();

        Artist artist = artistRepository.save(Artist.build(UserContactInfo.from(artistRequest.getTelephoneNumber(), artistRequest.getUsername(), artistRequest.getEmailDomain()),
                UserPersonalInfo.from(artistRequest.getFirstName(), artistRequest.getLastName(), artistRequest.getArtName()),
                UserRegistrationInfo.from(artistRequest.getUsername(), passwordEncoder.encode(artistRequest.getPassword()))));

        if (Objects.nonNull(profilePicture) && !profilePicture.isEmpty()) {
            String fileName = String.format("%s.%s", artist.getId().getId(), FileConstants.PNG_EXTENSION);
            fileSystemStorage.saveFile(profilePicture, fileName, FileLocationType.ARTIST_PROFILE_PICTURE);
        }

        return Optional.of(artist);
    }

    /**
     * Method used for filtering an artist by the specified username.
     *
     * @param username - the username by which the filtering is to be done.
     * @return an optional with the found artist.
     */
    private Optional<Artist> findByUsername(String username) {
        return artistRepository.findByUserRegistrationInfo_Username(username);
    }

    /**
     * Method used for filtering an artist by the specified email.
     *
     * @param username    - the username by which the filtering is to be done.
     * @param emailDomain - the email domain by which the filtering is to be done.
     * @return an optional with the found artist.
     */
    private Optional<Artist> findByEmail(String username, EmailDomain emailDomain) {
        return artistRepository.findByUserContactInfo_Email(
                Email.from(username, emailDomain));
    }
}
