package kata.supermarket.checkout.discount.single_product;

import kata.supermarket.checkout.ItemName;
import kata.supermarket.checkout.PriceList;
import kata.supermarket.model.Item;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;

public class TwoForOnePound extends SingleProductDiscount {

    public TwoForOnePound(final EnumSet<ItemName> eligibleItems, final PriceList priceList) {
        super(eligibleItems, priceList);
    }

    @Override
    public BigDecimal calculateDiscount(final ItemName key, final List<Item> items) {
        final BigDecimal timesToApplyDiscount = BigDecimal.valueOf(items.size() / 2);
        final BigDecimal priceOfTwoItems = priceList.getPrice(key).multiply(new BigDecimal("2"));
        return timesToApplyDiscount.multiply(priceOfTwoItems.subtract(BigDecimal.ONE));
    }
}
