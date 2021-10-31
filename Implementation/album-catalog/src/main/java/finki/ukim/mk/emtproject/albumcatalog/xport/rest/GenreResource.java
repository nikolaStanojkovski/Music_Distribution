package finki.ukim.mk.emtproject.albumcatalog.xport.rest;

import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Genre;
import finki.ukim.mk.emtproject.sharedkernel.util.ApiController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Genre Rest Controller.
 */
@ApiController
@RequestMapping("/api/genres")
public class GenreResource {

    /**
     * Method for getting all the genres.
     *
     * @return the list of genres.
     */
    @GetMapping
    public List<Genre> getGenres() {
        return List.of(Genre.values());
    }
}
