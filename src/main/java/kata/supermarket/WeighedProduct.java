package kata.supermarket;

import java.math.BigDecimal;

public class WeighedProduct {

    private final String name;
    private final BigDecimal pricePerKilo;

    public WeighedProduct(final String name, final BigDecimal pricePerKilo) {
        this.name = name;
        this.pricePerKilo = pricePerKilo;
    }

    public String name() {
        return name;
    }

    BigDecimal pricePerKilo() {
        return pricePerKilo;
    }

    public Item weighing(final BigDecimal kilos) {
        return new ItemByWeight(this, kilos);
    }
}
