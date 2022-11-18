package com.musicdistribution.storageservice.service.implementation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.musicdistribution.storageservice.constant.AuthConstants;
import com.musicdistribution.storageservice.domain.model.entity.Artist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the user details wrapper.
 */
@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final String id;
    private final String username;
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Method used to create a new user details object.
     *
     * @param artist - the artist from which the new object is to be created.
     * @return the created user details object.
     */
    public static UserDetailsImpl build(Artist artist) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(AuthConstants.ARTIST_AUTHORITY);

        return new UserDetailsImpl(
                artist.getId().getId(),
                artist.getArtistUserInfo().getUsername(),
                artist.getArtistUserInfo().getPassword(),
                authorities);
    }

    /**
     * Getter for the user authorities.
     *
     * @return the user authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Getter for the user password.
     *
     * @return the user password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Getter for the username.
     *
     * @return the username.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Method used for determining whether an account is expired.
     *
     * @return a flag determining whether the account has expired or not.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Method used for determining whether an account is locked.
     *
     * @return a flag determining whether the account is locked or not.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Method used for determining whether the user credentials are expired.
     *
     * @return a flag determining whether the user credentials are expired or not.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Method used for determining whether the user account is enabled.
     *
     * @return a flag determining whether the user account is enabled or not.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Method used to generate logic for comparing two user details objects.
     *
     * @param o - a user details object with which {this} object is to be compared with.
     * @return a flag determining whether the objects are identical or not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}