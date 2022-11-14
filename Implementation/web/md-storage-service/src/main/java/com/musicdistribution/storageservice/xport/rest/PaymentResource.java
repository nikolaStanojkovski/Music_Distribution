package com.musicdistribution.storageservice.xport.rest;

import com.musicdistribution.storageservice.service.PaymentService;
import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import com.musicdistribution.sharedkernel.util.ApiController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Payment Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping("/api/payment")
public class PaymentResource {

    private final PaymentService paymentService;

    /**
     * Method for getting the subscription fee based on the given tier
     *
     * @param tier - the tier by which the subscription fee will be determined
     * @return the given subscription fee.
     */
    @GetMapping("/subscription")
    public Money getSubscriptionFee(@RequestParam Tier tier) {
        return paymentService.getSubscriptionFee(tier);
    }

    /**
     * Method for getting the transaction fee based on the given locale
     *
     * @param locale - the locale by which the transaction fee will be determined
     * @return the given transaction fee.
     */
    @GetMapping("/transaction")
    public Money getTransactionFee(@RequestParam String locale) {
        return paymentService.getTransactionFee(locale);
    }
}
