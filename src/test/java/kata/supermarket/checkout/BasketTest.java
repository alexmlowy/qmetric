package kata.supermarket.checkout;

import kata.supermarket.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BasketTest {

    private Basket basket;

    @BeforeEach
    public void setUp() {
        this.basket = new Basket();
    }

    @Test
    public void noItemsInBasket() {
        assertThat(basket.items()).isEmpty();
    }

    @Test
    public void singleItemPerType() {
        basket.add(item(ItemName.Milk));
        basket.add(item(ItemName.Pack_Of_Digestives));

        final Map<ItemName, List<Item>> items = basket.items();
        assertThat(items).isNotEmpty();
        assertThat(items.get(ItemName.Milk)).hasSize(1);
        assertThat(items.get(ItemName.Pack_Of_Digestives)).hasSize(1);
    }

    @Test
    public void multipleItemsPerType() {
        basket.add(item(ItemName.Milk));
        basket.add(item(ItemName.Pack_Of_Digestives));
        basket.add(item(ItemName.Milk));

        final Map<ItemName, List<Item>> items = basket.items();
        assertThat(items).isNotEmpty();
        assertThat(items.get(ItemName.Milk)).hasSize(2);
        assertThat(items.get(ItemName.Pack_Of_Digestives)).hasSize(1);
    }

    private Item item(final ItemName name) {
        final Item item = mock(Item.class);
        given(item.name()).willReturn(name);
        return item;
    }
}