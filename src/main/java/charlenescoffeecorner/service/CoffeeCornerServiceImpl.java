package charlenescoffeecorner.service;

import charlenescoffeecorner.component.ReceiptGenerator;
import charlenescoffeecorner.model.Customer;
import charlenescoffeecorner.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static charlenescoffeecorner.model.Type.*;

public class CoffeeCornerServiceImpl implements CoffeeCornerService {

    private ReceiptGenerator receiptGenerator;

    public CoffeeCornerServiceImpl(ReceiptGenerator receiptGenerator) {
        this.receiptGenerator = receiptGenerator;
    }

    /**
     * @param customer -   contains customer details and the list of items the customer going to purchase
     * @return -   A double value i.e the grant total amount the customer has to pay for his purchase
     * Customer object contains customer details and the list of items the customer going to purchase
     */
    @Override
    public double processCustomerOrder(Customer customer) {

        Optional.ofNullable(customer)
                .map(customerObj -> checkCustomerItemListIsEmpty(customerObj))
                .orElseThrow(() -> new IllegalArgumentException("Customer cannot be null"));

        List<Item> itemList = customer.getItems();
        List<Item> beverageOfferList = new ArrayList<>();
        Long customerStampCard = customer.getCustomerStampCard();
        double beverageOfferSum = 0.0;
        /*
         * Get the sum of the price of all the items in the order before applying the offers.
         * */
        double initialSum = getInitialSum.apply(itemList);
        /*
         * Populate the beverage offer list if the customer is applicable
         * */
        populateBeverageOfferList.accept(beverageOfferList, itemList);
        /*
         * Check if the customer is eligible for BEVERAGE offer
         * */
        boolean isEligibleBeverageOffer = checkEligibleForBeverageOffer.apply(beverageOfferList);
        /*
         * If the customer is eligible for BEVERAGE offer, get the sum of the prices of beverages after applying the beverage offer
         * Beverage offer = every 5th beverage is free.
         * */
        if (isEligibleBeverageOffer) {
            beverageOfferSum = getBeverageOfferSum.apply(beverageOfferList);
        }
        /*
         * Check if customer is eligible for Extra offer
         * Extra offer = if a customer orders a hot beverage and a snack, then one of the Extra is free
         * Extra = EXTRA_MILK, FOAMED_MILK, ROAST_COFFEE
         * In this case the free extra item is always EXTRA_MILK
         * */
        boolean isEligibleExtraOffer = checkEligibleForExtraOffer.apply(itemList);
        /*
         * The total amount the customer have to pay for this purchase
         * Total = initial sum - the amount reduced for the beverage offer from the initial sum.
         * Note : Amount saved in extra offer is not considered for calculating the total, because the Extra offer is something that
         * is not in the customer ordered list, it is an complimentary offer from the coffee shop. So it is not considered for
         * the total.
         * */
        double grandTotal = getGrandTotal.apply(initialSum, beverageOfferSum);

        receiptGenerator.generateReceipt(customerStampCard, itemList, initialSum, grandTotal, beverageOfferSum,
                beverageOfferList, isEligibleExtraOffer, isEligibleBeverageOffer);

        return grandTotal;
    }

    private List<Item> checkCustomerItemListIsEmpty(Customer customer) {
        return Optional.ofNullable(customer.getItems())
                .filter(item -> item.size() > 0)
                .orElseThrow(() -> new NullPointerException("Item list cannot be null or empty"));
    }

    private Function<List<Item>, Double> getInitialSum = getSum();

    private Function<List<Item>, Double> getBeverageOfferSum = getSum();

    private Function<List<Item>, Double> getSum() {
        return orderList -> orderList.stream().mapToDouble(item -> item.getPrice()).sum();
    }

    private BiFunction<Double, Double, Double> getGrandTotal = (initialSum, beverageOfferAmount) -> initialSum - beverageOfferAmount;

    private Function<List<Item>, Boolean> checkEligibleForExtraOffer = itemList -> isEligibleForExtraOffer(itemList);

    private Function<List<Item>, Boolean> checkEligibleForBeverageOffer = itemList -> itemList.size() > 0;

    private BiConsumer<List<Item>, List<Item>> populateBeverageOfferList = (beverageOffer, order) -> populateBeverageOfferList(beverageOffer, order);

    private Boolean isEligibleForExtraOffer(List<Item> itemList) {
        /*
         * Get the count of beverages
         * */
        long beverageCount = itemList
                .stream()
                .filter(beverage -> beverage.getType() == HOT_BEVERAGE)
                .count();
        /*
         * Get the count of snacks
         * */
        long snackCount = itemList
                .stream()
                .filter(snack -> snack.getType() == SNACK)
                .count();
        /*
         * If a customer orders a hot beverage and a snack, one of the extra's is free.
         * EXTRA_MILK is selected for giving to the customer as free extra.
         * */
        return beverageCount > 0 && snackCount > 0;
    }

    private void populateBeverageOfferList(List<Item> beverageOffer, List<Item> itemList) {
        List<Item> beverageList = itemList.stream()
                .filter(beverage -> (beverage.getType() == COLD_BEVERAGE || beverage.getType() == HOT_BEVERAGE))
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
    }
}
