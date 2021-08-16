package finki.ukim.mk.emtproject.albumcatalog.config;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Album;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.Artist;
import finki.ukim.mk.emtproject.albumcatalog.domain.models.Song;
import finki.ukim.mk.emtproject.albumcatalog.domain.repository.AlbumRepository;
import finki.ukim.mk.emtproject.albumcatalog.domain.repository.ArtistRepository;
import finki.ukim.mk.emtproject.albumcatalog.domain.repository.SongRepository;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.AlbumInfo;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.ArtistContactInfo;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.ArtistPersonalInfo;
import finki.ukim.mk.emtproject.albumcatalog.domain.valueobjects.SongLength;
import finki.ukim.mk.emtproject.albumcatalog.services.AlbumService;
import finki.ukim.mk.emtproject.albumcatalog.services.ArtistService;
import finki.ukim.mk.emtproject.albumcatalog.services.SongService;
import finki.ukim.mk.emtproject.albumcatalog.services.form.AlbumForm;
import finki.ukim.mk.emtproject.albumcatalog.services.form.ArtistForm;
import finki.ukim.mk.emtproject.albumcatalog.services.form.SongForm;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.EmailDomain;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final ArtistService artistService;
    private final AlbumService albumService;
    private final SongService songService;

    @PostConstruct
    public void initData() {

        if(artistService.findAll().size() == 0) {
            Artist artist1 = artistService.createArtist(ArtistForm.build("nikola.stanojkovski", EmailDomain.gmail, "078-916-999", "Nikola", "Stanojkovski", "Agremo", "mst-1")).get();
            Artist artist2 = artistService.createArtist(ArtistForm.build("trajko.trajanovski", EmailDomain.gmail, "077-911-998", "Trajko", "Trajkovski", "Agseto", "mst-2")).get();

            Album a1 = albumService.createAlbum(AlbumForm.build("Album 1", 0, false, Genre.Jazz,"Agro", "Agro", "Agro", artist1.getId().getId())).get();
            Album a2 = albumService.createAlbum(AlbumForm.build("Album 2", 0, false, Genre.Rock,"Wa", "Wa", "Wa", artist1.getId().getId())).get();
            Album a3 = albumService.createAlbum(AlbumForm.build("Album 3", 0, false, Genre.Pop,"Mk", "Mk", "Mk", artist2.getId().getId())).get();

            Song s1 = songService.createSong(SongForm.build("Forandra", false, 187, artist1.getId().getId(), a1.getId().getId())).get();
            Song s2 = songService.createSong(SongForm.build("Jokawa", false, 190, artist2.getId().getId(), a3.getId().getId())).get();
            Song s3 = songService.createSong(SongForm.build("Mikaragwa", false, 120, artist1.getId().getId(), a1.getId().getId())).get();
            Song s4 = songService.createSong(SongForm.build("Seneda", true, 217, artist1.getId().getId(), a1.getId().getId())).get();
            Song s5 = songService.createSong(SongForm.build("Norma", true, 127, artist1.getId().getId(), a1.getId().getId())).get();
        }
    }
}
