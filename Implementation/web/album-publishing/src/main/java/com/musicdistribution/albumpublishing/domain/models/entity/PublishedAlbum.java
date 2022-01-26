package com.musicdistribution.albumpublishing.domain.models.entity;

import com.musicdistribution.albumpublishing.domain.valueobjects.AlbumId;
import com.musicdistribution.albumpublishing.domain.valueobjects.ArtistId;
import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Currency;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.Instant;

/**
 * Published album entity.
 */
@Entity
@Table(name = "published_album")
@Getter
public class PublishedAlbum extends AbstractEntity<PublishedAlbumId> {

    @AttributeOverride(name = "id", column = @Column(name = "album_id"))
    private AlbumId albumId;

    private String albumName;

    @AttributeOverride(name = "id", column = @Column(name = "artist_id"))
    private ArtistId artistId;

    private String artistInformation;

    private Instant publishedOn;

    @Enumerated(EnumType.STRING)
    private Tier albumTier;

    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "fee_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "fee_currency"))
    })
    private Money subscriptionFee;

    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "transaction_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "transaction_currency"))
    })
    private Money transactionFee;


    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private MusicDistributor publisher;

    /**
     * Protected no args constructor for the Published Album entity.
     */
    protected PublishedAlbum() {
        super(PublishedAlbumId.randomId(PublishedAlbumId.class));
    }

    /**
     * Static method for creating a new music distributor.
     *
     * @param albumId           - album's id.
     * @param albumName         - album's name.
     * @param artistId          - artist's id.
     * @param artistInformation - artist's information.
     * @param distributor       - album's music distributor.
     * @param subscriptionFee   - album's subscription fee.
     * @param albumTier         - album's tier.
     * @param transactionFee    - album's transaction fee.
     * @return the created published album.
     */
    public static PublishedAlbum build(AlbumId albumId, String albumName, ArtistId artistId, String artistInformation, MusicDistributor distributor, Money subscriptionFee, Tier albumTier, Money transactionFee) {
        PublishedAlbum publishedAlbum = new PublishedAlbum();

        publishedAlbum.albumId = albumId;
        publishedAlbum.albumName = albumName;
        publishedAlbum.artistId = artistId;
        publishedAlbum.artistInformation = artistInformation;
        publishedAlbum.publisher = distributor;
        publishedAlbum.subscriptionFee = subscriptionFee;
        publishedAlbum.publishedOn = Instant.now();
        publishedAlbum.albumTier = albumTier;
        publishedAlbum.transactionFee = transactionFee;

        return publishedAlbum;
    }

    /**
     * Method used to calculate the earnings per published album for the music distributor.
     * @return the earnings per published album.
     */
    public Money earningsPerAlbum() {
        return Money.valueOf(this.subscriptionFee.getCurrency(), 0.0)
                .add(this.subscriptionFee)
                .add(this.transactionFee);
    }

    /**
     * Method used to raise album's tier
     *
     * @param tier - album's new tier.
     * @param subscriptionFee - album's subscription fee.
     * @param transactionFee - album's transaction fee.
     */
    public void raiseAlbumTier(Tier tier, Double subscriptionFee, Double transactionFee) {
        this.albumTier = tier;
        this.subscriptionFee = Money.valueOf(Currency.EUR, subscriptionFee);
        this.transactionFee = Money.valueOf(Currency.EUR, transactionFee);
    }
}
