package kata.supermarket.checkout.discount;

import kata.supermarket.checkout.Basket;
import kata.supermarket.checkout.ItemName;
import kata.supermarket.checkout.PriceList;
import kata.supermarket.model.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.stream.Stream;

import static kata.supermarket.checkout.ItemName.Milk;
import static kata.supermarket.checkout.ItemName.Pack_Of_Digestives;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class BuyOneGetOneFreeTest {

    private static final PriceList PRICE_LIST = createPriceList();

    private static PriceList createPriceList() {
        final PriceList priceList = new PriceList();
        priceList.add(Milk, new BigDecimal("0.49"));
        priceList.add(ItemName.Pack_Of_Digestives, new BigDecimal("1.55"));
        return priceList;
    }

    @DisplayName("basket provides its total value when containing...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void basketProvidesTotalDiscount(String description, String expectedDiscount, EnumSet<ItemName> applicableItems, Basket basket) {
        final Discount discount = new BuyOneGetOneFree(applicableItems, PRICE_LIST);

        final BigDecimal discountReduction = discount.calculate(basket);
        assertThat(discountReduction).isEqualByComparingTo(new BigDecimal(expectedDiscount));
    }

    static Stream<Arguments> basketProvidesTotalDiscount() {
        return Stream.of(
                noApplicableItems(),
                noMatchingItems(),
                notApplicableOnSingleItem(),
                discountAppliedOnTwoMatchingItems(),
                discountAppliedOnlyOncePerMatchingPair(),
                discountAppliedMultipleTimesOnMatchingPairs(),
                discountAppliedOnMultipleItems()
        );
    }

    private static Arguments noApplicableItems() {
        return Arguments.of("no applicable items", "0", EnumSet.noneOf(ItemName.class), createBasket(Milk, Milk));
    }

    private static Arguments noMatchingItems() {
        return Arguments.of("no matching items", "0", EnumSet.of(Pack_Of_Digestives), createBasket(Milk, Milk));
    }

    private static Arguments notApplicableOnSingleItem() {
        return Arguments.of("not applicable on single item", "0", EnumSet.of(Milk), createBasket(Milk));
    }

    private static Arguments discountAppliedOnTwoMatchingItems() {
        return Arguments.of("discount applied on two matching items", "0.49", EnumSet.of(Milk), createBasket(Milk, Milk));
    }

    private static Arguments discountAppliedOnlyOncePerMatchingPair() {
        return Arguments.of("discount applied only once per matching pair", "0.49", EnumSet.of(Milk), createBasket(Milk, Milk, Milk));
    }

    private static Arguments discountAppliedMultipleTimesOnMatchingPairs() {
        return Arguments.of("discount applied multiple times on matching pairs", "0.98", EnumSet.of(Milk), createBasket(Milk, Milk, Milk, Milk));
    }

    private static Arguments discountAppliedOnMultipleItems() {
        return Arguments.of("discount applied on multiple items", "2.04", EnumSet.of(Milk, Pack_Of_Digestives), createBasket(Milk, Milk, Pack_Of_Digestives, Pack_Of_Digestives));
    }

    private static Basket createBasket(final ItemName... name) {
        final Basket basket = new Basket();
        for (ItemName itemName : name) {
            final Item item = mock(Item.class);
            given(item.name()).willReturn(itemName);
            given(item.price()).willReturn(PRICE_LIST.getPrice(itemName));
            basket.add(item);
        }

        return basket;
    }

}