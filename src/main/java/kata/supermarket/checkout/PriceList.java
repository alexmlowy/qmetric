package kata.supermarket.checkout;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PriceList {

    private final Map<ItemName, BigDecimal> prices = new HashMap<>();

    public void add(final ItemName itemName, final BigDecimal price) {
        prices.put(itemName, price);
    }

    public BigDecimal getPrice(final ItemName itemName) {
        final BigDecimal itemPrice = prices.get(itemName);
        if (itemPrice == null) {
            throw new IllegalArgumentException("Price not specified for item=" + itemName);
        }
        return itemPrice;
    }
}
