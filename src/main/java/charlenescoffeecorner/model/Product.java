package charlenescoffeecorner.model;

public class Product {
    private Item item;

    public Product(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public String toString() {
        return "Order{" +
                "item=" + item +
                '}';
    }
}
