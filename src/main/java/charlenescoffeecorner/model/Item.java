package charlenescoffeecorner.model;

public enum Item {

    COFFEE_SMALL(2.50, "BEVERAGE"),
    COFFEE_MEDIUM(3.00, "BEVERAGE"),
    COFFEE_LARGE(3.50, "BEVERAGE"),
    BACON_ROLL(4.50, "SNACK"),
    ORANGE_JUICE(3.95, "BEVERAGE"),
    EXTRA_MILK(0.30, "EXTRA"),
    FOAMED_MILK(0.50, "EXTRA"),
    ROAST_COFFEE(0.90, "EXTRA");
    private double price;
    private String type;

    Item(double price, String type) {
        this.price = price;
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }
}
