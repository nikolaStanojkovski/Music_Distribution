package com.musicdistribution.storageservice.domain.valueobject;

import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Value object for a payment information.
 */
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo implements Serializable {

    private Money subscriptionFee;
    private Money transactionFee;
    private Tier tier;

    /**
     * Static method for creating a new album information object.
     *
     * @param subscriptionFee - the subscription fee of the payment.
     * @param transactionFee  - the subscription fee of the payment.
     * @param tier            - the tier of the publishing which is being made.
     * @return the payment information object.
     */
    public static PaymentInfo build(Money subscriptionFee, Money transactionFee, Tier tier) {
        return new PaymentInfo(subscriptionFee, transactionFee, tier);
    }
}
