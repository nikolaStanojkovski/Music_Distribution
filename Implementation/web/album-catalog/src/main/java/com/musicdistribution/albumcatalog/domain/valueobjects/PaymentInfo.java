package com.musicdistribution.albumcatalog.domain.valueobjects;

import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * Value object for a payment information.
 */
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo {

    private String subscriptionFee;
    private String transactionFee;
    private Tier tier;

    /**
     * Static method for creating a new album information object.
     *
     * @param subscriptionFee - the subscription fee of the payment.
     * @param transactionFee  - the subscription fee of the payment.
     * @param tier            - the tier of the publishing which is being made.
     * @return the payment information object.
     */
    public static PaymentInfo build(String subscriptionFee, String transactionFee, Tier tier) {
        return new PaymentInfo(subscriptionFee, transactionFee, tier);
    }
}
