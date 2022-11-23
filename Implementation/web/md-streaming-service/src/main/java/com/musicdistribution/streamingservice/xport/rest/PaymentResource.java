package com.musicdistribution.streamingservice.xport.rest;

import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import com.musicdistribution.sharedkernel.util.ApiController;
import com.musicdistribution.streamingservice.constant.PathConstants;
import com.musicdistribution.streamingservice.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Payment Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping(PathConstants.API_PAYMENT)
public class PaymentResource {

    private final PaymentService paymentService;

    /**
     * Method used for fetching the subscription fee based on a given tier.
     *
     * @param tier - the tier by which the subscription fee will be determined.
     * @return the calculated subscription fee.
     */
    @GetMapping(PathConstants.SUBSCRIPTION)
    public Money getSubscriptionFee(@RequestParam Tier tier) {
        return paymentService.getSubscriptionFee(tier);
    }

    /**
     * Method used for fetching the transaction fee based on a given locale.
     *
     * @param locale - the locale by which the transaction fee will be determined.
     * @return the calculated transaction fee.
     */
    @GetMapping(PathConstants.TRANSACTION)
    public Money getTransactionFee(@RequestParam String locale) {
        return paymentService.getTransactionFee(locale);
    }
}
