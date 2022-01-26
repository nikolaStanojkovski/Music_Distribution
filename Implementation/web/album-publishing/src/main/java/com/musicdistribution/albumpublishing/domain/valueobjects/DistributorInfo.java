package com.musicdistribution.albumpublishing.domain.valueobjects;

import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * A value object that keeps the main information for the music distributor.
 */
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class DistributorInfo implements ValueObject {

    private String companyName;
    private String distributorName;

    /**
     * Static method used for creation of a new distributor information object.
     *
     * @param companyName     - music distributor's company name.
     * @param distributorName - music distributor's name.
     * @return the created DistributorInfo object.
     */
    public static DistributorInfo build(String companyName, String distributorName) {
        return new DistributorInfo(companyName, distributorName);
    }

    /**
     * Getter method for the distributor information including the distributor name and company.
     *
     * @return the distributor information.
     */
    public String getDistributorInformation() {
        return distributorName + " - " + companyName;
    }
}
