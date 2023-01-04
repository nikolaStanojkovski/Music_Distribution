package com.musicdistribution.streamingservice.service;

import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import com.musicdistribution.streamingservice.domain.model.response.payment.OrderResponse;

import java.net.URI;

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

    /**
     * Method used for creating a new payment order based on amount and return URL.
     *
     * @param amount    - the amount from which the order is to be created.
     * @param returnUrl - the return URL for the user creating the order.
     * @return a response object containing information about the order.
     */
    OrderResponse createPayment(Money amount, URI returnUrl);
}
