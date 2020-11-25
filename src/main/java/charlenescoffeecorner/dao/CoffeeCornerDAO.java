package charlenescoffeecorner.dao;

import charlenescoffeecorner.model.Product;

import java.util.List;

public interface CoffeeCornerDAO {
    void printReceipt(Long customerStampCard, List<Product> product, double initialSum, double beverageOfferSum, double savings,
                      List<Product> beverageOfferList, List<Product> extraOfferList);
}
