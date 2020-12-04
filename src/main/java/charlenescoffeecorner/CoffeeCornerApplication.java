package charlenescoffeecorner;

import charlenescoffeecorner.model.Customer;
import charlenescoffeecorner.service.CoffeeCornerService;

public class CoffeeCornerApplication {
    private CoffeeCornerService coffeeCornerService;

    public CoffeeCornerApplication(CoffeeCornerService coffeeCornerService) {
        this.coffeeCornerService = coffeeCornerService;
    }

    public Double processCustomerOrder(Customer customer) {
        return coffeeCornerService.processCustomerOrder(customer);
    }
}
