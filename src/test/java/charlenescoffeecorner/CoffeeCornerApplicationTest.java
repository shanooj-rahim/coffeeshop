package charlenescoffeecorner;

import charlenescoffeecorner.component.ReceiptGenerator;
import charlenescoffeecorner.model.Customer;
import charlenescoffeecorner.model.Item;
import charlenescoffeecorner.service.CoffeeCornerServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static charlenescoffeecorner.model.Item.*;
import static org.junit.Assert.assertEquals;

public class CoffeeCornerApplicationTest {

    private CoffeeCornerApplication coffeeCornerApplication = null;

    @Before
    public void before() {
        coffeeCornerApplication = new CoffeeCornerApplication(new CoffeeCornerServiceImpl(new ReceiptGenerator()));
    }

    /*
     * COFFEE_LARGE = 3.50
     * Total should be 3.50
     * No savings
     * */

    @Test
    public void test_coffee_large_with_extra_milk() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(COFFEE_LARGE);

        Customer customer = new Customer.Builder().customerStampCard(123546L).order(itemList).build();
        double v = coffeeCornerApplication.processCustomerOrder(customer);
        assertEquals(3.50, v, 0.00);
    }

    /*
     * COFFEE_LARGE = 3.50
     * BACON_ROLL = 4.50
     * Total = 8.00
     * snack offer = EXTRA_MILK(0.30)
     * Should see 0.30 in the receipt as Total Savings
     * */
    @Test
    public void test_coffee_large_with_extra_milk_and_snack_with_offer() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(COFFEE_LARGE);
        itemList.add(BACON_ROLL);

        Customer customer = new Customer.Builder().customerStampCard(123546L).order(itemList).build();
        double v = coffeeCornerApplication.processCustomerOrder(customer);
        assertEquals(8.00, v, 0.00);
    }

    /*
     * COFFEE_LARGE = 3.50
     * COFFEE_MEDIUM = 3.00
     * ORANGE_JUICE = 3.95
     * COFFEE_SMALL = 2.50
     * total should be ( (3.50 * 3) + 3.00 + (3.95*2) + 2.50 ) - 2.50 (offer COFFEE_SMALL)
     * 23.9 - 2.50 = 21.4
     * Should see 2.50 in the receipt as Beverage discount and 2.50 in Total savings
     * */
    @Test
    public void test_coffee_large_with_only_beverage_offer() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(COFFEE_LARGE);
        itemList.add(COFFEE_MEDIUM);
        itemList.add(ORANGE_JUICE);
        itemList.add(COFFEE_LARGE);
        itemList.add(COFFEE_SMALL);
        itemList.add(COFFEE_LARGE);
        itemList.add(ORANGE_JUICE);

        Customer customer = new Customer.Builder().customerStampCard(123546L).order(itemList).build();
        double v = coffeeCornerApplication.processCustomerOrder(customer);
        assertEquals(21.40, v, 0.00);
    }

    /*
     * Total should be 31.45
     * There should be 2 offers applicable
     * one beverage offer and one extra offer
     * Total savings should be 4.25
     * */
    @Test
    public void test_beverage_offer_and_extra_offer() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(COFFEE_LARGE);
        itemList.add(EXTRA_MILK);
        itemList.add(COFFEE_SMALL);
        itemList.add(ROAST_COFFEE);
        itemList.add(BACON_ROLL);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);

        Customer customer = new Customer.Builder().customerStampCard(123546L).order(itemList).build();
        double v = coffeeCornerApplication.processCustomerOrder(customer);
        assertEquals(31.45, v, 0.00);
    }

    /*
     * Total should be 55.70
     * There should be 4 offers applicable
     * three beverage offer and one extra offer
     * Total savings should be 12.15( (3.95 * 3) + 0.30)
     * */
    @Test
    public void test_beverage_offer_and_extra_offer_4_offers() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(COFFEE_LARGE);
        itemList.add(EXTRA_MILK);
        itemList.add(COFFEE_SMALL);
        itemList.add(ROAST_COFFEE);
        itemList.add(BACON_ROLL);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(BACON_ROLL);

        Customer customer = new Customer.Builder().customerStampCard(123546L).order(itemList).build();
        double v = coffeeCornerApplication.processCustomerOrder(customer);
        assertEquals(55.70, v, 0.01);
    }
}