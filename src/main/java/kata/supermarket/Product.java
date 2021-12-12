package kata.supermarket;

import java.math.BigDecimal;

public class Product {

    private final String name;
    private final BigDecimal pricePerUnit;

    public Product(final String name, final BigDecimal pricePerUnit) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
    }

    BigDecimal pricePerUnit() {
        return pricePerUnit;
    }

    String name() {
        return name;
    }

    public Item oneOf() {
        return new ItemByUnit(this);
    }
}
