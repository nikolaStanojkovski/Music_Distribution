package finki.ukim.mk.emtproject.albumpublishing.domain.models;

import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.DistributorInfo;
import finki.ukim.mk.emtproject.sharedkernel.domain.base.AbstractEntity;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Money;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Currency;
import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain entity for a Music Distributor
 */
@Entity
@Table(name="music_distributor")
@Getter
public class MusicDistributor extends AbstractEntity<MusicDistributorId> {

    /**
     * Required properties defintiion
     */

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

    /**
     * Methods used for defining the consistency rules
     */

    // calculate the total earnings of the music distributor
    public Money totalEarnings() {
        Money totalEarnings = Money.valueOf(Currency.EUR, 0.0);
        if(this.publishedAlbums != null && this.publishedAlbums.size() != 0) {
            for(PublishedAlbum p : publishedAlbums) {
                totalEarnings = totalEarnings.add(p.earningsPerAlbum());
            }
        }

        return totalEarnings;
    }

    // add a new album to the list
    public PublishedAlbum subscribeAlbum(PublishedAlbum newAlbum) {
        this.publishedAlbums.add(newAlbum);
        return newAlbum;
    }

    // remove the album from the subscribed list
    public PublishedAlbum unsubscribeAlbum(PublishedAlbum album) {
        this.publishedAlbums.remove(album);
        return album;
    }
}
