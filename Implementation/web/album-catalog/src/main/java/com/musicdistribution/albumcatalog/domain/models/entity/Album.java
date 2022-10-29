package com.musicdistribution.albumcatalog.domain.models.entity;

import com.musicdistribution.albumcatalog.domain.valueobjects.AlbumInfo;
import com.musicdistribution.albumcatalog.domain.valueobjects.PaymentInfo;
import com.musicdistribution.albumcatalog.domain.valueobjects.SongLength;
import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Album domain entity.
 */
@Data
@Entity
@Table(name = "album")
@EqualsAndHashCode(callSuper = true)
public class Album extends AbstractEntity<AlbumId> {

    private String albumName;

    private SongLength totalLength;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @AttributeOverrides({
            @AttributeOverride(name = "subscriptionFee", column = @Column(name = "payment_subscription_fee")),
            @AttributeOverride(name = "transactionFee", column = @Column(name = "payment_transaction_fee")),
            @AttributeOverride(name = "tier", column = @Column(name = "payment_tier"))
    })
    private PaymentInfo paymentInfo;

    @AttributeOverrides({
            @AttributeOverride(name = "artistName", column = @Column(name = "album_artistName")),
            @AttributeOverride(name = "producerName", column = @Column(name = "album_producerName")),
            @AttributeOverride(name = "composerName", column = @Column(name = "album_composerName"))
    })
    private AlbumInfo albumInfo;


    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private Artist creator;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Song> songs;

    /**
     * Protected no args constructor for the Album entity.
     */
    protected Album() {
        super(AlbumId.randomId(AlbumId.class));
    }

    /**
     * Static method for creating a new album.
     *
     * @param albumName - the album's name
     * @param genre     - the album's genre
     * @param albumInfo - the album's information
     * @param creator   - the album's creator
     * @param paymentInfo - the album's payment information
     * @return the created album.
     */
    public static Album build(String albumName, Genre genre, AlbumInfo albumInfo, Artist creator, PaymentInfo paymentInfo) {
        Album album = new Album();

        album.albumName = albumName;
        album.genre = genre;
        album.albumInfo = albumInfo;
        album.creator = creator;
        album.totalLength = SongLength.build(0);
        album.paymentInfo = paymentInfo;

        album.songs = new ArrayList<>();

        return album;
    }

    /**
     * Method for adding a song to an album.
     *
     * @param song - the song to be added to the album.
     */
    public void addSong(Song song) {
        this.songs.add(song);
        this.totalLength.addSecondsToSongLength(song.getSongLength().getLengthInSeconds());
    }

    /**
     * Method for removing a song from an album.
     *
     * @param song - the song to be added to the album.
     */
    public void removeSong(Song song) {
        this.songs.remove(song);
        this.totalLength.removeSecondsFromSongLength(song.getSongLength().getLengthInSeconds());
    }

    /**
     * Method for calculating the total song length of an album.
     *
     * @return the song length of an album.
     */
    public SongLength totalLength() {
        return SongLength.build(this.songs.size() != 0 ? this.songs.stream().mapToInt(i -> i.getSongLength().getLengthInSeconds()).sum() : 0);
    }
}
