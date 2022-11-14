package com.musicdistribution.storageservice.security.services;

import com.musicdistribution.storageservice.domain.model.entity.Artist;
import com.musicdistribution.storageservice.domain.repository.ArtistRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ArtistRepository artistRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Artist artist = artistRepository.findByArtistUserInfo_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException("ArtistUserInfo Not Found with username: " + username));

        return UserDetailsImpl.build(artist);
    }
}
