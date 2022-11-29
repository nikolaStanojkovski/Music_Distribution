package com.musicdistribution.streamingservice.xport.rest.enums;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import com.musicdistribution.sharedkernel.config.ApiController;
import com.musicdistribution.streamingservice.constant.PathConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Genre Rest Controller.
 */
@ApiController
@RequestMapping(PathConstants.API_GENRES)
public class GenreResource {

    /**
     * Method used for fetching all the genres.
     *
     * @return the list of genres.
     */
    @GetMapping
    public List<Genre> getGenres() {
        return List.of(Genre.values());
    }
}
