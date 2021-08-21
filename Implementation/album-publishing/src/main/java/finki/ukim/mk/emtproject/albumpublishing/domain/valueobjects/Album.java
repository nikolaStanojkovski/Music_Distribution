package finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.auxiliary.AlbumInfo;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.auxiliary.ArtistContactInfo;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.auxiliary.ArtistPersonalInfo;
import finki.ukim.mk.emtproject.sharedkernel.domain.base.ValueObject;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.Getter;

import javax.persistence.*;

/**
 * Album - value object that keeps the essential information about the album
 */
@Getter
public class Album implements ValueObject {

    private AlbumId id;

    private String albumName;

    private Integer totalLength = 0;

    @Enumerated(EnumType.STRING)
    private Genre genre;


    private Album() {
        this.id = AlbumId.randomId(AlbumId.class);
    }

    @JsonCreator
    public Album(@JsonProperty("id") AlbumId id,
                  @JsonProperty("albumName") String albumName,
                  @JsonProperty("totalLength") Integer totalLength,
                  @JsonProperty("genre") Genre genre) {
        this.id = id;
        this.albumName = albumName;
        this.totalLength = totalLength;
        this.genre = genre;
    }
}
