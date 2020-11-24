package charlenescoffeecorner.service;

import charlenescoffeecorner.dao.CoffeeCornerDAO;
import charlenescoffeecorner.model.Customer;
import charlenescoffeecorner.model.Item;
import charlenescoffeecorner.model.Order;
import charlenescoffeecorner.model.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CoffeeCornerServiceImpl implements CoffeeCornerService {

    private CoffeeCornerDAO coffeeCornerDAO;

    public CoffeeCornerServiceImpl(CoffeeCornerDAO coffeeCornerDAO) {
        this.coffeeCornerDAO = coffeeCornerDAO;
    }

    @Override
    public double processCustomer(Customer customer) {

        List<Order> extraOfferList = new ArrayList<>();
        List<Order> beverageOfferList = new ArrayList<>();
        Long customerStampCard = customer.getCustomerStampCard();
        List<Order> order = customer.getOrder();

        double initialSum = getInitialSum.apply(order);
        double beverageOfferSum = getBeverageOfferSum.apply(beverageOfferList, order);
        double extraOfferSavingAmount = getExtraOffer.apply(extraOfferList, order);
        double savings = getSavings.apply(beverageOfferSum, extraOfferSavingAmount);
        double total = getTotal.apply(initialSum, beverageOfferSum);

        coffeeCornerDAO.printReceipt(customerStampCard, order, initialSum, beverageOfferSum, savings, beverageOfferList, extraOfferList);

        return total;
    }

    private Function<List<Order>, Double> getInitialSum = order -> order.stream().mapToDouble(o -> o.getItem().getPrice()).sum();
    private BiFunction<Double, Double, Double> getTotal = (initialSum, beverageOfferAmount) -> initialSum - beverageOfferAmount;
    private BiFunction<Double, Double, Double> getSavings = (beverageOfferAmount, extraOfferSavingAmount) -> beverageOfferAmount + extraOfferSavingAmount;
    private BiFunction<List<Order>, List<Order>, Double> getExtraOffer = (extraOffer, orderList) -> getExtraOfferApply(extraOffer, orderList);
    private BiPredicate<Long, Long> beverageSnackCount = (beverageCount, snackCount) -> beverageCount > 0 && snackCount > 0;
    private BiFunction<List<Order>, List<Order>, Double> getBeverageOfferSum = (beverageOffer, order) -> getBeverageOfferSumApply(beverageOffer, order);


    private Double getExtraOfferApply(List<Order> extraOffer, List<Order> orderList) {
        /*
         * Get the count of beverages
         * */
        long beverageCount = orderList
                .stream()
                .filter(beverage -> beverage.getItem().getType().equals(Type.BEVERAGE.name()))
                .count();
        /*
         * Get the count of snacks
         * */
        long snackCount = orderList
                .stream()
                .filter(snack -> snack.getItem().getType().equals(Type.SNACK.name()))
                .count();

        /*
         * If a customer orders a beverage and a snack, one of the extra's is free.
         * EXTRA_MILK is selected for giving to the customer as free extra.
         * */
        if (beverageSnackCount.test(beverageCount, snackCount)) {
            extraOffer.add(new Order(Item.EXTRA_MILK));
            return Item.EXTRA_MILK.getPrice();
        }
        return Double.valueOf(0);
    }

    private Double getBeverageOfferSumApply(List<Order> beverageOffer, List<Order> order) {
        List<Order> beverageList = order.stream().filter(beverage -> beverage.getItem().getType().equals(Type.BEVERAGE.name()))
                .collect(Collectors.toList());
        /*
         * every 5th beverage is for free.
         * */
        int count = 0;
        for (Order bevOrder : beverageList) {
            count++;
            if (count == 5) {
                beverageOffer.add(bevOrder);
                count = 0;
            }
        }
        return beverageOffer.stream().mapToDouble(beverageOrder -> beverageOrder.getItem().getPrice()).sum();
    }
}
