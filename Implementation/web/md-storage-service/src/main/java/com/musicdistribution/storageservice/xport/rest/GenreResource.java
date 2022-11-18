package com.musicdistribution.storageservice.xport.rest;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Genre;
import com.musicdistribution.sharedkernel.util.ApiController;
import com.musicdistribution.storageservice.constant.PathConstants;
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
