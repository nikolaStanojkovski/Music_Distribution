package com.musicdistribution.storageservice.domain.valueobject;

import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * Value object for an album information that contains the name of the artist, producer and composer.
 */
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AlbumInfo implements ValueObject {

    private String artistName;
    private String producerName;
    private String composerName;

    /**
     * Static method for creating a new album information object.
     *
     * @param artistName   - artist's name.
     * @param producerName - producer's name.
     * @param composerName - composer's name.
     * @return the created album information object.
     */
    public static AlbumInfo build(String artistName, String producerName, String composerName) {
        return new AlbumInfo(artistName, producerName, composerName);
    }
}
