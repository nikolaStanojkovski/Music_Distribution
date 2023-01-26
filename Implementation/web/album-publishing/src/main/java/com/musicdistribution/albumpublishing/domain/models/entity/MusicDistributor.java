package com.musicdistribution.albumpublishing.domain.models.entity;

import com.musicdistribution.albumpublishing.domain.valueobjects.DistributorInfo;
import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Currency;
import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Music distributor entity.
 */
@Getter
@Entity
@Table(name = "music_distributor")
public class MusicDistributor extends AbstractEntity<MusicDistributorId> {

    @AttributeOverrides({
            @AttributeOverride(name = "companyName", column = @Column(name = "distributor_companyName")),
            @AttributeOverride(name = "distributorName", column = @Column(name = "distributor_name"))
    })
    private DistributorInfo distributorInfo;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PublishedAlbum> publishedAlbums;

    /**
     * Protected no args constructor for the Music Distributor entity.
     */
    protected MusicDistributor() {
        super(MusicDistributorId.randomId(MusicDistributorId.class));
    }

    /**
     * Static method for creating a new music distributor.
     *
     * @param distributorInfo - music distributor's information for the distributor to be created.
     * @return the created music distributors.
     */
    public static MusicDistributor build(DistributorInfo distributorInfo) {
        MusicDistributor musicDistributor = new MusicDistributor();

        musicDistributor.distributorInfo = distributorInfo;
        musicDistributor.publishedAlbums = new ArrayList<>();

        return musicDistributor;
    }

    /**
     * Method used to calculate the total earnings of a music distributor.
     *
     * @return the total earnings in money.
     */
    public Money totalEarnings() {
        Money totalEarnings = Money.valueOf(Currency.EUR, 0.0);
        if (this.publishedAlbums != null && this.publishedAlbums.size() != 0) {
            for (PublishedAlbum p : publishedAlbums) {
                totalEarnings = totalEarnings.add(p.earningsPerAlbum());
            }
        }

        return totalEarnings;
    }

    /**
     * Method for publishing a new album to the music distributor.
     *
     * @param newAlbum - the new album to be published.
     */
    public void subscribeAlbum(PublishedAlbum newAlbum) {
        this.publishedAlbums.add(newAlbum);
    }

    /**
     * Method for an album unpublished.
     *
     * @param album - the new album to be unpublished.
     */
    public void unsubscribeAlbum(PublishedAlbum album) {
        this.publishedAlbums.remove(album);
    }
}
