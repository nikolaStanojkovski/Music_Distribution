package com.musicdistribution.albumpublishing.xport.rest;

import com.musicdistribution.albumpublishing.domain.models.entity.PublishedAlbum;
import com.musicdistribution.albumpublishing.domain.models.entity.PublishedAlbumId;
import com.musicdistribution.albumpublishing.domain.models.request.PublishedAlbumRequest;
import com.musicdistribution.albumpublishing.domain.models.response.MusicDistributorResponse;
import com.musicdistribution.albumpublishing.services.MusicDistributorService;
import com.musicdistribution.sharedkernel.util.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Music Distributors Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping("/api/distributors")
public class MusicDistributorResource {

    private final MusicDistributorService musicDistributorService;

    /**
     * Method for getting all music distributors.
     *
     * @return a list of music distributors.
     */
    @GetMapping
    public List<MusicDistributorResponse> getAll() {
        return musicDistributorService.findAll().stream().map(MusicDistributorResponse::from).collect(Collectors.toList());
    }

    /**
     * Method used for publishing an album.
     *
     * @param publishedAlbumRequest - object containing all needed information for publishing an album.
     * @return an empty response entity with status code 200.
     */
    @PostMapping("/publish")
    public ResponseEntity<Void> publishAlbum(@RequestBody PublishedAlbumRequest publishedAlbumRequest) {
        Optional<PublishedAlbum> publishedAlbum = this.musicDistributorService.publishAlbum(publishedAlbumRequest);
        return publishedAlbum.isPresent() ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method used for making an album unpublished.
     *
     * @param publishedAlbumId - published album's id.
     * @return an empty response entity with status code 200.
     */
    @PostMapping("/unPublish")
    public ResponseEntity<Void> unPublishAlbum(@RequestBody String publishedAlbumId) {
        Optional<PublishedAlbum> publishedAlbum = this.musicDistributorService.unPublishAlbum(PublishedAlbumId.of(publishedAlbumId));
        return publishedAlbum.isPresent() ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method used for raising an album's tier.
     *
     * @param publishedAlbumRequest - object containing all needed information for raising an album's tier.
     * @return an empty response entity with status code 200.
     */
    @PostMapping("/raiseAlbumTier")
    public ResponseEntity<Void> raiseAlbumTier(@RequestBody PublishedAlbumRequest publishedAlbumRequest) {
        Optional<PublishedAlbum> publishedAlbum = this.musicDistributorService.raiseAlbumTier(publishedAlbumRequest);
        return publishedAlbum.isPresent() ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
