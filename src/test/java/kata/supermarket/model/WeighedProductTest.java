package kata.supermarket.model;

import kata.supermarket.checkout.ItemName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeighedProductTest {

    @ParameterizedTest
    @MethodSource
    void itemFromWeighedProductHasExpectedUnitPrice(ItemName productName, String pricePerKilo, String weightInKilos, String expectedPrice) {
        final WeighedProduct weighedProduct = new WeighedProduct(productName, new BigDecimal(pricePerKilo));
        final Item weighedItem = weighedProduct.weighing(new BigDecimal(weightInKilos));
        assertEquals(new BigDecimal(expectedPrice), weighedItem.price());
    }

    static Stream<Arguments> itemFromWeighedProductHasExpectedUnitPrice() {
        return Stream.of(
                Arguments.of(ItemName.Milk, "100.00", "1.00", "100.00"),
                Arguments.of(ItemName.Milk, "100.00", "0.33333", "33.33"),
                Arguments.of(ItemName.Milk, "100.00", "0.33335", "33.34"),
                Arguments.of(ItemName.Milk, "100.00", "0", "0.00")
        );
    }

}