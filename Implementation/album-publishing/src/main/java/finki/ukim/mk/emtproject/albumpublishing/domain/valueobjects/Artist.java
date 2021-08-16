package finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.auxiliary.ArtistContactInfo;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.auxiliary.ArtistPersonalInfo;
import finki.ukim.mk.emtproject.sharedkernel.domain.base.ValueObject;
import lombok.Getter;

import javax.persistence.*;

@Getter
public class Artist implements ValueObject {

    @AttributeOverrides({
            @AttributeOverride(name="id", column = @Column(name="artist_id"))
    })
    private ArtistId id;
    private String artistFirstName;
    private String artistLastName;

    private Artist() {
        this.id = ArtistId.randomId(ArtistId.class);
    }

    @JsonCreator
    public Artist(@JsonProperty("id") ArtistId id,
                   @JsonProperty("artistFirstName") String artistFirstName,
                   @JsonProperty("artistLastName") String artistLastName) {
        this.id = id;
        this.artistFirstName = artistFirstName;
        this.artistLastName = artistLastName;
    }
}
