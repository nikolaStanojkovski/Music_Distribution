package com.musicdistribution.albumcatalog.domain.valueobjects;

import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * Value object for a song length.
 */
@Getter
@Embeddable
@AllArgsConstructor
public class SongLength implements ValueObject {

    private Integer lengthInSeconds;

    /**
     * Protected no-args constructor for SongLength.
     */
    protected SongLength() {
        this.lengthInSeconds = 0;
    }

    /**
     * Static method for creating a new song length object.
     *
     * @param lengthInSeconds - song's length in seconds.
     * @return the song length object.
     */
    public static SongLength build(Integer lengthInSeconds) {
        return new SongLength(lengthInSeconds);
    }

    /**
     * Method for adding seconds to a song length.
     *
     * @param seconds - the amount of seconds to be added.
     */
    public void addSecondsToSongLength(Integer seconds) {
        if (seconds >= 0) {
            this.lengthInSeconds += seconds;
        }
    }

    /**
     * Method for removing seconds from a song length.
     *
     * @param seconds - the amount of seconds to be removed.
     */
    public void removeSecondsFromSongLength(Integer seconds) {
        if (seconds >= 0 && (this.lengthInSeconds - seconds) < 0) {
            this.lengthInSeconds -= seconds;
        }
    }

    /**
     * Method used for formatting the song length.
     *
     * @return the length of the song in the format mm:ss
     */
    public String getFormattedString() {
        String minutes = ((this.lengthInSeconds / 60) < 10)
                ? String.format("0%s", this.lengthInSeconds / 60)
                : String.valueOf(this.lengthInSeconds / 60);
        String seconds = ((this.lengthInSeconds % 60) < 10)
                ? String.format("0%s", this.lengthInSeconds % 60)
                : String.valueOf(this.lengthInSeconds % 60);
        return String.format("%s : %s", minutes, seconds);
    }
}
