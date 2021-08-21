package finki.ukim.mk.emtproject.albumpublishing.domain.models;

import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.AlbumId;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.ArtistId;
import finki.ukim.mk.emtproject.sharedkernel.domain.base.AbstractEntity;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Money;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Currency;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.Instant;

/**
 * Domain entity for a Published Album
 */
@Entity
@Table(name="published_album")
@Getter
public class PublishedAlbum extends AbstractEntity<PublishedAlbumId> {

    /**
     * Required properties definition
     */

    // album
    @AttributeOverride(name = "id", column = @Column(name = "album_id"))
    private AlbumId albumId;
    private String albumName;

    // artist
    @AttributeOverride(name = "id", column = @Column(name = "artist_id"))
    private ArtistId artistId;
    private String artistInformation;


    private Instant publishedOn;

    @Enumerated(EnumType.STRING)
    private Tier albumTier;

    @AttributeOverrides({
            @AttributeOverride(name="amount", column = @Column(name="fee_amount")),
            @AttributeOverride(name="currency", column = @Column(name="fee_currency"))
    })
    private Money subscriptionFee;

    @AttributeOverrides({
            @AttributeOverride(name="amount", column = @Column(name="transaction_amount")),
            @AttributeOverride(name="currency", column = @Column(name="transaction_currency"))
    })
    private Money transactionFee;


    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private MusicDistributor publisher;


    private PublishedAlbum() {
        super(PublishedAlbumId.randomId(PublishedAlbumId.class));
    }

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
     * Methods used for defining the consistency rules
     */

    // calculate the total earnings of the album
    public Money earningsPerAlbum() {
        return Money.valueOf(this.subscriptionFee.getCurrency(), 0.0)
                .add(this.subscriptionFee)
                .add(this.transactionFee);
        // the sum of subscription and transaction fee is the earning of the specific published album
    }

    // raise the tier of the album
    public Tier raiseAlbumTier(Tier tier, Double subscriptionFee, Double transactionFee) {
        this.albumTier = tier;
        this.subscriptionFee = Money.valueOf(Currency.EUR, subscriptionFee);
        this.transactionFee = Money.valueOf(Currency.EUR, transactionFee);
        return tier;
    }
}
