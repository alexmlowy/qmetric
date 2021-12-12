package kata.supermarket.model;

import kata.supermarket.checkout.ItemName;

import java.math.BigDecimal;

public class ItemByUnit implements Item {

    private final Product product;

    ItemByUnit(final Product product) {
        this.product = product;
    }

    public BigDecimal price() {
        return product.pricePerUnit();
    }

    @Override
    public ItemName name() {
        return product.name();
    }
}
