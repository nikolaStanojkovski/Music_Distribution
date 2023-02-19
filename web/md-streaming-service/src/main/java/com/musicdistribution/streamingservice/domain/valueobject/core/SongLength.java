
package com.musicdistribution.streamingservice.domain.valueobject.core;

import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * A value object that contains information about song length.
 */
@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SongLength implements ValueObject, Serializable {

    private final Integer lengthInSeconds;

    /**
     * Protected no-args constructor for the class.
     */
    protected SongLength() {
        this.lengthInSeconds = 0;
    }

    /**
     * Method used for creating a new song length object.
     *
     * @param lengthInSeconds - song's length in seconds.
     * @return the song length object.
     */
    public static SongLength from(Integer lengthInSeconds) {
        return new SongLength(lengthInSeconds);
    }

    /**
     * Method used for formatting the song length.
     *
     * @return the length of the song in the format {mm:ss}
     */
    public String getFormattedString() {
        String minutes = ((this.lengthInSeconds / 60) < 10)
                ? String.format("0%s", this.lengthInSeconds / 60)
                : String.valueOf(this.lengthInSeconds / 60);
        String seconds = ((this.lengthInSeconds % 60) < 10)
                ? String.format("0%s", this.lengthInSeconds % 60)
                : String.valueOf(this.lengthInSeconds % 60);
        return String.format("%s min, %s sec", minutes, seconds);
    }
}
