package com.musicdistribution.streamingservice.service.implementation;

import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Currency;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import com.musicdistribution.streamingservice.config.payment.PaymentProperties;
import com.musicdistribution.streamingservice.constant.AlphabetConstants;
import com.musicdistribution.streamingservice.constant.TransactionConstants;
import com.musicdistribution.streamingservice.domain.model.response.payment.OrderResponse;
import com.musicdistribution.streamingservice.service.PaymentService;
import com.musicdistribution.streamingservice.util.PaymentUtil;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Collections;

/**
 * Implementation of the payment service.
 */
@Slf4j
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PayPalHttpClient payPalHttpClient;

    /**
     * An autowired constructor used to inject the HTTP client used for payments.
     *
     * @param paymentProperties - a reference to the static payment properties used for integration.
     */
    @Autowired
    public PaymentServiceImpl(PaymentProperties paymentProperties) {
        this.payPalHttpClient = new PayPalHttpClient(new PayPalEnvironment.Sandbox(
                paymentProperties.getPaypalClientId(), paymentProperties.getPaypalSecret()
        ));
    }

    /**
     * Method used for calculating the transaction fee based on the user locale.
     *
     * @param locale - the locale from which the filtering is to be done.
     * @return the amount of money for the transaction fee.
     */
    @Override
    public Money getTransactionFee(String locale) {
        if (locale.isEmpty())
            return TransactionConstants.NO_FEE;

        char leading = Character.toLowerCase(locale.charAt(0));
        if (leading >= AlphabetConstants.A_LOWERCASE && leading
                <= AlphabetConstants.H_LOWERCASE)
            return Money.from(Currency.EUR, TransactionConstants.LEVEL_1_TRANSACTION_FEE);
        else if (leading >= AlphabetConstants.I_LOWERCASE
                && leading <= AlphabetConstants.P_LOWERCASE)
            return Money.from(Currency.EUR, TransactionConstants.LEVEL_2_TRANSACTION_FEE);
        else if (leading >= AlphabetConstants.Q_LOWERCASE
                && leading <= AlphabetConstants.Z_LOWERCASE)
            return Money.from(Currency.EUR, TransactionConstants.LEVEL_3_TRANSACTION_FEE);
        else
            return TransactionConstants.NO_FEE;
    }

    /**
     * Method used for calculating the subscription fee based on the publishing tier.
     *
     * @param tier - the tier from which the fee is to be determined.
     * @return the amount of money for the subscription fee.
     */
    @Override
    public Money getSubscriptionFee(Tier tier) {
        switch (tier) {
            case Bronze:
                return Money.from(Currency.EUR, TransactionConstants.BRONZE_SUBSCRIPTION_FEE);
            case Silver:
                return Money.from(Currency.EUR, TransactionConstants.SILVER_SUBSCRIPTION_FEE);
            case Gold:
                return Money.from(Currency.EUR, TransactionConstants.GOLD_SUBSCRIPTION_FEE);
            case Platinum:
                return Money.from(Currency.EUR, TransactionConstants.PLATINUM_SUBSCRIPTION_FEE);
            case Diamond:
                return Money.from(Currency.EUR, TransactionConstants.DIAMOND_SUBSCRIPTION_FEE);
            default:
                return TransactionConstants.NO_FEE;
        }
    }

    /**
     * Method used for creating a new payment order based on amount and return URL.
     *
     * @param amount    - the amount from which the order is to be created.
     * @param returnUrl - the return URL for the user creating the order.
     * @return a response object containing information about the order.
     */
    @Override
    public OrderResponse createPayment(Money amount, URI returnUrl) {
        OrderRequest orderRequest = createOrderRequest(amount, returnUrl);
        OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest().requestBody(orderRequest);
        HttpResponse<Order> orderHttpResponse;
        try {
            orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);
            Order order = orderHttpResponse.result();
            LinkDescription approveUri = PaymentUtil.extractApprovalLink(order);
            return OrderResponse.from(order.id(), URI.create(approveUri.href()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return OrderResponse.from(StringUtils.EMPTY, URI.create(StringUtils.EMPTY));
    }

    /**
     * Method used to create an order request wrapper object.
     *
     * @param amount    - the amount of money to be  included in the transaction.
     * @param returnUrl - the return URL for the user creating the order.
     * @return a response object containing information about the order.
     */
    private OrderRequest createOrderRequest(Money amount, URI returnUrl) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.applicationContext(new ApplicationContext()
                .returnUrl(returnUrl.toString()));
        orderRequest.checkoutPaymentIntent(TransactionConstants.CAPTURED_ORDER_STATUS);
        orderRequest.purchaseUnits(Collections.singletonList(new PurchaseUnitRequest()
                .amountWithBreakdown(new AmountWithBreakdown()
                        .currencyCode(amount.getCurrency().name())
                        .value(amount.getAmount().toString()))
        ));
        return orderRequest;
    }
}
