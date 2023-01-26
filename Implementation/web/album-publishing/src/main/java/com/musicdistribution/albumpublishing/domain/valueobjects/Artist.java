package com.musicdistribution.albumpublishing.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import lombok.Getter;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Value object for an artist.
 */
@Getter
@Embeddable
public class Artist implements ValueObject {

    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "artist_id"))
    })
    private final ArtistId id;
    private String artistFirstName;
    private String artistLastName;

    /**
     * Protected no-args constructor for the artist.
     */
    protected Artist() {
        this.id = ArtistId.randomId(ArtistId.class);
    }

    /**
     * Public args constructor for an album with aattributes
     *
     * @param id              - album's id.
     * @param artistFirstName - artist's first name.
     * @param artistLastName  - artist's last name.
     */
    @JsonCreator
    public Artist(@JsonProperty("id") ArtistId id,
                  @JsonProperty("artistFirstName") String artistFirstName,
                  @JsonProperty("artistLastName") String artistLastName) {
        this.id = id;
        this.artistFirstName = artistFirstName;
        this.artistLastName = artistLastName;
    }
}
