package com.musicdistribution.streamingservice.infrastructure.payment;

import com.musicdistribution.streamingservice.constant.PropertyConstants;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Configuration helper class used for storing static payment properties.
 */
@Getter
@Component
public class PaymentProperties {

    @Value(PropertyConstants.MD_PAYPAL_CLIENT_ID)
    private String paypalClientId;

    @Value(PropertyConstants.MD_PAYPAL_SECRET)
    private String paypalSecret;
}
