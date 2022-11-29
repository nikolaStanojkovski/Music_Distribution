package com.musicdistribution.streamingservice.service.implementation;

import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Currency;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Tier;
import com.musicdistribution.streamingservice.constant.AlphabetConstants;
import com.musicdistribution.streamingservice.constant.TransactionConstants;
import com.musicdistribution.streamingservice.service.PaymentService;
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
}
