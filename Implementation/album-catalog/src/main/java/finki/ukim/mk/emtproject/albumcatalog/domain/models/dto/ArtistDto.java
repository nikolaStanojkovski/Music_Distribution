package finki.ukim.mk.emtproject.albumcatalog.domain.models.dto;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Album;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.AlbumId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.ArtistId;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.Song;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.ArtistContactInfo;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.ArtistPersonalInfo;
import finki.ukim.mk.emtproject.sharedkernel.domain.base.AbstractEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * ArtistDto - Dto object for an artist
 */
@Data
public class ArtistDto {

    private String id;

    private ArtistContactInfo artistContactInfo;

    private ArtistPersonalInfo artistPersonalInfo;

    private String password;

    public ArtistDto(String id, ArtistContactInfo artistContactInfo, ArtistPersonalInfo artistPersonalInfo, String password) {
        this.id = id;
        this.artistContactInfo = artistContactInfo;
        this.artistPersonalInfo = artistPersonalInfo;
        this.password = password;
    }
}
