package kata.supermarket.checkout.discount.single_product;

import kata.supermarket.checkout.ItemName;
import kata.supermarket.checkout.PriceList;
import kata.supermarket.model.Item;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;

public class BuyOneGetOneFree extends SingleProductDiscount {

    public BuyOneGetOneFree(final EnumSet<ItemName> eligibleItems, final PriceList priceList) {
        super(eligibleItems, priceList);
    }

    @Override
    public BigDecimal calculateDiscount(final ItemName key, final List<Item> items) {
        final BigDecimal timesToApplyDiscount = BigDecimal.valueOf(items.size() / 2);
        return timesToApplyDiscount.multiply(priceList.getPrice(key));
    }
}
