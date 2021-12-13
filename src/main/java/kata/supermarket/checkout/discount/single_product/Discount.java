package kata.supermarket.checkout.discount.single_product;

import kata.supermarket.checkout.Basket;

import java.math.BigDecimal;

public interface Discount {

    BigDecimal calculate(Basket basket);

}
