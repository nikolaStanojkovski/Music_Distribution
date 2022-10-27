package com.musicdistribution.albumcatalog.xport.rest.auxiliary;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import com.musicdistribution.sharedkernel.util.ApiController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Tier Rest Controller.
 */
@ApiController
@RequestMapping("/api/tiers")
public class TierResource {

    /**
     * Method for getting all the tiers.
     *
     * @return the list of platform tiers.
     */
    @GetMapping
    public List<Tier> getTiers() {
        return List.of(Tier.values());
    }
}
