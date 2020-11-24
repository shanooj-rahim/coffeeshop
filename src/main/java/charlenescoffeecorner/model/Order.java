package charlenescoffeecorner.model;

public class Order {
    private Item item;

    public Order(Item item) {
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
