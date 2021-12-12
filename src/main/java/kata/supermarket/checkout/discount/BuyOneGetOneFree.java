package kata.supermarket.checkout.discount;

import kata.supermarket.checkout.Basket;
import kata.supermarket.checkout.ItemName;
import kata.supermarket.checkout.PriceList;
import kata.supermarket.model.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class BuyOneGetOneFree implements Discount {

    private final EnumSet<ItemName> eligibleItems;
    private final PriceList priceList;

    public BuyOneGetOneFree(final EnumSet<ItemName> eligibleItems, final PriceList priceList) {
        this.eligibleItems = eligibleItems;
        this.priceList = priceList;
    }

    @Override
    public BigDecimal calculate(final Basket basket) {
        final Map<ItemName, List<Item>> items = basket.items();
        return items.entrySet().stream()
                .filter(entry -> eligibleItems.contains(entry.getKey()))
                .map(entry -> calculateDiscount(entry.getKey(), entry.getValue()))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateDiscount(final ItemName key, final List<Item> items) {
        final BigDecimal timesToApplyDiscount = BigDecimal.valueOf(items.size() / 2);
        return timesToApplyDiscount.multiply(priceList.getPrice(key));
    }
}
