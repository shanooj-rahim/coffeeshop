package charlenescoffeecorner.service;

import charlenescoffeecorner.dao.CoffeeCornerDAO;
import charlenescoffeecorner.model.Customer;
import charlenescoffeecorner.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

import static charlenescoffeecorner.model.Item.EXTRA_MILK;
import static charlenescoffeecorner.model.Type.BEVERAGE;
import static charlenescoffeecorner.model.Type.SNACK;

public class CoffeeCornerServiceImpl implements CoffeeCornerService {

    private CoffeeCornerDAO coffeeCornerDAO;

    public CoffeeCornerServiceImpl(CoffeeCornerDAO coffeeCornerDAO) {
        this.coffeeCornerDAO = coffeeCornerDAO;
    }

    @Override
    public double processCustomerOrder(Customer customer) {

        List<Product> extraOfferList = new ArrayList<>();
        List<Product> beverageOfferList = new ArrayList<>();
        Long customerStampCard = customer.getCustomerStampCard();
        List<Product> product = customer.getProduct();
        /*
         * Get the sum of the price of all the items in the order before applying the offers.
         * */
        double initialSum = getInitialSum.apply(product);
        /*
         * Get the sum of the prices of beverages after applying the beverage offer
         * Beverage offer = every 5th beverage is free.
         * */
        double beverageOfferSum = getBeverageOfferSum.apply(beverageOfferList, product);
        /*
         * Get the amount of the extra item if customer is eligible for Extra offer
         * Extra offer = if a customer orders a beverage and a snack, then one of the Extra is free
         * Extra = EXTRA_MILK, FOAMED_MILK, ROAST_COFFEE
         * In this case the free extra item is always EXTRA_MILK
         * */
        double extraOfferAmount = getExtraOffer.apply(extraOfferList, product);
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

        coffeeCornerDAO.printReceipt(customerStampCard, product, initialSum, beverageOfferSum, savings, beverageOfferList, extraOfferList);

        return total;
    }

    private Function<List<Product>, Double> getInitialSum = order -> order.stream().mapToDouble(o -> o.getItem().getPrice()).sum();
    private BiFunction<Double, Double, Double> getTotal = (initialSum, beverageOfferAmount) -> initialSum - beverageOfferAmount;
    private BiFunction<Double, Double, Double> getSavings = (beverageOfferAmount, extraOfferSavingAmount) -> beverageOfferAmount + extraOfferSavingAmount;
    private BiFunction<List<Product>, List<Product>, Double> getExtraOffer = (extraOffer, orderList) -> getExtraOfferApply(extraOffer, orderList);
    private BiPredicate<Long, Long> beverageSnackCount = (beverageCount, snackCount) -> beverageCount > 0 && snackCount > 0;
    private BiFunction<List<Product>, List<Product>, Double> getBeverageOfferSum = (beverageOffer, order) -> getBeverageOfferSumApply(beverageOffer, order);


    private Double getExtraOfferApply(List<Product> extraOffer, List<Product> productList) {
        /*
         * Get the count of beverages
         * */
        long beverageCount = productList
                .stream()
                .filter(beverage -> beverage.getItem().getType().equals(BEVERAGE.name()))
                .count();
        /*
         * Get the count of snacks
         * */
        long snackCount = productList
                .stream()
                .filter(snack -> snack.getItem().getType().equals(SNACK.name()))
                .count();

        /*
         * If a customer orders a beverage and a snack, one of the extra's is free.
         * EXTRA_MILK is selected for giving to the customer as free extra.
         * */
        if (beverageSnackCount.test(beverageCount, snackCount)) {
            extraOffer.add(new Product(EXTRA_MILK));
            return EXTRA_MILK.getPrice();
        }
        return Double.valueOf(0);
    }

    private Double getBeverageOfferSumApply(List<Product> beverageOffer, List<Product> product) {
        List<Product> beverageList = product.stream().filter(beverage -> beverage.getItem().getType().equals(BEVERAGE.name()))
                .collect(Collectors.toList());
        /*
         * every 5th beverage is for free.
         * */
        int count = 0;
        for (Product bevProduct : beverageList) {
            count++;
            if (count == 5) {
                beverageOffer.add(bevProduct);
                count = 0;
            }
        }
        return beverageOffer.stream().mapToDouble(beverageOrder -> beverageOrder.getItem().getPrice()).sum();
    }
}
