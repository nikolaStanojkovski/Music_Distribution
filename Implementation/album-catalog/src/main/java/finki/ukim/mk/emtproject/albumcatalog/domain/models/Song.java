package finki.ukim.mk.emtproject.albumcatalog.domain.models;

import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.SongLength;
import finki.ukim.mk.emtproject.sharedkernel.domain.base.AbstractEntity;
import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.lang.Nullable;

import javax.persistence.*;

/**
 * Song domain entity
 */
@Entity
@Table(name="song")
@Getter
public class Song extends AbstractEntity<SongId> {

    /**
     * Required properties defintiion
     */

    private String songName;

    private Boolean isASingle;

    @AttributeOverrides({
            @AttributeOverride(name="lengthInSeconds", column = @Column(name="song_length"))
    })
    private SongLength songLength;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private Artist creator;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private Album album;


    private Song() {
        super(SongId.randomId(SongId.class));
    }

    public static Song build(String songName, Artist artist, Album album, SongLength songLength) {
        Song song = new Song();

        song.songName = songName;
        if(album == null) {
            song.isASingle = true;
            // the song is a single if it doesn't belong in any album
            song.album = null;
        } else {
            song.isASingle = false;
            // the song belongs to an album, so it isn't a single
            song.album = album;
        }

        song.creator = artist;
        song.songLength = songLength;

        return song;
    }
}
