package charlenescoffeecorner.model;

import static charlenescoffeecorner.model.Type.*;

public enum Item {

    COFFEE_SMALL(2.50, HOT_BEVERAGE),
    COFFEE_MEDIUM(3.00, HOT_BEVERAGE),
    COFFEE_LARGE(3.50, HOT_BEVERAGE),
    BACON_ROLL(4.50, SNACK),
    ORANGE_JUICE(3.95, COLD_BEVERAGE),
    EXTRA_MILK(0.30, EXTRA),
    FOAMED_MILK(0.50, EXTRA),
    ROAST_COFFEE(0.90, EXTRA);

    private double price;
    private Type type;

    Item(double price, Type type) {
        this.price = price;
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public Type getType() {
        return type;
    }

    /*
     * This method is for getting the item selected for Extra offer.
     * EXTRA_MILK is now selected for extra offer
     * */
    public static Item getExtraOfferItem() {
        return EXTRA_MILK;
    }
}
