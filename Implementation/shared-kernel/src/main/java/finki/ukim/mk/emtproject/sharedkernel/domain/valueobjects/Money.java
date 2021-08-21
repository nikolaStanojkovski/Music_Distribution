package finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.ValueObject;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Currency;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

/**
 * Money - value object for money that contains the currency and amount
 */
@Embeddable
@Getter
public class Money implements ValueObject {

    @Enumerated(value = EnumType.STRING)
    private final Currency currency;

    private final Double amount;

    protected Money() {
        this.amount = 0.0;
        this.currency = null;
    }

    public Money(@NonNull Currency currency, @NonNull Double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public static Money valueOf(Currency currency, Double amount) {
        return new Money(currency, amount);
    }

    public Money add(Money money) {
        if(money != null && currency != null) {
            if (!currency.equals(money.currency)) {
                throw new IllegalArgumentException("Cannot add two Money objects with different currencies");
            }
            return new Money(currency,this.amount + money.amount);
        } else
            return null;
    }

    public Money subtract(Money money) {
        if(money != null && currency != null) {
            if (!currency.equals(money.currency)) {
                throw new IllegalArgumentException("Cannot add two Money objects with different currencies");
            }
            return new Money(currency,amount - money.amount);
        } else
            return null;
    }

    public Money multiply(int m)  {
        if(currency != null)
            return new Money(currency,amount * m);

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount == money.amount && currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

}
