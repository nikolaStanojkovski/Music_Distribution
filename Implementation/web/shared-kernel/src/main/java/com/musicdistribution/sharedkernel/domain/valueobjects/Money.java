package com.musicdistribution.sharedkernel.domain.valueobjects;

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
     * Protected no-args constructor for the money object.
     */
    protected Money() {
        this.amount = 0.0;
        this.currency = null;
    }

    /**
     * Static method for creating a money object given the currency and amount.
     *
     * @param currency - money's currency.
     * @param amount   - money's amount.
     * @return the created money object.
     */
    public static Money valueOf(Currency currency, Double amount) {
        return new Money(currency, amount);
    }

    /**
     * Method used for the addition of two money objects.
     *
     * @param money - object's money which is to be added to this.
     * @return the new money object with a made addition of the amounts of the two objects.
     */
    public Money add(Money money) {
        if (money != null && currency != null) {
            if (!currency.equals(money.currency)) {
                throw new IllegalArgumentException("Cannot add two Money objects with different currencies");
            }
            return new Money(currency, this.amount + money.amount);
        } else
            return null;
    }

    /**
     * Method used for the subtraction of two money objects.
     *
     * @param money - object's money which is to be subtracted to this.
     * @return the new money object with a made subtraction of the amounts of the two objects.
     */
    public Money subtract(Money money) {
        if (money != null && currency != null) {
            if (!currency.equals(money.currency)) {
                throw new IllegalArgumentException("Cannot add two Money objects with different currencies");
            }
            return new Money(currency, amount - money.amount);
        } else
            return null;
    }

    /**
     * Method used for the multiplication of two money objects.
     *
     * @param factor - the factor that the money's amount will be multiplied to.
     * @return the new money object with a made multiplication of the amounts of the two objects.
     */
    public Money multiply(int factor) {
        if (currency != null)
            return new Money(currency, amount * factor);

        return null;
    }

    /**
     * Method used for comparing two objects of type money.
     *
     * @param o - the other object that is compared to this.
     * @return a flag whether the two objects are equal or not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount) && currency == money.currency;
    }

    /**
     * Method used for generating the hash code for an money object.
     *
     * @return the integer value of the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }
}
