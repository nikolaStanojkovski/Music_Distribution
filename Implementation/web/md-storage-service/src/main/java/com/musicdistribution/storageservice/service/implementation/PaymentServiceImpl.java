package com.musicdistribution.storageservice.service.implementation;

import com.musicdistribution.storageservice.service.PaymentService;
import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Currency;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the payment service.
 */
@Service
@Transactional
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    public static final Money NO_FEE = Money.valueOf(Currency.EUR, 0.00);

    @Override
    public Money getTransactionFee(String locale) {
        if (locale.isEmpty())
            return NO_FEE;

        char leading = Character.toLowerCase(locale.charAt(0));
        if (leading >= 'a' && leading <= 'h')
            return Money.valueOf(Currency.EUR, 3.00);
        else if (leading >= 'i' && leading <= 'p')
            return Money.valueOf(Currency.EUR, 5.00);
        else if (leading >= 'q' && leading <= 'z')
            return Money.valueOf(Currency.EUR, 10.00);
        else
            return NO_FEE;
    }

    @Override
    public Money getSubscriptionFee(Tier tier) {
        switch (tier) {
            case Bronze:
                return Money.valueOf(Currency.EUR, 10.00);
            case Silver:
                return Money.valueOf(Currency.EUR, 25.00);
            case Gold:
                return Money.valueOf(Currency.EUR, 50.00);
            case Platinum:
                return Money.valueOf(Currency.EUR, 100.00);
            case Diamond:
                return Money.valueOf(Currency.EUR, 250.00);
            default:
                return NO_FEE;
        }
    }
}
