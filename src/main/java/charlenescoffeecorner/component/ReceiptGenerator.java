package charlenescoffeecorner.component;

import charlenescoffeecorner.model.Item;
import charlenescoffeecorner.model.Type;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static charlenescoffeecorner.model.Type.COLD_BEVERAGE;
import static charlenescoffeecorner.model.Type.HOT_BEVERAGE;

public class ReceiptGenerator {

    private static final char NEWLINE = '\n';
    private static final String LINE = "=================================================" + NEWLINE;
    private static final String ITEMS_LINE = "-------------------------------------------------" + NEWLINE;
    private static final String EMPTY_STRING = "";
    private static final String SLASH = "/";

    /**
     * @param customerStampCard       -   Unique card number of a customer
     * @param items                   -   All the items the customer ordered in this purchase
     * @param initialSum              -   The sum of all the item prices with out calculating the offers
     * @param grandTotal              -   The total amount customer have to spent for this purchase after the offers
     * @param beverageOfferSum        -   The sum of the prices of the beverages the customer received as part of the beverage offer
     * @param beverageOfferList       -   List of beverages the customer received as part of the beverage offer
     * @param isEligibleExtraOffer    -   True - if the customer is eligible for extra offer, else false
     * @param isEligibleBeverageOffer -   True - if the customer is eligible for BEVERAGE offer, else false
     */
    public void generateReceipt(Long customerStampCard, List<Item> items, double initialSum, double grandTotal, double beverageOfferSum,
                                List<Item> beverageOfferList, boolean isEligibleExtraOffer, boolean isEligibleBeverageOffer) {
        /*
         * Receipt Header
         * */
        print.accept(LINE);
        print.accept(String.format("%35s", "CHARLENES COFFEE CORNER") + NEWLINE);
        print.accept(String.format("%20s", LocalDateTime.now().getYear()) + SLASH);
        print.accept(LocalDateTime.now().getMonth().getValue() + SLASH);
        print.accept(LocalDateTime.now().getDayOfMonth() + " ");
        print.accept(LocalDateTime.now().getHour() + ":");
        print.accept(String.valueOf(LocalDateTime.now().getMinute()) + NEWLINE);
        print.accept(ITEMS_LINE);
        /*
         * Customer stampcard number and receipt column heading
         * */
        print.accept("Customer No : " + customerStampCard + NEWLINE);
        print.accept(String.format("%-15s %5s %13s %10s", "Item", "Type", "Quantity", "Price") + NEWLINE);
        print.accept(ITEMS_LINE);
        /*
         * This prints all the items in the list to the receipt
         * Group by each item from the list of orders
         * Grouping is required to show the quantity of each item
         * */
        Map<Item, Long> groupByItem = getItemsGrouped(items);
        groupByItem.entrySet().stream().forEach(item -> displayGroupByItem.accept(item));
        /*
         * This part displays the total amount and the total quantity
         * */
        print.accept(EMPTY_STRING + NEWLINE);
        print.accept("Total=");
        print.accept(String.format("%25s %8s %.2f", String.valueOf(items.size()), EMPTY_STRING, initialSum) + NEWLINE);
        /*
         * This prints the beverages list which added as a part of the BEVERAGE offer if eligible
         * Group by each item from the beveragesList
         * Grouping is required to show the quantity of each item
         * This will print the price in -ve since it is free.
         * If the customer is not eligible for BEVERAGE offer, this part will not be displayed in the receipt
         * */
        if (isEligibleBeverageOffer) {
            print.accept("=================DISCOUNT========================" + NEWLINE);
            Map<Item, Long> groupByItemBeveragesOffer = getItemsGrouped(beverageOfferList);
            groupByItemBeveragesOffer.entrySet().stream().forEach(item -> displayGroupByItemOffer.accept(item));
        }
        /*
         * This part displays the grand total amount which the customer have to pay after applying the offers.
         * */
        print.accept(LINE);
        print.accept("Grand Total=");
        print.accept(String.format("%28s %.2f", EMPTY_STRING, grandTotal) + NEWLINE);
        /*
         * This part displays if customer is eligible for any EXTRA offers.
         * The price of the EXTRA is shown as 0.00 since it is a complementary offer from the coffee shop
         * If the customer is not eligible for EXTRA offer, this part will not be displayed in the receipt
         * */
        if (isEligibleExtraOffer) {
            print.accept("=================EXTRA OFFER====================" + NEWLINE);
            displayExtraOffer.accept(Item.getExtraOfferItem());
        }

        /*
         * Total savings portion of the receipt with total savings displayed
         * */
        print.accept(LINE);
        print.accept("Total Savings=");
        print.accept(String.format("%27s %.2f", EMPTY_STRING, beverageOfferSum) + NEWLINE);
        print.accept("============THANK YOU AND VISIT AGAIN!===========" + NEWLINE);
    }

    /*
     * To print the output to the console
     * */
    private Consumer<String> print = line -> System.out.print(line);

    /*
     * Group the list based on items.
     * */
    private Map<Item, Long> getItemsGrouped(List<Item> itemList) {
        return itemList.stream()
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()));
    }

    /*
     * This prints the beverages list which added as a part of the offer
     * offer - for every 5th beverage, the 5th beverage is free.
     * This will print the price in -ve since it is free.
     * */
    private Consumer<Map.Entry<Item, Long>> displayGroupByItemOffer = item -> displayOfferItems(item);

    /*
     * Method to print the offer items in to the receipt
     * */
    private void displayOfferItems(Map.Entry<Item, Long> item) {
        Type type = item.getKey().getType();
        print.accept(String.format("%-15s %5s" + getQuantityFormatting(type) + "%15.2f", item.getKey(),
                getBeverageTypeForDisplay(type), item.getValue(), (item.getValue() * item.getKey().getPrice()) * -1) + NEWLINE);
    }

    /*
     * Method to print the extra offer items in to the receipt
     * Quantity is always 1
     * Price is 0.00 since this is an offer from the shop to the customer.
     * */
    private Consumer<Item> displayExtraOffer = item -> {
        print.accept(String.format("%-15s %5s" + getQuantityFormatting(item.getType()) + "%15.2f", item,
                item.getType(), 1, 0.00) + NEWLINE);
    };

    /*
     * Method to print the offer items in to the receipt
     * */
    private Consumer<Map.Entry<Item, Long>> displayGroupByItem = item -> {
        Type type = item.getKey().getType();
        print.accept(String.format("%-15s %5s" + getQuantityFormatting(type) + "%15.2f", item.getKey(),
                getBeverageTypeForDisplay(type), item.getValue(), (item.getValue() * item.getKey().getPrice())) + NEWLINE);
    };

    /*
     * Method to format the entries in to the receipt for snacks and extra
     * */
    private String getQuantityFormatting(Type item) {
        return item == Type.SNACK || item == Type.EXTRA ? "%10s" : "%7s";
    }

    /*
     * Method to print the type of the beverage as BEVERAGE if the type is HOT_BEVERAGE or COLD_BEVERAGE
     * */
    private String getBeverageTypeForDisplay(Type beverageType) {
        return beverageType == HOT_BEVERAGE || beverageType == COLD_BEVERAGE ? "BEVERAGE" : beverageType.name();
    }
}
