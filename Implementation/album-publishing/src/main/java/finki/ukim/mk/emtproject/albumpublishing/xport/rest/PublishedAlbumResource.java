package finki.ukim.mk.emtproject.albumpublishing.xport.rest;

import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbum;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.dto.MusicDistributorDto;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.dto.PublishedAlbumDto;
import finki.ukim.mk.emtproject.albumpublishing.services.MusicDistributorService;
import finki.ukim.mk.emtproject.albumpublishing.services.PublishedAlbumService;
import finki.ukim.mk.emtproject.albumpublishing.services.form.AlbumPublishForm;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/publishedAlbums")
@AllArgsConstructor
public class PublishedAlbumResource {

    private final PublishedAlbumService publishedAlbumService;

    @GetMapping
    public List<PublishedAlbumDto> getAll() {
        return publishedAlbumService.findAll();
    }
}
