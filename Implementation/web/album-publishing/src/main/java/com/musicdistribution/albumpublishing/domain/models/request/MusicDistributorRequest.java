package com.musicdistribution.albumpublishing.domain.models.request;

import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Object used to transfer data from.
 */
@Data
@NoArgsConstructor
public class MusicDistributorRequest {

    private String companyName;
    private String distributorName;
    private Money totalEarned;

    /**
     * Static method used for creation of a music distributor request object.
     *
     * @param companyName - music distributor's company name.
     * @param distributorName - music distributor's name.
     * @param totalEarned - music distributor's total earned money.
     * @return the created music distributor request object.
     */
    public static MusicDistributorRequest build(String companyName, String distributorName, Money totalEarned) {
        MusicDistributorRequest musicDistributorRequest = new MusicDistributorRequest();
        musicDistributorRequest.setCompanyName(companyName);
        musicDistributorRequest.setDistributorName(distributorName);
        musicDistributorRequest.setTotalEarned(totalEarned);

        return musicDistributorRequest;
    }
}
