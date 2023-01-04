package com.musicdistribution.streamingservice.xport.rest;

import com.musicdistribution.sharedkernel.config.ApiController;
import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import com.musicdistribution.streamingservice.constant.PathConstants;
import com.musicdistribution.streamingservice.domain.model.response.payment.OrderResponse;
import com.musicdistribution.streamingservice.service.PaymentService;
import com.musicdistribution.streamingservice.util.PaymentUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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

    /**
     * Method used for creating a new payment given a total amount for transaction.
     *
     * @param totalAmount - the total amount of money to be processed for the transaction.
     * @return the created order for the payment.
     */
    @PostMapping(PathConstants.CREATE)
    public ResponseEntity<OrderResponse> createPayment(@RequestBody @Valid Money totalAmount) {
        return Optional.ofNullable(paymentService.createPayment(totalAmount, PaymentUtil.buildReturnUrl()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
