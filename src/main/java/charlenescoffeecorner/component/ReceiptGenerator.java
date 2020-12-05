package charlenescoffeecorner.component;

import charlenescoffeecorner.model.Item;
import charlenescoffeecorner.model.Type;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static charlenescoffeecorner.model.Type.COLD_BEVERAGE;
import static charlenescoffeecorner.model.Type.HOT_BEVERAGE;

public class ReceiptGenerator {

    private static final char NEWLINE = '\n';
    private static final String LINE = "=================================================" + NEWLINE;

    /*
     * generateReceipt method is to generate the receipt for the purchase by customer.
     * Input parameters
     *   -   (Long) customer stamp card  -   Unique card number of a customer
     *   -   List<Item> items            -   All the items the customer ordered in this purchase
     *   -   (double) initialSum         -   The sum of all the item prices with out calculating the offers
     *   -   (double) beverageOfferSum   -   The sum of the prices of the beverages the customer received as part of the beverage offer
     *   -   (double) savings            -   Total amount saved by the customer as a part of the 2 offers
     *   -   List<Item> beverageOfferList-   List of beverages the customer received as part of the beverage offer
     *   -   List<Item> extraOfferList   -   List of extra's the customer received as part of the extra offer
     * */
    public void generateReceipt(Long customerStampCard, List<Item> items, double initialSum, double beverageOfferSum,
                                double savings, List<Item> beverageOfferList, List<Item> extraOfferList) {
        /*
         * Receipt Header starts
         * */
        print.accept(LINE);
        print.accept("            CHARLENES COFFEE CORNER" + NEWLINE);
        print.accept("                " + LocalDateTime.now().getYear() + "/");
        print.accept(LocalDateTime.now().getMonth().getValue() + "/");
        print.accept(LocalDateTime.now().getDayOfMonth() + " ");
        print.accept(LocalDateTime.now().getHour() + ":");
        print.accept(String.valueOf(LocalDateTime.now().getMinute()) + NEWLINE);
        print.accept(LINE);
        /*
         * Customer no and receipt column heading starts
         * */
        print.accept("Customer No : " + customerStampCard + NEWLINE);
        print.accept(String.format("%-15s %5s %13s %10s", "Item", "Type", "Quantity", "Price") + NEWLINE);
        print.accept(LINE);
        /*
         * Group by each item from the list of orders
         * Grouping is required to show the quantity of each item
         * */
        Map<Item, Long> groupByItem = getItemsGrouped(items);

        /*
         * Group by each item from the beveragesList
         * Grouping is required to show the quantity of each item
         * */
        Map<Item, Long> groupByItemBeveragesOffer = getItemsGrouped(beverageOfferList);

        /*
         * This prints all the items in the list to the receipt
         * */
        groupByItem.entrySet().stream().forEach(item -> displayGroupByItem.accept(item));

        /*
         * This prints the beverages list which added as a part of the offer
         * offer - for every 5th beverage, the 5th beverage is free.
         * This will print the price in -ve since it is free.
         * */
        groupByItemBeveragesOffer.entrySet().stream().forEach(item -> displayGroupByItemOffer.accept(item));

        /*
         * This part displays the total amount and the total items
         * */
        print.accept(LINE);
        print.accept("Total=                                   " + String.format("%.2f", (initialSum - beverageOfferSum)) + NEWLINE);
        print.accept("Total Items=                              " + String.format("%s", (items.size() + beverageOfferList.size())) + NEWLINE);
        /*
         * This part displays how much the customer saved in the current purchase
         * */
        print.accept("===============YOU HAVE SAVED====================" + NEWLINE);

        /*
         * This list will contain all the items the customer received as a part of the 2 offers program
         * 1st offer - every 5th beverage is free
         * 2nd offer - if the customer buys a beverage and a snack, then an 'Extra' is free
         * This list is used to display the contents under 'YOU HAVE SAVED' section of the report
         * */
        List<Item> allOfferList = Stream.of(extraOfferList, beverageOfferList)
                .flatMap(item -> item.stream())
                .collect(Collectors.toList());
        /*
         * Group the list based on the item and display it.
         * Grouping is required to show the quantity.
         * Price is calculated by multiplying the quantity and price.
         * */
        Map<Item, Long> groupByItemFullOffer = getItemsGrouped(allOfferList);

        /*
         * Display the orders under YOU HAVE SAVED portion of the receipt
         * This will display all the items the customer received as a part of the offers
         * */
        groupByItemFullOffer.entrySet().stream().forEach(item -> displayGroupByItem.accept(item));

        /*
         * Total savings portion of the receipt with total savings displayed
         * */
        print.accept(LINE);
        print.accept("Total Savings=                            " + String.format("%.2f", savings) + NEWLINE);
        print.accept("==============THANK YOU AND VISIT AGAIN!=========" + NEWLINE);
    }

    /*
     * To print the output to the console
     * */
    Consumer<String> print = line -> System.out.print(line);

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
