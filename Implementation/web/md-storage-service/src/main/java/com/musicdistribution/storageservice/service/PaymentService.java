package com.musicdistribution.storageservice.service;

import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;

public interface PaymentService {

    Money getTransactionFee(String locale);

    Money getSubscriptionFee(Tier tier);
}
