package com.musicdistribution.albumcatalog.domain.models.entity;

import com.musicdistribution.albumcatalog.domain.valueobjects.PaymentInfo;
import com.musicdistribution.albumcatalog.domain.valueobjects.SongLength;
import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

/**
 * Song domain entity.
 */
@Entity
@Getter
@Table(name = "Song")
public class Song extends AbstractEntity<SongId> {

    private String songName;

    private Boolean isASingle;

    private Boolean isPublished;

    @Enumerated(value = EnumType.STRING)
    private Genre songGenre;

    @AttributeOverrides({
            @AttributeOverride(name = "subscriptionFee", column = @Column(name = "payment_subscription_fee")),
            @AttributeOverride(name = "transactionFee", column = @Column(name = "payment_transaction_fee")),
            @AttributeOverride(name = "tier", column = @Column(name = "payment_tier"))
    })
    private PaymentInfo paymentInfo;

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
     * @param songLength - song's length.
     * @param genre      - song's genre.
     * @return the created song.
     */
    public static Song build(String songName, Artist artist, SongLength songLength, Genre genre) {
        Song song = new Song();

        song.songName = songName;
        song.songGenre = genre;

        song.isASingle = false;
        song.isPublished = false;
        song.album = null;

        song.creator = artist;
        song.songLength = songLength;

        return song;
    }

    /**
     * Method for publishing a song.
     *
     * @param paymentInfo - the information about the payment for the song that is being published.
     * @return the published song.
     */
    public Song publish(PaymentInfo paymentInfo) {
        this.isASingle = true;
        this.isPublished = true;
        this.paymentInfo = paymentInfo;

        return this;
    }
}
