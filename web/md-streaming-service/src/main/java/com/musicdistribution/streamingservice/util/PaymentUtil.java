package com.musicdistribution.streamingservice.util;

import com.musicdistribution.sharedkernel.constant.EnvironmentConstants;
import com.musicdistribution.streamingservice.constant.PathConstants;
import com.musicdistribution.streamingservice.constant.TransactionConstants;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.util.NoSuchElementException;

/**
 * A utility helper class used for payment related operations.
 */
@Slf4j
public final class PaymentUtil {

    /**
     * Private constructor which throws an exception because the class should not be instantiated.
     */
    private PaymentUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Method used to build the return URL when making a payment.
     *
     * @return the built URI object.
     */
    public static URI buildReturnUrl() {
        try {
            return new URI(String.format("%s%s", EnvironmentConstants.CROSS_ORIGIN_DOMAIN, PathConstants.CHECKOUT));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return URI.create(StringUtils.EMPTY);
    }

    /**
     * Method used to extract the approval link from a payment order.
     *
     * @param order - the payment order to be processed.
     * @return the extracted approval link.
     */
    public static LinkDescription extractApprovalLink(Order order) {
        return order.links().stream()
                .filter(link -> TransactionConstants.APPROVED_ORDER_STATUS
                        .equals(link.rel()))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
