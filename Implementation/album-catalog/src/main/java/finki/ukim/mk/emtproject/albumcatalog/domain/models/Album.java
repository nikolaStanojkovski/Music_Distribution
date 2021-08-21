package finki.ukim.mk.emtproject.albumcatalog.domain.models;

import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.AlbumInfo;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.SongLength;
import finki.ukim.mk.emtproject.sharedkernel.domain.base.AbstractEntity;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Album domain entity
 */
@Entity
@Table(name="album")
@Data
public class Album extends AbstractEntity<AlbumId> {

    /**
     * Required properties defintiion
     */

    private String albumName;

    private SongLength totalLength;

    private Boolean isPublished;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @AttributeOverrides({
            @AttributeOverride(name="artistName", column = @Column(name="album_artistName")),
            @AttributeOverride(name="producerName", column = @Column(name="album_producerName")),
            @AttributeOverride(name="composerName", column = @Column(name="album_composerName"))
    })
    private AlbumInfo albumInfo;


    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private Artist creator;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Song> songs;


    protected Album() {
        super(AlbumId.randomId(AlbumId.class));
    }

    public static Album build(String albumName, Genre genre, AlbumInfo albumInfo, Artist creator) {
        Album album = new Album();

        album.albumName = albumName;
        album.genre = genre;
        album.albumInfo = albumInfo;
        album.creator = creator;
        album.isPublished = false;
        album.totalLength = SongLength.build(0); // the album is empty in the start

        album.songs = new ArrayList<>();

        return album;
    }

    /**
     * Methods used for defining the consistency rules
     */

    // add the song to the appropriate album
    public Song addSong(Song song) {
        this.songs.add(song);
        this.totalLength.addSecondsToSongLength(song.getSongLength().getLengthSeconds());

        return song;
    }

    // remove the song from the appropriate album
    public Song removeSong(Song song) {
        this.songs.remove(song);
        this.totalLength.removeSecondsFromSongLength(song.getSongLength().getLengthSeconds());

        return song;
    }

    // return total length of the album
    public SongLength totalLength() {
        Integer sum = 0;
        if(this.songs.size() != 0)
            sum = this.songs.stream().mapToInt(i -> i.getSongLength().getLengthSeconds()).sum();

        return SongLength.build(sum);
    }

    // make the album published
    public Boolean publish() {
        this.isPublished = true;
        return this.isPublished;
    }

    // make the album unpublished
    public Boolean unpublish() {
        this.isPublished = false;
        return this.isPublished;
    } // make the album unpublished
}
