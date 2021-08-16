package finki.ukim.mk.emtproject.albumpublishing.domain.models;

import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbumId;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.Album;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.AlbumId;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.Artist;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.ArtistId;
import finki.ukim.mk.emtproject.sharedkernel.domain.base.AbstractEntity;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Money;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="published_album")
@Getter
public class PublishedAlbum extends AbstractEntity<PublishedAlbumId> {

    @AttributeOverrides({
            @AttributeOverride(name="id", column = @Column(name="album_id")),
            @AttributeOverride(name="albumName", column = @Column(name="album_albumName")),
            @AttributeOverride(name="totalLength", column = @Column(name="album_totalLength")),
            @AttributeOverride(name="genre", column = @Column(name="album_genre")),
    })
    private Album album;

    @AttributeOverrides({
            @AttributeOverride(name="id", column = @Column(name="album_id")),
            @AttributeOverride(name="albumName", column = @Column(name="album_albumName")),
            @AttributeOverride(name="totalLength", column = @Column(name="album_totalLength")),
            @AttributeOverride(name="genre", column = @Column(name="album_genre")),
    })
    private Artist artist;


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

    public static PublishedAlbum build(Album album, Artist artist, MusicDistributor distributor, Money subscriptionFee, Tier albumTier, Money transactionFee) {
        PublishedAlbum publishedAlbum = new PublishedAlbum();

        publishedAlbum.album = album;
        publishedAlbum.artist = artist;
        publishedAlbum.publisher = distributor;
        publishedAlbum.subscriptionFee = subscriptionFee;
        publishedAlbum.publishedOn = Instant.now();
        publishedAlbum.albumTier = albumTier;
        publishedAlbum.transactionFee = transactionFee;

        return publishedAlbum;
    }

    public Money earningsPerAlbum() {
        return this.subscriptionFee.add(this.transactionFee);
        // the sum of subscription and transaction fee is the earning of the specific published album
    }
}
