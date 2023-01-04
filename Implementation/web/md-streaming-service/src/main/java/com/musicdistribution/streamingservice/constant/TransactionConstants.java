package com.musicdistribution.streamingservice.constant;

import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Currency;

/**
 * Helper class used for storing constants related to user transactions.
 */
public final class TransactionConstants {

    public static final Money NO_FEE = Money.from(Currency.EUR, 0.00);

    public final static Double LEVEL_1_TRANSACTION_FEE = 3.00;
    public final static Double LEVEL_2_TRANSACTION_FEE = 5.00;
    public final static Double LEVEL_3_TRANSACTION_FEE = 10.00;

    public final static Double BRONZE_SUBSCRIPTION_FEE = 10.00;
    public final static Double SILVER_SUBSCRIPTION_FEE = 25.00;
    public final static Double GOLD_SUBSCRIPTION_FEE = 50.00;
    public final static Double PLATINUM_SUBSCRIPTION_FEE = 100.00;
    public final static Double DIAMOND_SUBSCRIPTION_FEE = 150.00;

    public final static String CAPTURED_ORDER_STATUS = "CAPTURE";
    public final static String APPROVED_ORDER_STATUS = "approve";

    /**
     * Private constructor which throws an exception because the class should not be instantiated.
     */
    private TransactionConstants() {
        throw new UnsupportedOperationException(ExceptionConstants.UNSUPPORTED_CLASS_INSTANTIATION);
    }
}
