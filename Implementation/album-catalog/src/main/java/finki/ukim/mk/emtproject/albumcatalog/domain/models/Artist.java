package finki.ukim.mk.emtproject.albumcatalog.domain.models;

import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.ArtistContactInfo;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.ArtistPersonalInfo;
import finki.ukim.mk.emtproject.sharedkernel.domain.base.AbstractEntity;
import lombok.Getter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="artist")
@Getter
public class Artist extends AbstractEntity<ArtistId> {

    @AttributeOverrides({
            @AttributeOverride(name="email", column = @Column(name="artist_email")),
            @AttributeOverride(name="telephoneNumber", column = @Column(name="artist_telephoneNumber"))
    })
    private ArtistContactInfo artistContactInfo;

    @AttributeOverrides({
            @AttributeOverride(name="firstName", column = @Column(name="artist_firstName")),
            @AttributeOverride(name="lastName", column = @Column(name="artist_lastName")),
            @AttributeOverride(name="artName", column = @Column(name="artist_artName"))
    })
    private ArtistPersonalInfo artistPersonalInfo;

    @Column(nullable = false)
    private String password;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Album> albums;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Song> songs;


    private Artist() {
        super(ArtistId.randomId(ArtistId.class));
    }

    public static Artist build(ArtistContactInfo artistContactInfo, ArtistPersonalInfo artistPersonalInfo, String password) {
        Artist artist = new Artist();
        artist.artistContactInfo = artistContactInfo;
        artist.artistPersonalInfo = artistPersonalInfo;
        artist.password = password;

        artist.albums = new ArrayList<>();
        artist.songs = new ArrayList<>();

        return artist;
    }

    public Album createAlbum(Album album) {
        this.albums.add(album);

        return album;
    }

    public Song addSongToArtist(Song song) {
        this.songs.add(song);

        return song;
    }
}
