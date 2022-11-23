package com.musicdistribution.streamingservice.service.implementation;

import com.musicdistribution.streamingservice.constant.ExceptionConstants;
import com.musicdistribution.streamingservice.domain.model.entity.Artist;
import com.musicdistribution.streamingservice.domain.repository.ArtistRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of the user details service.
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ArtistRepository artistRepository;

    /**
     * Method used to load a user by his username.
     *
     * @param username - the username by which the filtering is to be done.
     * @return an object containing the required detailed information about a user.
     * @throws UsernameNotFoundException if the user with the specified username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Artist artist = artistRepository.findByUserRegistrationInfo_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(ExceptionConstants.ARTIST_USERNAME_NOT_FOUND, username)));

        return UserDetailsImpl.build(artist);
    }
}
