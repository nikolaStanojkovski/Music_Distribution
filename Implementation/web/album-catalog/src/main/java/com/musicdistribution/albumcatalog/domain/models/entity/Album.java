package com.musicdistribution.albumcatalog.domain.models.entity;

import com.musicdistribution.albumcatalog.domain.valueobjects.AlbumInfo;
import com.musicdistribution.albumcatalog.domain.valueobjects.PaymentInfo;
import com.musicdistribution.albumcatalog.domain.valueobjects.SongLength;
import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
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
            @AttributeOverride(name = "subscriptionFee.amount", column = @Column(name = "payment_subscription_fee_amount")),
            @AttributeOverride(name = "subscriptionFee.currency", column = @Column(name = "payment_subscription_fee_amount_currency")),
            @AttributeOverride(name = "transactionFee.amount", column = @Column(name = "payment_transaction_fee_amount")),
            @AttributeOverride(name = "transactionFee.currency", column = @Column(name = "payment_transaction_fee_currency")),
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
     * @param albumName   - the album's name
     * @param genre       - the album's genre
     * @param albumInfo   - the album's information
     * @param creator     - the album's creator
     * @param paymentInfo - the album's payment information
     * @param songs       - the album's songs
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
        album.totalLength = SongLength.build(songs.stream()
                .mapToInt(song -> song.getSongLength().getLengthInSeconds())
                .sum());

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
     * Method used for raising album's tier.
     *
     * @param paymentInfo - the payment information for raising album's tier.
     */
    public void raiseTier(PaymentInfo paymentInfo) {
        Money subscriptionFeeSum = this.paymentInfo.getSubscriptionFee().add(paymentInfo.getSubscriptionFee());
        Money transactionFeeSum = this.paymentInfo.getTransactionFee().add(paymentInfo.getTransactionFee());

        this.paymentInfo = PaymentInfo.build(subscriptionFeeSum, transactionFeeSum, paymentInfo.getTier());
    }
}
