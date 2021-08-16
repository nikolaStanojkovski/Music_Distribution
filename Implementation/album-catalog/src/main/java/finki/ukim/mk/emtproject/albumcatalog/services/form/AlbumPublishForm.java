package finki.ukim.mk.emtproject.albumcatalog.services.form;

import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Money;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;

@Data
public class AlbumPublishForm {

    private String albumId;
    private String artistId;
    private String musicPublisherId;

    private Tier albumTier;
    private Double subscriptionFee; // in eur
    private Double transactionFee; // in eur

}
