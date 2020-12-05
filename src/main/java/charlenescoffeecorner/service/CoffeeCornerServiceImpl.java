package charlenescoffeecorner.service;

import charlenescoffeecorner.component.ReceiptGenerator;
import charlenescoffeecorner.model.Customer;
import charlenescoffeecorner.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

import static charlenescoffeecorner.model.Type.*;

public class CoffeeCornerServiceImpl implements CoffeeCornerService {

    private ReceiptGenerator receiptGenerator;

    public CoffeeCornerServiceImpl(ReceiptGenerator receiptGenerator) {
        this.receiptGenerator = receiptGenerator;
    }

    /*
     * processCustomerOrder method is for processing the customer and his orders.
     * Input parameter - Customer object
     * Customer object contains customer details and the list of items the customer going to purchase
     * Return a double value i.e the total amount the customer has to pay for his purchase
     * */
    @Override
    public double processCustomerOrder(Customer customer) {

        List<Item> extraOfferList = new ArrayList<>();
        List<Item> beverageOfferList = new ArrayList<>();
        Long customerStampCard = customer.getCustomerStampCard();
        List<Item> itemList = customer.getItems();
        /*
         * Get the sum of the price of all the items in the order before applying the offers.
         * */
        double initialSum = getInitialSum.apply(itemList);
        /*
         * Get the sum of the prices of beverages after applying the beverage offer
         * Beverage offer = every 5th beverage is free.
         * */
        double beverageOfferSum = getBeverageOfferSum.apply(beverageOfferList, itemList);
        /*
         * Get the amount of the extra item if customer is eligible for Extra offer
         * Extra offer = if a customer orders a beverage and a snack, then one of the Extra is free
         * Extra = EXTRA_MILK, FOAMED_MILK, ROAST_COFFEE
         * In this case the free extra item is always EXTRA_MILK
         * */
        double extraOfferAmount = getExtraOffer.apply(extraOfferList, itemList);
        /*
         * Total savings by the customer in this purchase
         * total savings = amount saved in beverage offer + amount saved in extra offer (if any)
         * */
        double savings = getSavings.apply(beverageOfferSum, extraOfferAmount);
        /*
         * The total amount the customer have to pay for this purchase
         * Total = initial sum - the amount reduced for the beverage offer from the initial sum.
         * Note : Amount saved in extra offer is not considered for calculating the total, because the Extra offer is something that
         * is not in the customer ordered list, it is an complimentary offer from the coffee shop. So it is not considered for
         * the total. It is only considered for calculating the 'total savings' by the customer in this purchase.
         * */
        double total = getTotal.apply(initialSum, beverageOfferSum);

        receiptGenerator.generateReceipt(customerStampCard, itemList, initialSum, beverageOfferSum, savings, beverageOfferList, extraOfferList);

        return total;
    }

    private Function<List<Item>, Double> getInitialSum = order -> order.stream().mapToDouble(item -> item.getPrice()).sum();
    private BiFunction<Double, Double, Double> getTotal = (initialSum, beverageOfferAmount) -> initialSum - beverageOfferAmount;
    private BiFunction<Double, Double, Double> getSavings = (beverageOfferAmount, extraOfferSavingAmount) -> beverageOfferAmount + extraOfferSavingAmount;
    private BiFunction<List<Item>, List<Item>, Double> getExtraOffer = (extraOffer, orderList) -> getExtraOfferApply(extraOffer, orderList);
    private BiPredicate<Long, Long> hotBeverageSnackCount = (beverageCount, snackCount) -> beverageCount > 0 && snackCount > 0;
    private BiFunction<List<Item>, List<Item>, Double> getBeverageOfferSum = (beverageOffer, order) -> getBeverageOfferSumApply(beverageOffer, order);


    private Double getExtraOfferApply(List<Item> extraOffer, List<Item> itemList) {
        /*
         * Get the count of beverages
         * */
        long beverageCount = itemList
                .stream()
                .filter(beverage -> beverage.getType().equals(HOT_BEVERAGE.name()))
                .count();
        /*
         * Get the count of snacks
         * */
        long snackCount = itemList
                .stream()
                .filter(snack -> snack.getType().equals(SNACK.name()))
                .count();

        /*
         * If a customer orders a beverage and a snack, one of the extra's is free.
         * EXTRA_MILK is selected for giving to the customer as free extra.
         * */
        if (hotBeverageSnackCount.test(beverageCount, snackCount)) {
            Item extraOfferItem = Item.getExtraOfferItem();
            extraOffer.add(extraOfferItem);
            return extraOfferItem.getPrice();
        }
        return Double.valueOf(0);
    }

    private Double getBeverageOfferSumApply(List<Item> beverageOffer, List<Item> itemList) {
        List<Item> beverageList = itemList.stream()
                .filter(beverage -> beverage.getType().equals(COLD_BEVERAGE.name()) || beverage.getType().equals(HOT_BEVERAGE.name()))
                .collect(Collectors.toList());
        /*
         * every 5th beverage is for free.
         * */
        int count = 0;
        for (Item bevItem : beverageList) {
            count++;
            if (count == 5) {
                beverageOffer.add(bevItem);
                count = 0;
            }
        }
        return beverageOffer.stream().mapToDouble(beverageOrder -> beverageOrder.getPrice()).sum();
    }
}
