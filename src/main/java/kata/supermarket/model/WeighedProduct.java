package kata.supermarket.model;

import kata.supermarket.checkout.ItemName;

import java.math.BigDecimal;

public class WeighedProduct {

    private final ItemName name;
    private final BigDecimal pricePerKilo;

    public WeighedProduct(final ItemName name, final BigDecimal pricePerKilo) {
        this.name = name;
        this.pricePerKilo = pricePerKilo;
    }

    public ItemName name() {
        return name;
    }

    BigDecimal pricePerKilo() {
        return pricePerKilo;
    }

    public Item weighing(final BigDecimal kilos) {
        return new ItemByWeight(this, kilos);
    }
}
