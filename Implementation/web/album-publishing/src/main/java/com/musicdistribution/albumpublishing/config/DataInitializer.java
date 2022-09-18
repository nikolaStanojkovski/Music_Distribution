package com.musicdistribution.albumpublishing.config;

import com.musicdistribution.albumpublishing.services.MusicDistributorService;
import com.musicdistribution.albumpublishing.domain.models.request.MusicDistributorRequest;
import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Currency;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Data Initializer class.
 */
@Component
@AllArgsConstructor
public class DataInitializer {

    private final MusicDistributorService musicDistributorService;

    /**
     * Initial data initialization, for testing purposes.
     */
    @PostConstruct
    public void initData() {
        if (musicDistributorService.findAll().size() == 0) {
            musicDistributorService
                    .createDistributor(MusicDistributorRequest.build("DDD Nicktera",
                            "Tidal", Money.valueOf(Currency.EUR, 0.0)));
            musicDistributorService
                    .createDistributor(MusicDistributorRequest.build("DDD Nicktera",
                            "TeamViewer", Money.valueOf(Currency.EUR, 0.0)));
            musicDistributorService
                    .createDistributor(MusicDistributorRequest.build("DDD Open Source",
                            "SmileyFace", Money.valueOf(Currency.EUR, 0.0)));
            musicDistributorService
                    .createDistributor(MusicDistributorRequest.build("DDD Phonia",
                            "Tumblr", Money.valueOf(Currency.EUR, 0.0)));
            musicDistributorService
                    .createDistributor(MusicDistributorRequest.build("DDD Khilea",
                            "HolyRoler", Money.valueOf(Currency.EUR, 0.0)));

        }
    }
}
