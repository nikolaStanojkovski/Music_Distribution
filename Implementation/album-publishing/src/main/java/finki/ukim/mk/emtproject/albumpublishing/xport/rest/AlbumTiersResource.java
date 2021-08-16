package finki.ukim.mk.emtproject.albumpublishing.xport.rest;

import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/albumTiers")
@AllArgsConstructor
public class AlbumTiersResource {

    @GetMapping
    public List<Tier> getAlbumTiers() {
        return List.of(Tier.values());
    }
}
