package kata.supermarket.checkout;

import kata.supermarket.model.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

public class Checkout {

    private final Basket basket;

    public Checkout(final Basket basket) {
        this.basket = basket;
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

        /**
         * TODO: This could be a good place to apply the results of
         * the discount calculations.
         * It is not likely to be the best place to do those calculations.
         * Think about how Basket could interact with something
         * which provides that functionality.
         */
        private BigDecimal discounts() {
            return BigDecimal.ZERO;
        }

        private BigDecimal calculate() {
            return subtotal().subtract(discounts());
        }
    }
}
