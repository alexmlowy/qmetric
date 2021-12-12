package kata.supermarket.model;

import kata.supermarket.checkout.ItemName;

import java.math.BigDecimal;

public class Product {

    private final ItemName name;
    private final BigDecimal pricePerUnit;

    public Product(final ItemName name, final BigDecimal pricePerUnit) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
    }

    BigDecimal pricePerUnit() {
        return pricePerUnit;
    }

    ItemName name() {
        return name;
    }

    public Item oneOf() {
        return new ItemByUnit(this);
    }
}
