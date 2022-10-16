package com.musicdistribution.albumcatalog.domain.models.entity;

import com.musicdistribution.albumcatalog.domain.valueobjects.SongLength;
import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Objects;

/**
 * Song domain entity.
 */
@Entity
@Getter
@Table(name = "Song")
public class Song extends AbstractEntity<SongId> {

    private String songName;

    private Boolean isASingle;

    @AttributeOverrides({
            @AttributeOverride(name = "lengthInSeconds", column = @Column(name = "song_length"))
    })
    private SongLength songLength;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private Artist creator;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private Album album;

    /**
     * Protected no args constructor for the Song entity.
     */
    protected Song() {
        super(SongId.randomId(SongId.class));
    }

    /**
     * Static method for creating a new song.
     *
     * @param songName   - song's name.
     * @param artist     - song's artist.
     * @param album      - song's album.
     * @param songLength - song's length.
     * @return the created song.
     */
    public static Song build(String songName, Artist artist, Album album, SongLength songLength) {
        Song song = new Song();

        song.songName = songName;

        if (Objects.isNull(album)) {
            song.isASingle = true;
            song.album = null;
        } else {
            song.isASingle = false;
            song.album = album;
        }

        song.creator = artist;
        song.songLength = songLength;

        return song;
    }

    /**
     * Method for publishing a song.
     *
     * @param song - the song to be published.
     * @return the published song.
     */
    public static Song publishSong(Song song) {
        if (!song.isASingle) {
            song.isASingle = true;
        }
        return song;
    }
}
