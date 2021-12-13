package kata.supermarket.checkout.discount;

import kata.supermarket.checkout.ItemName;
import kata.supermarket.checkout.PriceList;
import kata.supermarket.model.Item;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;

public class TwoForOnePound extends SingleProductDiscount {

    public TwoForOnePound(final EnumSet<ItemName> applicableItems, final PriceList priceList) {
        super(applicableItems, priceList);
    }

    @Override
    public BigDecimal calculateDiscount(final ItemName key, final List<Item> items) {
        final BigDecimal timesToApplyDiscount = BigDecimal.valueOf(items.size() / 2);
        final BigDecimal priceOfTwoItems = priceList.getPrice(key).multiply(new BigDecimal("2"));
        return timesToApplyDiscount.multiply(priceOfTwoItems.subtract(BigDecimal.ONE));
    }
}
