package charlenescoffeecorner.model;

import static charlenescoffeecorner.model.Type.*;

public enum Item {

    COFFEE_SMALL(2.50, HOT_BEVERAGE, "Coffee Small", "Beverage"),
    COFFEE_MEDIUM(3.00, HOT_BEVERAGE, "Coffee Medium", "Beverage"),
    COFFEE_LARGE(3.50, HOT_BEVERAGE, "Coffee Large", "Beverage"),
    BACON_ROLL(4.50, SNACK, "Bacon Roll", "Snack"),
    ORANGE_JUICE(3.95, COLD_BEVERAGE, "Orange Juice", "Beverage"),
    EXTRA_MILK(0.30, EXTRA, "Extra Milk", "Extra"),
    FOAMED_MILK(0.50, EXTRA, "Foamed Milk", "Extra"),
    ROAST_COFFEE(0.90, EXTRA, "Roast Coffee", "Extra");

    private double price;
    private Type type;
    private String displayItemName;
    private String displayTypeName;

    Item(double price, Type type, String displayItemName, String displayTypeName) {
        this.price = price;
        this.type = type;
        this.displayItemName = displayItemName;
        this.displayTypeName = displayTypeName;
    }

    public double getPrice() {
        return price;
    }

    public Type getType() {
        return type;
    }

    public String getDisplayItemName() {
        return displayItemName;
    }

    public String getDisplayTypeName() {
        return displayTypeName;
    }

    /*
     * This method is for getting the item selected for Extra offer.
     * EXTRA_MILK is now selected for extra offer
     * */
    public static Item getExtraOfferItem() {
        return EXTRA_MILK;
    }
}
