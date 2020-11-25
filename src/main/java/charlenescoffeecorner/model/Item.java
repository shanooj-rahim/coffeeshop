package charlenescoffeecorner.model;

import static charlenescoffeecorner.model.Type.*;

public enum Item {

    COFFEE_SMALL(2.50, BEVERAGE.name()),
    COFFEE_MEDIUM(3.00, BEVERAGE.name()),
    COFFEE_LARGE(3.50, BEVERAGE.name()),
    BACON_ROLL(4.50, SNACK.name()),
    ORANGE_JUICE(3.95, BEVERAGE.name()),
    EXTRA_MILK(0.30, EXTRA.name()),
    FOAMED_MILK(0.50, EXTRA.name()),
    ROAST_COFFEE(0.90, EXTRA.name());
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
