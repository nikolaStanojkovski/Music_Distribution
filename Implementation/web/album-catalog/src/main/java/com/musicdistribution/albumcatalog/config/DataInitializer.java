package com.musicdistribution.albumcatalog.config;

import com.musicdistribution.albumcatalog.services.AlbumService;
import com.musicdistribution.albumcatalog.services.ArtistService;
import com.musicdistribution.albumcatalog.services.SongService;
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
//            Artist artist1 = artistService.registerArtist(ArtistRequest.build(Artist.build(ArtistContactInfo.build("078-916-999", "nikola.stanojkovski", EmailDomain.gmail),
//                    ArtistPersonalInfo.build("Nikola", "Stanojkovski", "Agremorta"), "mst-2"))).orElse(null);
//            Artist artist2 = artistService.registerArtist(ArtistRequest.build(Artist.build(ArtistContactInfo.build("077-911-998", "trajko.trajanovski", EmailDomain.gmail),
//                    ArtistPersonalInfo.build("Trajko", "Trajkovski", "Higretorta"), "mst-4"))).orElse(null);
//
//            Album album1 = albumService.createAlbum(AlbumShortTransactionRequest.build(Album.build("Album 1", Genre.Jazz, AlbumInfo.build("Agro", "Agro", "Agro"), artist1))).orElse(null);
//            albumService.createAlbum(AlbumShortTransactionRequest.build(Album.build("Album 2", Genre.Rock, AlbumInfo.build("Wa", "Ki", "Ca"), artist2)));
//            albumService.createAlbum(AlbumShortTransactionRequest.build(Album.build("Album 3", Genre.Pop, AlbumInfo.build("Mk", "Mk", "Mk"), artist1)));

//            Song song1 = Song.build("Forandra", artist1, album1, SongLength.build(187), Genre.Funk, "/files/0000000001.zip");
//            Song song2 = Song.build("Jokawa", artist1, album1, SongLength.build(201), Genre.Jazz, "/files/0000000002.zip");
//            Song song3 = Song.build("Mikaragwa", artist1, album1, SongLength.build(172), Genre.Metal, "/files/0000000003.zip");
//            Song song4 = Song.build("Seneda", artist2, album1, SongLength.build(255), Genre.Rock, "/files/0000000004.zip");
//            Song song5 = Song.build("Norma", artist2, album1, SongLength.build(176), Genre.Metal, "/files/0000000005.zip");
//            Song song6 = Song.build("Norma", artist1, null, SongLength.build(176), Genre.Rock, "/files/0000000006.zip");
//            songService.createSong(SongRequest.build(song1));
//            songService.createSong(SongRequest.build(song2));
//            songService.createSong(SongRequest.build(song3));
//            songService.createSong(SongRequest.build(song4));
//            songService.createSong(SongRequest.build(song5));
//            songService.createSong(SongRequest.build(song6));
        }
    }
}
