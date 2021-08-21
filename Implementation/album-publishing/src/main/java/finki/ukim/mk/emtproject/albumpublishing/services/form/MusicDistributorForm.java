package finki.ukim.mk.emtproject.albumpublishing.services.form;

import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.DistributorInfo;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Money;
import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;

/**
 * MusicDistributorForm - object used to transfer data from the front-end user form to the backend
 */
@Data
public class MusicDistributorForm {

    private String companyName;

    private String distributorName;

    private Money totalEarned;

    private MusicDistributorForm() {
    }

    public static MusicDistributorForm build(String companyName, String distributorName, Money totalEarned) {
        MusicDistributorForm musicDistributorForm = new MusicDistributorForm();

        musicDistributorForm.companyName = companyName;
        musicDistributorForm.distributorName = distributorName;
        musicDistributorForm.totalEarned = totalEarned;

        return musicDistributorForm;
    }
}
