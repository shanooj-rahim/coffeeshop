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

• If a customer orders a beverage and a snack, one of the extra's is free.

## Steps to run the project
```python
Download the repository from github
Import the project to any IDE of your choice
```

## Execute the below command to run the tests

```bash
mvn test
```

## Input

```python
list of products the shopper wants to purchase 

large coffee with extra milk, 
smallcoffee with special roast, 
bacon roll, 
orange juice
```

## Output

```python

=================================================
            CHARLENES COFFEE CORNER
                2020/11/24 23:20
=================================================
Customer No : 123546
Item             Type      Quantity      Price
=================================================
ORANGE_JUICE    BEVERAGE     13          51.35
EXTRA_MILK      EXTRA         1           0.30
ROAST_COFFEE    EXTRA         1           0.90
BACON_ROLL      SNACK         2           9.00
COFFEE_SMALL    BEVERAGE      1           2.50
COFFEE_LARGE    BEVERAGE      1           3.50
ORANGE_JUICE    BEVERAGE      3         -11.85
=================================================
Total=                                   55.70
Total Items=                              22
===============YOU HAVE SAVED====================
ORANGE_JUICE    BEVERAGE      3         -11.85
EXTRA_MILK      EXTRA         1          -0.30
=================================================
Total Savings=                            12.15
==============THANK YOU AND VISIT AGAIN!=========
```