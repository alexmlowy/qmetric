package kata.supermarket.model;

import kata.supermarket.checkout.ItemName;

import java.math.BigDecimal;

public interface Item {
    ItemName name();

    BigDecimal price();
}
