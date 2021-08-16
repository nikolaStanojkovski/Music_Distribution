package finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.ValueObject;
import lombok.Getter;

import javax.persistence.Embeddable;

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

    public void changeCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void changeDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getDistributorInformation() {
        return distributorName + " - " + companyName;
    }
}
