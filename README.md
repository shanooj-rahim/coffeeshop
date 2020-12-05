# Coding Assignment: Charlene's Coffee Corner - Details

Recently, Charlene decided to open her very own little coffee shop on a busy street corner.
Being the careful entrepreneur, she decided to start off with a limited offering, with the option to expand her
choice of products, as business goes.

Her Offering

• Coffee (small, medium, large) 2.50 CHF, 3.00 CHF, 3.50 CHF

• Bacon Roll 4.50 CHF

• Freshly squeezed orange juice (0.25l) 3.95 CHF

Extras:

• Extra milk 0.30 CHF

• Foamed milk 0.50 CHF

• Special roast coffee 0.90 CHF

Bonus Program

• Charlene's idea is to attract as many regular‘s as possible to have a steady turnaround.

• She decides to offer a customer stamp card, where every 5th beverage is for free.

• If a customer orders a beverage and a snack, one of the extra's is free.

## Pre-requisites to run the project
```python
• Java8
• Any IDE - IntelliJ IDEA, Eclipse or Netbeans
• Maven   - any version equal to or higher than 3.0
```

## Steps to run the project
#### 1. Download the repository from github
```python
https://github.com/shanooj-rahim/coffeeshop
```
#### 2. Clean and install the maven project
```python
mvn clean install
```
#### 3. Execute the below command to run the tests

```bash
mvn test
```

## Points to keep in mind

```python
• Beverages are classified to two categories
  HOT BEVERAGE and COLD BEVERAGE

  - If a customer orders a HOT BEVERAGE 
  (COFFEE_SMALL,COFFEE_MEDIUM,COFFEE_LARGE) and a snack 
  (BACON_ROLL), then customer will 
  receive a complimentary EXTRA offer from the coffee shop.

  - If a customer orders a COLD BEVERAGE (ORANGE_JUICE) 
  and a snack (BACON_ROLL), then customer will not receive a 
  complimentary EXTRA offer from the coffee shop.

  - For Ex: If customer order's 
    Input 1                   - COFFEE_SMALL and a BACON_ROLL
    Eligible for EXTRA Offer  - Yes
    Input 2                   - ORANGE_JUICE and a BACON_ROLL
    Eligible for EXTRA Offer  - No

  Reason: The EXTRA's (EXTRA_MILK,FOAMED_MILK,ROAST_COFFEE)
  can be mixed only with HOT BEVERAGES

• Customer will always receive EXTRA_MILK as the EXTRA offer 
  when he/she orders a HOT BEVERAGE and a SNACK. This is set as
  default for enabling automated testing and verifying the results.

• The price of the item received as a part of the EXTRA offer
  will be displayed as '0.00' in the receipt since it is free.

• It is assumed that every customer has a stamp card.

• To receive the BEVERAGE offer, customer have to buy
  5 or more than 5 HOT/COLD BEVERAGES. Every 5th beverage in
  the order will be free.
  For Ex:
    Input : COFFEE_LARGE
            COFFEE_MEDIUM
            ORANGE_JUICE
            COFFEE_LARGE
            COFFEE_SMALL //This item eligible for BEVERAGE offer
            COFFEE_LARGE
            ORANGE_JUICE
            COFFEE_MEDIUM
            COFFEE_SMALL
            ORANGE_JUICE//This item eligible for BEVERAGE offer

• The receipt is divided in to many sections

  Items       - This section shows all the items ordered by
                the customer with out applying any offers.
  Discount    - This section shows all the beverage offers
                the customer received.
                Note: This section will not be displayed in the
                      receipt if the customer did not receive
                      any beverage offer.
  Extra offer - This section shows the EXTRA offer item the customer
                received.
                Note: This section will not be displayed in the
                      receipt if the customer did not receive
                      any EXTRA offer.

• Total calculations in the receipt
  
  Total         -  This variable stores the total sum of the prices of
                   all the items the customer ordered before applying
                   the beverage offers.

  Grant total   -  This variable stores the total sum of the prices of
                   all the items the customer ordered after applying
                   the beverage offer.

  Total Savings -  This variable stores the total sum of the prices of
                   the beverages the customer received as part of the
                   BEVERAGE offer. 
```

## Input and output samples

```python
1) Inputs:
    - Bacon Roll
    - Coffee Large

Note : Eligible for EXTRA offer

Output:
=================================================
            CHARLENES COFFEE CORNER
                2020/12/5 23:57
-------------------------------------------------
Customer No : 123546
Item             Type      Quantity      Price
-------------------------------------------------
Bacon Roll      Snack         1           4.50
Coffee Large    Beverage      1           3.50

Total=                        2          8.00
=================================================
Grant Total=                             8.00
=================Extra Offer====================
Extra Milk      Extra         1           0.00
=================================================
Total Savings=                            0.00
============Thank you and visit again!===========

2) Inputs:
    - Bacon Roll
    - Orange Juice
    - Orange Juice
    - Orange Juice
    - Orange Juice
    - Orange Juice - This item is eligible for BEVERAGE offer

Note : Eligible for BEVERAGE offer

Output:
=================================================
            CHARLENES COFFEE CORNER
                2020/12/5 23:57
-------------------------------------------------
Customer No : 123546
Item             Type      Quantity      Price
-------------------------------------------------
Bacon Roll      Snack         1           4.50
Orange Juice    Beverage      5          19.75

Total=                        6          24.25
=================Discount========================
Orange Juice    Beverage      1          -3.95
=================================================
Grant Total=                             20.30
=================================================
Total Savings=                            3.95
============Thank you and visit again!===========

3) Inputs:
    - Bacon Roll
    - Coffee Medium
    - Orange Juice
    - Orange Juice
    - Orange Juice
    - Orange Juice - This item is eligible for BEVERAGE offer
    - Orange Juice

Note : Eligible for 1 BEVERAGE offer and EXTRA offer

Output:
=================================================
            CHARLENES COFFEE CORNER
                2020/12/5 23:57
-------------------------------------------------
Customer No : 123546
Item             Type      Quantity      Price
-------------------------------------------------
Bacon Roll      Snack         1           4.50
Coffee Medium   Beverage      1           3.00
Orange Juice    Beverage      5          19.75

Total=                        7          27.25
=================Discount========================
Coffee Medium   Beverage      1          -3.00
=================================================
Grant Total=                             24.25
=================Extra Offer====================
Extra Milk      Extra         1           0.00
=================================================
Total Savings=                            3.00
============Thank you and visit again!=========== 

```