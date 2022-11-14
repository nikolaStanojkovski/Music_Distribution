package com.musicdistribution.storageservice.domain.model.entity;

import com.musicdistribution.storageservice.domain.valueobject.PaymentInfo;
import com.musicdistribution.storageservice.domain.valueobject.SongLength;
import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Song domain entity.
 */
@Entity
@Getter
@Table(name = "Song")
public class Song extends AbstractEntity<SongId> implements Serializable {

    private String songName;

    private Boolean isASingle;

    private Boolean isPublished;

    @Enumerated(value = EnumType.STRING)
    private Genre songGenre;

    @AttributeOverrides({
            @AttributeOverride(name = "subscriptionFee.amount", column = @Column(name = "payment_subscription_fee_amount")),
            @AttributeOverride(name = "subscriptionFee.currency", column = @Column(name = "payment_subscription_fee_amount_currency")),
            @AttributeOverride(name = "transactionFee.amount", column = @Column(name = "payment_transaction_fee_amount")),
            @AttributeOverride(name = "transactionFee.currency", column = @Column(name = "payment_transaction_fee_currency")),
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
     * Method for publishing a song as a single.
     *
     * @param paymentInfo - the information about the payment for the song that is being published.
     * @return the published song.
     */
    public Song publishAsSingle(PaymentInfo paymentInfo) {
        this.isASingle = true;
        this.isPublished = true;
        this.paymentInfo = paymentInfo;

        return this;
    }

    /**
     * Method for publishing a song as non-single single, part of an album.
     *
     * @param album - the album in which the song is put into.
     * @return the published song.
     */
    public Song publishAsNonSingle(Album album) {
        this.isASingle = false;
        this.isPublished = true;
        this.album = album;

        return this;
    }

    /**
     * Method used for raising song's tier.
     *
     * @param paymentInfo - the payment information for raising song's tier.
     */
    public void raiseTier(PaymentInfo paymentInfo) {
        Money subscriptionFeeSum = this.paymentInfo.getSubscriptionFee().add(paymentInfo.getSubscriptionFee());
        Money transactionFeeSum = this.paymentInfo.getTransactionFee().add(paymentInfo.getTransactionFee());

        this.paymentInfo = PaymentInfo.build(subscriptionFeeSum, transactionFeeSum, paymentInfo.getTier());
    }
}
