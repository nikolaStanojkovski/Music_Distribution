package com.musicdistribution.streamingservice.domain.model.entity.core;

import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import com.musicdistribution.streamingservice.constant.EntityConstants;
import com.musicdistribution.streamingservice.domain.model.entity.id.SongId;
import com.musicdistribution.streamingservice.domain.valueobject.PaymentInfo;
import com.musicdistribution.streamingservice.domain.valueobject.core.SongLength;
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
@Table(name = EntityConstants.SONG)
public class Song extends AbstractEntity<SongId> implements Serializable {

    private String songName;

    private Boolean isASingle;

    private Boolean isPublished;

    @Enumerated(value = EnumType.STRING)
    private Genre songGenre;

    @AttributeOverrides({
            @AttributeOverride(name = EntityConstants.SUBSCRIPTION_FEE_AMOUNT,
                    column = @Column(name = EntityConstants.SONG_SUBSCRIPTION_FEE_AMOUNT)),
            @AttributeOverride(name = EntityConstants.SUBSCRIPTION_FEE_CURRENCY,
                    column = @Column(name = EntityConstants.SONG_SUBSCRIPTION_FEE_CURRENCY)),
            @AttributeOverride(name = EntityConstants.TRANSACTION_FEE_AMOUNT,
                    column = @Column(name = EntityConstants.SONG_TRANSACTION_FEE_AMOUNT)),
            @AttributeOverride(name = EntityConstants.TRANSACTION_FEE_CURRENCY,
                    column = @Column(name = EntityConstants.SONG_TRANSACTION_FEE_CURRENCY)),
            @AttributeOverride(name = EntityConstants.TIER,
                    column = @Column(name = EntityConstants.SONG_PAYMENT_TIER))
    })
    private PaymentInfo paymentInfo;

    @AttributeOverrides({
            @AttributeOverride(name = EntityConstants.LENGTH_IN_SECONDS,
                    column = @Column(name = EntityConstants.SONG_LENGTH_IN_SECONDS))
    })
    private SongLength songLength;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private Artist creator;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private Album album;

    /**
     * Protected no args constructor for creating a new Song entity.
     */
    protected Song() {
        super(SongId.randomId(SongId.class));
    }

    /**
     * Static method used for creating a new song.
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
     * Method used for publishing a song as a single.
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
     * Method used for publishing a non-single song, which is a part of an album.
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
     * @param paymentInfo - the information about the payment that was
     *                    made in order for the song's tier to be raised.
     * @return the song whose tier was raised.
     */
    public Song raiseTier(PaymentInfo paymentInfo) {
        Money subscriptionFeeSum = this.paymentInfo.getSubscriptionFee().add(paymentInfo.getSubscriptionFee());
        Money transactionFeeSum = this.paymentInfo.getTransactionFee().add(paymentInfo.getTransactionFee());

        this.paymentInfo = PaymentInfo.from(subscriptionFeeSum, transactionFeeSum, paymentInfo.getTier());
        return this;
    }
}
