package kata.supermarket.checkout.discount;

import kata.supermarket.checkout.Basket;

import java.math.BigDecimal;

public interface Discount {

    BigDecimal calculate(Basket basket);

}
