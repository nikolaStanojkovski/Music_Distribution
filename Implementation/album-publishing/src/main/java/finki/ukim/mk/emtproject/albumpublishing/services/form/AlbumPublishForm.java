package finki.ukim.mk.emtproject.albumpublishing.services.form;

import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.Data;

/**
 * AlbumPublishForm - object used to transfer data from the front-end user form to the backend
 */
@Data
public class AlbumPublishForm {

    private String albumId;
    private String artistId;
    private String musicPublisherId;

    private Tier albumTier;
    private Double subscriptionFee; // in eur
    private Double transactionFee; // in eur

}
