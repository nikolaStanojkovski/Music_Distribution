package finki.ukim.mk.emtproject.albumpublishing.config;

import finki.ukim.mk.emtproject.albumpublishing.domain.models.MusicDistributor;
import finki.ukim.mk.emtproject.albumpublishing.services.MusicDistributorService;
import finki.ukim.mk.emtproject.albumpublishing.services.PublishedAlbumService;
import finki.ukim.mk.emtproject.albumpublishing.services.form.MusicDistributorForm;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Money;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Currency;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Initial data definition, for testing purposes
 */
@Component
@AllArgsConstructor
public class DataInitializer {

    private final MusicDistributorService musicDistributorService;
    private final PublishedAlbumService publishedAlbumService;

    /**
     * Initial data definition, for testing purposes
     */
    @PostConstruct
    public void initData() {
        if(musicDistributorService.findAll().size() == 0) {
            MusicDistributor musicDistributor1 = musicDistributorService
                    .createDistributor(MusicDistributorForm.build("DDD Nicktera",
                            "Tidal", Money.valueOf(Currency.EUR, 0.0)));
            MusicDistributor musicDistributor2 = musicDistributorService
                    .createDistributor(MusicDistributorForm.build("DDD Nicktera",
                            "TeamViewer", Money.valueOf(Currency.EUR, 0.0)));
            MusicDistributor musicDistributor3 = musicDistributorService
                    .createDistributor(MusicDistributorForm.build("DDD Open Source",
                            "SmileyFace", Money.valueOf(Currency.EUR, 0.0)));
            MusicDistributor musicDistributor4 = musicDistributorService
                    .createDistributor(MusicDistributorForm.build("DDD Phonia",
                            "Tumblr", Money.valueOf(Currency.EUR, 0.0)));
            MusicDistributor musicDistributor5 = musicDistributorService
                    .createDistributor(MusicDistributorForm.build("DDD Khilea",
                            "HolyRoler", Money.valueOf(Currency.EUR, 0.0)));

        }
    }
}
