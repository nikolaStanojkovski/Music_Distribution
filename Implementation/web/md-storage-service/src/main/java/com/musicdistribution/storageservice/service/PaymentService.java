package com.musicdistribution.storageservice.service;

import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;

/**
 * A service that contains the specific business logic for payments.
 */
public interface PaymentService {

    /**
     * Method used for calculating the transaction fee based on the user locale.
     *
     * @param locale - the locale from which the filtering is to be done.
     * @return the amount of money for the transaction fee.
     */
    Money getTransactionFee(String locale);

    /**
     * Method used for calculating the subscription fee based on the publishing tier.
     *
     * @param tier - the tier from which the fee is to be determined.
     * @return the amount of money for the subscription fee.
     */
    Money getSubscriptionFee(Tier tier);
}
