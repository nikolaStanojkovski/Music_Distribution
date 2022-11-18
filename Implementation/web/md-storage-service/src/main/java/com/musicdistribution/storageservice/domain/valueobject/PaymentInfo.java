package com.musicdistribution.storageservice.domain.valueobject;

import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * A value object that contains payment information about a transaction.
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentInfo implements Serializable {

    private Money subscriptionFee;
    private Money transactionFee;
    private Tier tier;

    /**
     * Method used for creating a new payment information object.
     *
     * @param subscriptionFee - the subscription fee of the payment.
     * @param transactionFee  - the subscription fee of the payment.
     * @param tier            - the tier of the publishing which is being made.
     * @return the payment information object.
     */
    public static PaymentInfo from(Money subscriptionFee, Money transactionFee, Tier tier) {
        return new PaymentInfo(subscriptionFee, transactionFee, tier);
    }
}
