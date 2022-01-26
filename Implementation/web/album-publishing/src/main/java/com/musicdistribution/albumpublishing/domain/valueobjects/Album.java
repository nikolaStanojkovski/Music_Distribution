package com.musicdistribution.albumpublishing.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Value object for an album.
 */
@Getter
@Embeddable
public class Album implements ValueObject {

    private final AlbumId id;
    private String albumName;
    private Integer totalLength = 0;
    @Enumerated(EnumType.STRING)
    private Genre genre;

    /**
     * Protected no-args constructor for the album.
     */
    protected Album() {
        this.id = AlbumId.randomId(AlbumId.class);
    }

    /**
     * Public args constructor for an album with attributes.
     *
     * @param id          - album's id.
     * @param albumName   - album's name.
     * @param totalLength - album's total length.
     * @param genre       - album's genre.
     */
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
