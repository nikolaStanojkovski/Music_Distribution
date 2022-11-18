package com.musicdistribution.storageservice.domain.valueobject;

import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * A value object that contains the specific album publishing details.
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumInfo implements ValueObject {

    private String artistName;
    private String producerName;
    private String composerName;

    /**
     * Method used for creating a new album information object.
     *
     * @param artistName   - artist's name.
     * @param producerName - producer's name.
     * @param composerName - composer's name.
     * @return the created album information object.
     */
    public static AlbumInfo from(String artistName, String producerName, String composerName) {
        return new AlbumInfo(artistName, producerName, composerName);
    }
}
