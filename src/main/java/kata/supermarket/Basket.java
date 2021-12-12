package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Basket {
    private final Map<String, List<Item>> items;

    public Basket() {
        this.items = new HashMap<>();
    }

    public void add(final Item item) {
        this.items.computeIfAbsent(item.name(), v -> new ArrayList<>()).add(item);
    }

    Map<String, List<Item>> items() {
        return Collections.unmodifiableMap(items);
    }

    public BigDecimal total() {
        return new TotalCalculator().calculate();
    }

    private class TotalCalculator {
        private final Map<String, List<Item>> items;

        TotalCalculator() {
            this.items = items();
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
         *  the discount calculations.
         *  It is not likely to be the best place to do those calculations.
         *  Think about how Basket could interact with something
         *  which provides that functionality.
         */
        private BigDecimal discounts() {
            return BigDecimal.ZERO;
        }

        private BigDecimal calculate() {
            return subtotal().subtract(discounts());
        }
    }
}
