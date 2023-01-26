package com.musicdistribution.albumpublishing.xport.rest;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import com.musicdistribution.sharedkernel.util.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Album Tiers Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping("/api/albumTiers")
public class AlbumTiersResource {

    @GetMapping
    public List<Tier> getAlbumTiers() {
        return List.of(Tier.values());
    }
}
