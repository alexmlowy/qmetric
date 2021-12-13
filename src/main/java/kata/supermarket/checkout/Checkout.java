package kata.supermarket.checkout;

import kata.supermarket.checkout.discount.single_product.Discount;
import kata.supermarket.model.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

public class Checkout {

    private final Basket basket;
    private final List<Discount> discounts;

    public Checkout(final Basket basket, final List<Discount> discounts) {
        this.basket = basket;
        this.discounts = discounts;
    }

    public BigDecimal total() {
        return new TotalCalculator().calculate();
    }

    private class TotalCalculator {
        private final Map<ItemName, List<Item>> items;

        TotalCalculator() {
            this.items = basket.items();
        }

        private BigDecimal subtotal() {
            return items.values().stream().flatMap(List::stream)
                    .map(Item::price)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        private BigDecimal discounts() {
            return discounts.stream()
                    .map(discount -> discount.calculate(basket))
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        private BigDecimal calculate() {
            return subtotal().subtract(discounts());
        }
    }
}
