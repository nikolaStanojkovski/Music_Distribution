package com.musicdistribution.sharedkernel.domain.valueobjects;

import com.musicdistribution.sharedkernel.constant.ExceptionConstants;
import com.musicdistribution.sharedkernel.domain.base.ValueObject;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;

/**
 * Value object for money that contains the currency and amount.
 */
@Getter
@Embeddable
@AllArgsConstructor
public class Money implements ValueObject, Serializable {

    @Enumerated(value = EnumType.STRING)
    private final Currency currency;

    private final Double amount;

    /**
     * Protected no-args constructor used for creating a money entity.
     */
    protected Money() {
        this.amount = 0.0;
        this.currency = null;
    }

    /**
     * Static method used for the creation of a money object.
     *
     * @param currency - money's currency.
     * @param amount   - money's amount.
     * @return the created money object.
     */
    public static Money from(Currency currency, Double amount) {
        return new Money(currency, amount);
    }

    /**
     * Method used for the addition of money into an existing object.
     *
     * @param money - the money that is to be added to {this}.
     * @return the sum of the two objects.
     */
    public Money add(Money money) {
        if (money != null && currency != null) {
            if (!currency.equals(money.currency)) {
                throw new IllegalArgumentException(ExceptionConstants.MONEY_ADDITION_FAILURE);
            }
            return new Money(currency, this.amount + money.amount);
        } else
            return null;
    }

    /**
     * Method used for the subtraction of money from an existing object.
     *
     * @param money - the money that is to be subtracted from {this}.
     * @return the sum of the two objects.
     */
    public Money subtract(Money money) {
        if (money != null && currency != null) {
            if (!currency.equals(money.currency)) {
                throw new IllegalArgumentException(ExceptionConstants.MONEY_SUBTRACTION_FAILURE);
            }
            return new Money(currency, amount - money.amount);
        } else
            return null;
    }

    /**
     * Method used for the multiplication of the money amount from an existing object.
     *
     * @param factor - the factor that needs to get multiplied to {this}.
     * @return the multiplied money amount.
     */
    public Money multiply(int factor) {
        if (currency != null)
            return new Money(currency, amount * factor);

        return null;
    }

    /**
     * Method used for comparing two objects of type money.
     *
     * @param o - the other object that is compared to {this}.
     * @return a flag representing whether the two objects are identical or not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount) && currency == money.currency;
    }

    /**
     * Method used for generating a hash code for a money object.
     *
     * @return the integer value of the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }
}
