package kata.supermarket.checkout;

import kata.supermarket.model.Item;

import java.util.*;

public class Basket {

    private final Map<ItemName, List<Item>> items;

    public Basket() {
        this.items = new HashMap<>();
    }

    public void add(final Item item) {
        this.items.computeIfAbsent(item.name(), v -> new ArrayList<>()).add(item);
    }

    Map<ItemName, List<Item>> items() {
        return Collections.unmodifiableMap(items);
    }
}
