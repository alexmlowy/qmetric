package kata.supermarket.checkout;

public enum ItemName {

    Milk(false),
    Pack_Of_Digestives(false),
    Pick_And_Mix_Per_Kilo(true),
    American_Sweets_Per_Kilo(true);

    private final boolean weighedProduct;

    ItemName(final boolean weighedProduct) {
        this.weighedProduct = weighedProduct;
    }

    public boolean isWeighedProduct() {
        return weighedProduct;
    }
}
