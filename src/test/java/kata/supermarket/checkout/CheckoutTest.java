package kata.supermarket.checkout;

import kata.supermarket.checkout.discount.Discount;
import kata.supermarket.model.Item;
import kata.supermarket.model.Product;
import kata.supermarket.model.WeighedProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CheckoutTest {

    private static final PriceList PRICE_LIST = createPriceList();

    private static PriceList createPriceList() {
        final PriceList priceList = new PriceList();
        priceList.add(ItemName.American_Sweets_Per_Kilo, new BigDecimal("4.99"));
        priceList.add(ItemName.Milk, new BigDecimal("0.49"));
        priceList.add(ItemName.Pack_Of_Digestives, new BigDecimal("1.55"));
        priceList.add(ItemName.Pick_And_Mix_Per_Kilo, new BigDecimal("2.99"));
        return priceList;
    }

    @DisplayName("basket provides its total value when containing...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void basketProvidesTotalValue(String description, String expectedTotal, Iterable<Item> items, List<Discount> discounts) {
        final Basket basket = new Basket();
        items.forEach(basket::add);

        final Checkout checkout = new Checkout(basket, discounts);
        assertThat(new BigDecimal(expectedTotal)).isEqualByComparingTo((checkout.total()));
    }

    static Stream<Arguments> basketProvidesTotalValue() {
        return Stream.of(
                noItems(),
                aSingleItemPricedPerUnit(),
                multipleItemsPricedPerUnit(),
                aSingleItemPricedByWeight(),
                multipleItemsPricedByWeight(),
                aSingleItemWithDiscountApplied()
        );
    }

    private static Arguments aSingleItemPricedByWeight() {
        return Arguments.of("a single weighed item", "1.25", Collections.singleton(twoFiftyGramsOfAmericanSweets()), emptyList());
    }

    private static Arguments multipleItemsPricedByWeight() {
        return Arguments.of("multiple weighed items", "1.85",
                Arrays.asList(twoFiftyGramsOfAmericanSweets(), twoHundredGramsOfPickAndMix()), emptyList()
        );
    }

    private static Arguments multipleItemsPricedPerUnit() {
        return Arguments.of("multiple items priced per unit", "2.04",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk()), emptyList());
    }

    private static Arguments aSingleItemPricedPerUnit() {
        return Arguments.of("a single item priced per unit", "0.49", Collections.singleton(aPintOfMilk()), emptyList());
    }

    private static Arguments noItems() {
        return Arguments.of("no items", "0.00", emptyList(), emptyList());
    }

    private static Arguments aSingleItemWithDiscountApplied() {
        return Arguments.of("a single item priced per unit", "0.24", Collections.singleton(aPintOfMilk()), singletonList(discount("0.25")));
    }

    private static Item aPintOfMilk() {
        return new Product(ItemName.Milk, PRICE_LIST.getPrice(ItemName.Milk)).oneOf();
    }

    private static Item aPackOfDigestives() {
        return new Product(ItemName.Pack_Of_Digestives, PRICE_LIST.getPrice(ItemName.Pack_Of_Digestives)).oneOf();
    }

    private static WeighedProduct aKiloOfAmericanSweets() {
        return new WeighedProduct(ItemName.American_Sweets_Per_Kilo, PRICE_LIST.getPrice(ItemName.American_Sweets_Per_Kilo));
    }

    private static Item twoFiftyGramsOfAmericanSweets() {
        return aKiloOfAmericanSweets().weighing(new BigDecimal(".25"));
    }

    private static WeighedProduct aKiloOfPickAndMix() {
        return new WeighedProduct(ItemName.Pick_And_Mix_Per_Kilo, PRICE_LIST.getPrice(ItemName.Pick_And_Mix_Per_Kilo));
    }

    private static Item twoHundredGramsOfPickAndMix() {
        return aKiloOfPickAndMix().weighing(new BigDecimal(".2"));
    }

    private static Discount discount(final String discountApplied) {
        final Discount discount = mock(Discount.class);
        given(discount.calculate(any())).willReturn(new BigDecimal(discountApplied));
        return discount;
    }
}