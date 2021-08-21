package finki.ukim.mk.emtproject.albumpublishing.xport.rest;

import finki.ukim.mk.emtproject.albumpublishing.domain.models.dto.PublishedAlbumDto;
import finki.ukim.mk.emtproject.albumpublishing.services.PublishedAlbumService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * PublishedAlbumResource - Rest Controller for the published album methods that communicate with the front-end app
 */
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
