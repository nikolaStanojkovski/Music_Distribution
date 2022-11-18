package com.musicdistribution.storageservice.xport.rest;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import com.musicdistribution.sharedkernel.util.ApiController;
import com.musicdistribution.storageservice.constant.PathConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Tier Rest Controller.
 */
@ApiController
@RequestMapping(PathConstants.API_TIERS)
public class TierResource {

    /**
     * Method for fetching all the platform tiers.
     *
     * @return the list of the platform tiers.
     */
    @GetMapping
    public List<Tier> getTiers() {
        return List.of(Tier.values());
    }
}
