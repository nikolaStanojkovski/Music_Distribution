package com.musicdistribution.albumpublishing.domain.valueobjects.auxiliary;

import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * Value object that keeps the main information for the album value object.
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
     * Static method used for the creation of an album information object.
     *
     * @param artistName   - artist's name.
     * @param producerName - producer's name.
     * @param composerName - composer's name.
     * @return the new AlbumInfo object.
     */
    public static AlbumInfo build(String artistName, String producerName, String composerName) {
        return new AlbumInfo(artistName, producerName, composerName);
    }
}
