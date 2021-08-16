package finki.ukim.mk.emtproject.albumpublishing.domain.models;

import finki.ukim.mk.emtproject.albumpublishing.domain.models.MusicDistributorId;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbum;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.Album;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.ArtistId;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.DistributorInfo;
import finki.ukim.mk.emtproject.sharedkernel.domain.base.AbstractEntity;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Money;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Currency;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="music_distributor")
@Getter
public class MusicDistributor extends AbstractEntity<MusicDistributorId> {

    @AttributeOverrides({
            @AttributeOverride(name="companyName", column = @Column(name="distributor_companyName")),
            @AttributeOverride(name="distributorName", column = @Column(name="distributor_name"))
    })
    private DistributorInfo distributorInfo;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PublishedAlbum> publishedAlbums;


    private MusicDistributor() {
        super(MusicDistributorId.randomId(MusicDistributorId.class));
    }

    public static MusicDistributor build(DistributorInfo distributorInfo) {
        MusicDistributor musicDistributor = new MusicDistributor();

        musicDistributor.distributorInfo = distributorInfo;
        musicDistributor.publishedAlbums = new ArrayList<>();

        return musicDistributor;
    }


    public Money totalEarnings() {
        Money totalEarnings = Money.valueOf(Currency.EUR, 0.0);
        if(this.publishedAlbums != null && this.publishedAlbums.size() != 0) {
            this.publishedAlbums.forEach(i -> totalEarnings.add(i.earningsPerAlbum()));
        }

        return totalEarnings;
    }

    public PublishedAlbum subscribeAlbum(PublishedAlbum newAlbum) {
        this.publishedAlbums.add(newAlbum);
        // add a new album to the list
        return newAlbum;
    }

    public PublishedAlbum unsubscribeAlbum(PublishedAlbum album) {
        this.publishedAlbums.remove(album);
        // remove the album from the subscribed list
        return album;
    }
}
