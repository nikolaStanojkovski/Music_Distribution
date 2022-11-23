package com.musicdistribution.streamingservice.domain.model.entity;

import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import com.musicdistribution.streamingservice.constant.EntityConstants;
import com.musicdistribution.streamingservice.domain.valueobject.AlbumInfo;
import com.musicdistribution.streamingservice.domain.valueobject.PaymentInfo;
import com.musicdistribution.streamingservice.domain.valueobject.SongLength;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Album domain entity.
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = EntityConstants.ALBUM)
public class Album extends AbstractEntity<AlbumId> implements Serializable {

    private String albumName;

    private SongLength totalLength;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @AttributeOverrides({
            @AttributeOverride(name = EntityConstants.SUBSCRIPTION_FEE_AMOUNT,
                    column = @Column(name = EntityConstants.ALBUM_SUBSCRIPTION_FEE_AMOUNT)),
            @AttributeOverride(name = EntityConstants.SUBSCRIPTION_FEE_CURRENCY,
                    column = @Column(name = EntityConstants.ALBUM_SUBSCRIPTION_FEE_CURRENCY)),
            @AttributeOverride(name = EntityConstants.TRANSACTION_FEE_AMOUNT,
                    column = @Column(name = EntityConstants.ALBUM_TRANSACTION_FEE_AMOUNT)),
            @AttributeOverride(name = EntityConstants.TRANSACTION_FEE_CURRENCY,
                    column = @Column(name = EntityConstants.ALBUM_TRANSACTION_FEE_CURRENCY)),
            @AttributeOverride(name = EntityConstants.TIER,
                    column = @Column(name = EntityConstants.ALBUM_PAYMENT_TIER))
    })
    private PaymentInfo paymentInfo;

    @AttributeOverrides({
            @AttributeOverride(name = EntityConstants.ARTIST_NAME,
                    column = @Column(name = EntityConstants.ALBUM_ARTIST_NAME)),
            @AttributeOverride(name = EntityConstants.PRODUCER_NAME,
                    column = @Column(name = EntityConstants.ALBUM_PRODUCER_NAME)),
            @AttributeOverride(name = EntityConstants.COMPOSER_NAME,
                    column = @Column(name = EntityConstants.ALBUM_COMPOSER_NAME))
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
     * Static builder method for creating a new album.
     *
     * @param albumName   - album's name
     * @param genre       - album's genre
     * @param albumInfo   - album's information
     * @param creator     - album's creator
     * @param paymentInfo - album's payment information
     * @param songs       - album's songs
     * @return the created album.
     */
    public static Album build(String albumName, Genre genre, AlbumInfo albumInfo,
                              Artist creator, PaymentInfo paymentInfo, List<Song> songs) {
        Album album = new Album();

        album.albumName = albumName;
        album.genre = genre;
        album.albumInfo = albumInfo;
        album.creator = creator;
        album.paymentInfo = paymentInfo;

        album.songs = songs;
        album.totalLength = SongLength.from(songs.stream()
                .mapToInt(song -> song.getSongLength().getLengthInSeconds())
                .sum());

        return album;
    }

    /**
     * Method used for raising an album's tier.
     *
     * @param paymentInfo - the information about a payment when raising an album's tier.
     */
    public void raiseTier(PaymentInfo paymentInfo) {
        Money subscriptionFeeSum = this.paymentInfo.getSubscriptionFee().add(paymentInfo.getSubscriptionFee());
        Money transactionFeeSum = this.paymentInfo.getTransactionFee().add(paymentInfo.getTransactionFee());

        this.paymentInfo = PaymentInfo.from(subscriptionFeeSum, transactionFeeSum, paymentInfo.getTier());
    }
}
