package finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.ValueObject;
import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * DistributorInfo - value object that keeps the main information for the music distributor
 */
@Embeddable
@Getter
public class DistributorInfo implements ValueObject {

    private String companyName;

    private String distributorName;

    public static DistributorInfo build(String companyName, String distributorName) {
        return new DistributorInfo(companyName, distributorName);
    }

    public DistributorInfo(String companyName, String distributorName) {
        this.companyName = companyName;
        this.distributorName = distributorName;
    }

    protected DistributorInfo() {
    }

    public String getDistributorInformation() {
        return distributorName + " - " + companyName;
    }
}
