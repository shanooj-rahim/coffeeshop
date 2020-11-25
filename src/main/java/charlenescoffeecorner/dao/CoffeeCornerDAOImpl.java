package charlenescoffeecorner.dao;

import charlenescoffeecorner.model.Item;
import charlenescoffeecorner.model.Product;
import charlenescoffeecorner.model.Type;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CoffeeCornerDAOImpl implements CoffeeCornerDAO {
    @Override
    public void printReceipt(Long customerStampCard, List<Product> product, double initialSum, double beverageOfferSum,
                             double savings, List<Product> beverageOfferList, List<Product> extraOfferList) {
        /*
         * Receipt Header starts
         * */
        System.out.println("=================================================");
        System.out.println("            CHARLENES COFFEE CORNER");
        System.out.print("                " + LocalDateTime.now().getYear() + "/");
        System.out.print(LocalDateTime.now().getMonth().getValue() + "/");
        System.out.print(LocalDateTime.now().getDayOfMonth() + " ");
        System.out.print(LocalDateTime.now().getHour() + ":");
        System.out.println(LocalDateTime.now().getMinute());
        System.out.println("=================================================");
        /*
         * Customer no and receipt column heading starts
         * */
        System.out.println("Customer No : " + customerStampCard);
        System.out.println(String.format("%-15s %5s %13s %10s", "Item", "Type", "Quantity", "Price"));
        System.out.println("=================================================");
        /*
         * Group by each item from the list of orders
         * Grouping is required to show the quantity of each item
         * */
        Map<Item, Long> groupByItem = product.stream().collect(Collectors.groupingBy(Product::getItem, Collectors.counting()));

        /*
         * Group by each item from the beveragesList
         * Grouping is required to show the quantity of each item
         * */
        Map<Item, Long> groupByItemBeveragesOffer = beverageOfferList.stream().collect(Collectors.groupingBy(Product::getItem, Collectors.counting()));

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
        System.out.println("=================================================");
        System.out.println("Total=                                   " + String.format("%.2f", (initialSum - beverageOfferSum)));
        System.out.println("Total Items=                              " + String.format("%s", (product.size() + beverageOfferList.size())));
        /*
         * This part displays how much the customer saved in the current purchase
         * */
        System.out.println("===============YOU HAVE SAVED====================");

        /*
         * This list will contain all the orders the customer received as a part of the 2 offers
         * 1st offer - every 5th beverage is free
         * 2nd offer - if the customer buys a beverage and a snack, then an 'Extra' is free
         * This list is used to display the contents under 'YOU HAVE SAVED' section of the report
         * */
        extraOfferList.addAll(beverageOfferList);

        /*
         * Group the list based on the item and display it.
         * Grouping is required to show the quantity.
         * Price is calculated by multiplying the quantity and price.
         * */
        Map<Item, Long> groupByItemFullOffer = extraOfferList.stream()
                .collect(Collectors.groupingBy(Product::getItem, Collectors.counting()));

        /*
         * Display the orders under YOU HAVE SAVED portion of the receipt
         * This will display all the items the customer received as a part of the offers
         * */
        groupByItemFullOffer.entrySet().stream().forEach(item -> displayGroupByItemFullOffer.accept(item));

        /*
         * Total savings portion of the receipt with total savings displayed
         * */
        System.out.println("=================================================");
        System.out.println("Total Savings=                            " + String.format("%.2f", savings));
        System.out.println("==============THANK YOU AND VISIT AGAIN!=========");
    }

    /*
     * This prints the beverages list which added as a part of the offer
     * offer - for every 5th beverage, the 5th beverage is free.
     * This will print the price in -ve since it is free.
     * */
    private Consumer<Map.Entry<Item, Long>> displayGroupByItemOffer = item -> displayOfferItems(item);
    /*
     * Method to display all the items the customer received as a part of the offers
     * */
    private Consumer<Map.Entry<Item, Long>> displayGroupByItemFullOffer = item -> displayOfferItems(item);

    /*
     * Method to print the offer items in to the receipt
     * */
    private void displayOfferItems(Map.Entry<Item, Long> item) {
        System.out.println(String.format("%-15s %5s" + getQuantityFormatting(item) + "%15.2f", item.getKey(),
                item.getKey().getType(), item.getValue(), (item.getValue() * item.getKey().getPrice()) * -1));
    }

    /*
     * Method to print the offer items in to the receipt
     * */
    private Consumer<Map.Entry<Item, Long>> displayGroupByItem = item -> {
        System.out.println(String.format("%-15s %5s" + getQuantityFormatting(item) + "%15.2f", item.getKey(),
                item.getKey().getType(), item.getValue(), (item.getValue() * item.getKey().getPrice())));
    };

    /*
     * Method to format the entries in to the receipt for snacks and extra
     * */
    private String getQuantityFormatting(Map.Entry<Item, Long> item) {
        if (item.getKey().getType().equals(Type.SNACK.name()) || item.getKey().getType().equals(Type.EXTRA.name())) {
            return "%10s";
        }
        return "%7s";
    }
}
