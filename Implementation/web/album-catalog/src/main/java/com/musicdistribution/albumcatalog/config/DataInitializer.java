package com.musicdistribution.albumcatalog.config;

import com.musicdistribution.albumcatalog.domain.models.entity.Album;
import com.musicdistribution.albumcatalog.domain.models.entity.Artist;
import com.musicdistribution.albumcatalog.domain.models.entity.Song;
import com.musicdistribution.albumcatalog.domain.models.request.AlbumRequest;
import com.musicdistribution.albumcatalog.domain.models.request.ArtistRequest;
import com.musicdistribution.albumcatalog.domain.models.request.SongRequest;
import com.musicdistribution.albumcatalog.domain.valueobjects.AlbumInfo;
import com.musicdistribution.albumcatalog.domain.valueobjects.ArtistContactInfo;
import com.musicdistribution.albumcatalog.domain.valueobjects.ArtistPersonalInfo;
import com.musicdistribution.albumcatalog.domain.valueobjects.SongLength;
import com.musicdistribution.albumcatalog.services.AlbumService;
import com.musicdistribution.albumcatalog.services.ArtistService;
import com.musicdistribution.albumcatalog.services.SongService;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Data Initializer class.
 */
@Component
@AllArgsConstructor
public class DataInitializer {

    private final ArtistService artistService;
    private final AlbumService albumService;
    private final SongService songService;

    /**
     * Initial data initialization, for testing purposes.
     */
    @PostConstruct
    public void initData() {

        if (artistService.findAll().size() == 0) {
            Artist artist1 = Artist.build(ArtistContactInfo.build("078-916-999", "nikola.stanojkovski", EmailDomain.gmail),
                    ArtistPersonalInfo.build("Nikola", "Stanojkovski", "Agremorta"), "mst-2");
            Artist artist2 = Artist.build(ArtistContactInfo.build("077-911-998", "trajko.trajanovski", EmailDomain.gmail),
                    ArtistPersonalInfo.build("Trajko", "Trajkovski", "Higretorta"), "mst-4");
            artist1 = artistService.createArtist(ArtistRequest.build(artist1)).get();
            artist2 = artistService.createArtist(ArtistRequest.build(artist2)).get();

            Album album1 = Album.build("Album 1", Genre.Jazz, AlbumInfo.build("Agro", "Agro", "Agro"), artist1);
            Album album2 = Album.build("Album 2", Genre.Rock, AlbumInfo.build("Wa", "Ki", "Ca"), artist2);
            Album album3 = Album.build("Album 3", Genre.Pop, AlbumInfo.build("Mk", "Mk", "Mk"), artist1);
            album1 = albumService.createAlbum(AlbumRequest.build(album1)).get();
            albumService.createAlbum(AlbumRequest.build(album2)).get();
            albumService.createAlbum(AlbumRequest.build(album3)).get();

            Song song1 = Song.build("Forandra", artist1, album1, SongLength.build(187));
            Song song2 = Song.build("Jokawa", artist1, album1, SongLength.build(201));
            Song song3 = Song.build("Mikaragwa", artist1, album1, SongLength.build(172));
            Song song4 = Song.build("Seneda", artist1, album1, SongLength.build(255));
            Song song5 = Song.build("Norma", artist1, album1, SongLength.build(176));
            songService.createSong(SongRequest.build(song1));
            songService.createSong(SongRequest.build(song2));
            songService.createSong(SongRequest.build(song3));
            songService.createSong(SongRequest.build(song4));
            songService.createSong(SongRequest.build(song5));
        }
    }
}
