package kata.supermarket.checkout.discount;

import kata.supermarket.checkout.Basket;
import kata.supermarket.checkout.ItemName;
import kata.supermarket.checkout.PriceList;
import kata.supermarket.checkout.discount.single_product.Discount;
import kata.supermarket.checkout.discount.single_product.OneKiloHalfPrice;
import kata.supermarket.model.Item;
import kata.supermarket.model.WeighedProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.stream.Stream;

import static kata.supermarket.checkout.ItemName.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OneKiloHalfPriceTest {

    private static final PriceList PRICE_LIST = createPriceList();

    private static PriceList createPriceList() {
        final PriceList priceList = new PriceList();
        priceList.add(Milk, new BigDecimal("0.49"));
        priceList.add(Pick_And_Mix_Per_Kilo, new BigDecimal("1.50"));
        priceList.add(American_Sweets_Per_Kilo, new BigDecimal("2.50"));
        return priceList;
    }

    @DisplayName("basket provides its total value when containing...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void basketProvidesTotalDiscount(String description, String expectedDiscount, EnumSet<ItemName> applicableItems, Basket basket) {
        final Discount discount = new OneKiloHalfPrice(applicableItems, PRICE_LIST);

        final BigDecimal discountReduction = discount.calculate(basket);
        assertThat(discountReduction).isEqualByComparingTo(new BigDecimal(expectedDiscount));
    }

    static Stream<Arguments> basketProvidesTotalDiscount() {
        return Stream.of(
                noApplicableItems(),
                noMatchingItems(),
                notApplicableOnNonWeightedItem(),
                discountNotAppliedOnLessThanOneKilo(),
                discountNotAppliedOnAggregateWeightLessThanOneKilo(),
                discountAppliedOnSingleItemEqualingOneKilo(),
                discountAppliedOnMultipleItemsGreaterThanOneKilo(),
                discountAppliedMultipleTimesOnAdditionalWholeKilos(),
                discountAppliedOnMultipleItems()
        );
    }

    private static Arguments noApplicableItems() {
        return Arguments.of("no applicable items", "0", EnumSet.noneOf(ItemName.class), createBasket(Milk, Milk));
    }

    private static Arguments noMatchingItems() {
        return Arguments.of("no matching items", "0", EnumSet.of(Pack_Of_Digestives), createBasket(Milk, Milk));
    }

    private static Arguments notApplicableOnNonWeightedItem() {
        return Arguments.of("not applicable on non weighed item", "0", EnumSet.of(Milk), createBasket(Milk));
    }

    private static Arguments discountNotAppliedOnLessThanOneKilo() {
        return Arguments.of("discount not applied on less than one kilo", "0", EnumSet.of(Pick_And_Mix_Per_Kilo),
                createBasket(weighedItem(Pick_And_Mix_Per_Kilo, "0.5")));
    }

    private static Arguments discountNotAppliedOnAggregateWeightLessThanOneKilo() {
        return Arguments.of("discount not applied on aggregate weight less than one kilo", "0", EnumSet.of(Pick_And_Mix_Per_Kilo),
                createBasket(weighedItem(Pick_And_Mix_Per_Kilo, "0.5"), weighedItem(Pick_And_Mix_Per_Kilo, "0.49")));
    }

    private static Arguments discountAppliedOnSingleItemEqualingOneKilo() {
        return Arguments.of("discount applied on single item equalling one kilo", "0.75", EnumSet.of(Pick_And_Mix_Per_Kilo),
                createBasket(weighedItem(Pick_And_Mix_Per_Kilo, "1")));
    }

    private static Arguments discountAppliedOnMultipleItemsGreaterThanOneKilo() {
        return Arguments.of("discount applied on multiple items greater than one kilo", "0.75", EnumSet.of(Pick_And_Mix_Per_Kilo),
                createBasket(weighedItem(Pick_And_Mix_Per_Kilo, "0.75"), weighedItem(Pick_And_Mix_Per_Kilo, "0.75")));
    }

    private static Arguments discountAppliedMultipleTimesOnAdditionalWholeKilos() {
        return Arguments.of("discount applied multiple times on additional whole kilos", "1.50", EnumSet.of(Pick_And_Mix_Per_Kilo),
                createBasket(weighedItem(Pick_And_Mix_Per_Kilo, "0.75"), weighedItem(Pick_And_Mix_Per_Kilo, "1.75")));
    }

    private static Arguments discountAppliedOnMultipleItems() {
        return Arguments.of("discount applied on multiple items", "3.25", EnumSet.of(Pick_And_Mix_Per_Kilo, American_Sweets_Per_Kilo),
                createBasket(weighedItem(Pick_And_Mix_Per_Kilo, "1.75"), weighedItem(American_Sweets_Per_Kilo, "2")));
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

    private static Basket createBasket(final Item... items) {
        final Basket basket = new Basket();
        for (Item item : items) {
            basket.add(item);
        }
        return basket;
    }

    private static Item weighedItem(final ItemName name, final String weightInKilo) {
        final WeighedProduct weighedProduct = new WeighedProduct(name, PRICE_LIST.getPrice(name));
        return weighedProduct.weighing(new BigDecimal(weightInKilo));
    }

}