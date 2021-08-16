package finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class SongLength implements ValueObject {

    private Integer lengthInSeconds;

    protected SongLength() {
        this.lengthInSeconds = 0;
    }

    protected SongLength(Integer lengthInSeconds) {
        this.lengthInSeconds = lengthInSeconds;
    }

    public static SongLength build(Integer lengthInSeconds) {
        return new SongLength(lengthInSeconds);
    }

    public void addSecondsToSongLength(Integer seconds) {
        if(seconds >= 0) {
            this.lengthInSeconds += seconds;
        }
    }

    public void removeSecondsFromSongLength(Integer seconds) {
        if(seconds >= 0 && (this.lengthInSeconds - seconds) < 0) {
            this.lengthInSeconds -= seconds;
        }
    }

    public Integer getLengthMiliseconds() {
        return this.lengthInSeconds * 1000;
    }

    public Integer getLengthSeconds() {
        return this.lengthInSeconds;
    }

    public Integer getLengthMinutes() {
        return this.lengthInSeconds / 60;
    }

    public Integer getLengthHours() {
        return (this.lengthInSeconds / 60) / 60;
    }
}
