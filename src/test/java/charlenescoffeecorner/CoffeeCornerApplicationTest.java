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

    /**
     * No Offers applicable
     * Total =  3.50
     * Grant total = 3.50
     * Total Savings = 0.00
     */
    @Test
    public void test_only_coffee_no_offer() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(COFFEE_LARGE);

        Customer customer = new Customer.Builder().customerStampCard(123546L).items(itemList).build();
        double grantTotal = coffeeCornerApplication.processCustomerOrder(customer);
        assertEquals(3.50, grantTotal, 0.00);
    }

    /**
     * No Offers applicable
     * Total =  3.80
     * Grant total = 3.80
     * Total Savings = 0.00
     */
    @Test
    public void test_only_coffee_with_extra_no_offer() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(COFFEE_LARGE);
        itemList.add(EXTRA_MILK);

        Customer customer = new Customer.Builder().customerStampCard(123546L).items(itemList).build();
        double grantTotal = coffeeCornerApplication.processCustomerOrder(customer);
        assertEquals(3.80, grantTotal, 0.00);
    }

    /**
     * No Offers applicable
     * COFFEE_LARGE = 4.50
     * Total =  4.50
     * Grant total = 4.50
     * Total savings = 0.00
     */
    @Test
    public void test_only_snack_no_offer() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(BACON_ROLL);

        Customer customer = new Customer.Builder().customerStampCard(123546L).items(itemList).build();
        double grantTotal = coffeeCornerApplication.processCustomerOrder(customer);
        assertEquals(4.50, grantTotal, 0.00);
    }

    /**
     * BEVERAGE offer applicable
     * ORANGE_JUICE is free as a part of the offer
     * Total = 18.10
     * Grant Total = 14.15
     * Total savings = 3.95
     */
    @Test
    public void test_hot_cold_beverage_offer_should_have_one_beverage_offer() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(COFFEE_LARGE);
        itemList.add(EXTRA_MILK);
        itemList.add(ORANGE_JUICE);
        itemList.add(ROAST_COFFEE);
        itemList.add(COFFEE_SMALL);
        itemList.add(COFFEE_MEDIUM);
        itemList.add(ORANGE_JUICE); //This item eligible for BEVERAGE offer

        Customer customer = new Customer.Builder().customerStampCard(123546L).items(itemList).build();
        double grantTotal = coffeeCornerApplication.processCustomerOrder(customer);
        assertEquals(14.15, grantTotal, 0.01);
    }

    /**
     * EXTRA offer applicable
     * EXTRA_MILK is preselected as EXTRA offer
     * COFFEE_LARGE = 3.50
     * BACON_ROLL = 4.50
     * Total = 8.00
     * Grant Total = 8.00
     * Extra offer = EXTRA_MILK
     * Total Savings = 0.00
     */
    @Test
    public void test_hot_beverage_and_snack_should_have_extra_offer() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(COFFEE_LARGE);
        itemList.add(BACON_ROLL);

        Customer customer = new Customer.Builder().customerStampCard(123546L).items(itemList).build();
        double grantTotal = coffeeCornerApplication.processCustomerOrder(customer);
        assertEquals(8.00, grantTotal, 0.00);
    }

    /**
     * No Extra offer applicable since the customer ordered a cold beverage and a snack.
     * Total = 8.45
     * Grant Total = 8.45
     * Total savings - 0
     */
    @Test
    public void test_cold_beverage_and_snack_should_have_no_extra_offer() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(ORANGE_JUICE);
        itemList.add(BACON_ROLL);

        Customer customer = new Customer.Builder().customerStampCard(123546L).items(itemList).build();
        double grantTotal = coffeeCornerApplication.processCustomerOrder(customer);
        assertEquals(8.45, grantTotal, 0.00);
    }

    /**
     * 2 BEVERAGE offer applicable since the customer ordered 10 beverages.
     * Customer should receive 1 COFFEE_SMALL and 1 ORANGE_JUICE as part of the beverage offer
     * Total = 33.35
     * Grant Total = 26.90
     * Savings = 6.45
     */
    @Test
    public void test_beverage_offer_should_have_two_beverage_offer() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(COFFEE_LARGE);
        itemList.add(COFFEE_MEDIUM);
        itemList.add(ORANGE_JUICE);
        itemList.add(COFFEE_LARGE);
        itemList.add(COFFEE_SMALL); //This item eligible for BEVERAGE offer
        itemList.add(COFFEE_LARGE);
        itemList.add(ORANGE_JUICE);
        itemList.add(COFFEE_MEDIUM);
        itemList.add(COFFEE_SMALL);
        itemList.add(ORANGE_JUICE);//This item eligible for BEVERAGE offer

        Customer customer = new Customer.Builder().customerStampCard(123546L).items(itemList).build();
        double grantTotal = coffeeCornerApplication.processCustomerOrder(customer);
        assertEquals(26.90, grantTotal, 0.01);
    }

    /**
     * EXTRA offer applicable
     * EXTRA_MILK is preselected as EXTRA offer
     * Total = 12.25
     * Grant Total = 12.25
     * Extra offer = EXTRA_MILK
     * Total Savings = 0.00
     */
    @Test
    public void test_one_cold_one_hot_beverage_one_snack_should_return_one_extra_offer() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(COFFEE_LARGE);
        itemList.add(EXTRA_MILK);
        itemList.add(ORANGE_JUICE);
        itemList.add(BACON_ROLL);

        Customer customer = new Customer.Builder().customerStampCard(123546L).items(itemList).build();
        double grantTotal = coffeeCornerApplication.processCustomerOrder(customer);
        assertEquals(12.25, grantTotal, 0.00);
    }

    /**
     * BEVERAGE offer applicable
     * Total = 24.25
     * Grant Total = 20.30
     * Total Savings = 3.95
     */
    @Test
    public void test_five_cold_beverage_one_snack_should_return_one_beverage_offer() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);//This item eligible for BEVERAGE offer
        itemList.add(BACON_ROLL);

        Customer customer = new Customer.Builder().customerStampCard(123546L).items(itemList).build();
        double grantTotal = coffeeCornerApplication.processCustomerOrder(customer);
        assertEquals(20.30, grantTotal, 0.00);
    }

    /**
     * BEVERAGE and EXTRA offers applicable
     * Total = 27.25
     * Grant Total = 24.25
     * Total Savings = 3.00
     */
    @Test
    public void test_five_cold_beverage_one_hot_beverage_one_snack_should_return_two_offers() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(ORANGE_JUICE);
        itemList.add(COFFEE_MEDIUM);//This item eligible for BEVERAGE offer
        itemList.add(ORANGE_JUICE);
        itemList.add(BACON_ROLL);

        Customer customer = new Customer.Builder().customerStampCard(123546L).items(itemList).build();
        double grantTotal = coffeeCornerApplication.processCustomerOrder(customer);
        assertEquals(24.25, grantTotal, 0.00);
    }

    /**
     * Customer object is null. Should throw IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_customer_null_should_throw_exception() {
        coffeeCornerApplication.processCustomerOrder(null);
    }

    /**
     * Item list is null. Item list cannot be null.Should throw NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void test_customer_not_null_item_list_null_should_throw_exception() {
        Customer customer = new Customer.Builder().customerStampCard(123546L).items(null).build();
        coffeeCornerApplication.processCustomerOrder(customer);
    }

    /**
     * Item list is Empty. Item list cannot be empty.Should throw NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void test_customer_not_null_item_lis() {
        List<Item> itemList = new ArrayList<>();
        Customer customer = new Customer.Builder().customerStampCard(123546L).items(itemList).build();
        coffeeCornerApplication.processCustomerOrder(customer);
    }
}