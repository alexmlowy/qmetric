package kata.supermarket.checkout.discount;

import kata.supermarket.checkout.ItemName;
import kata.supermarket.checkout.PriceList;
import kata.supermarket.model.Item;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;

public class ThreeForTwo extends SingleProductDiscount {

    public ThreeForTwo(final EnumSet<ItemName> eligibleItems, final PriceList priceList) {
        super(eligibleItems, priceList);
    }

    @Override
    public BigDecimal calculateDiscount(final ItemName key, final List<Item> items) {
        final BigDecimal timesToApplyDiscount = BigDecimal.valueOf(items.size() / 3);
        return timesToApplyDiscount.multiply(priceList.getPrice(key));
    }
}
