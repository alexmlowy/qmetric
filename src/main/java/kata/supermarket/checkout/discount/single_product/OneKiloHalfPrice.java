package kata.supermarket.checkout.discount.single_product;

import kata.supermarket.checkout.ItemName;
import kata.supermarket.checkout.PriceList;
import kata.supermarket.model.Item;
import kata.supermarket.model.ItemByWeight;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.List;

public class OneKiloHalfPrice extends SingleProductDiscount {

    public OneKiloHalfPrice(final EnumSet<ItemName> eligibleItems, final PriceList priceList) {
        super(eligibleItems, priceList);
    }

    @Override
    public BigDecimal calculateDiscount(final ItemName key, final List<Item> items) {
        if (!key.isWeighedProduct()) {
            return BigDecimal.ZERO;
        }
        final BigDecimal wholeKilosPresent = items.stream()
                .map(item -> ((ItemByWeight) item).weightInKilos())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(0, RoundingMode.DOWN);


        return wholeKilosPresent.multiply(priceList.getPrice(key).multiply(new BigDecimal("0.5")));
    }
}
