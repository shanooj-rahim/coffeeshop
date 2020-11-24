package charlenescoffeecorner.dao;

import charlenescoffeecorner.model.Order;

import java.util.List;

public interface CoffeeCornerDAO {
    void printReceipt(Long customerStampCard, List<Order> order, double initialSum, double beverageOfferSum, double savings,
                      List<Order> beverageOfferList, List<Order> extraOfferList);
}
