@products 
Feature: Products

    View and purchase products inventory

    Scenario: Background - Sign in with valid credentials
        Given the user navigates to https://www.saucedemo.com/
        When he enters username standard_user and password secret_sauce
        And he succesfully signs in
        Then the products inventory should be displayed

    Scenario: 1 (Happy Path) - The user buys some products
        Given the user adds some items to the shopping cart
        When he proceeds to checkout
        And he completes the billing information
        Then he should see the purchase overview and be able to complete the payment
        And a thank-you message should be displayed, allowing the user to continue shopping
    
    Scenario Outline: 2 (Positive) - The user select sorting options for the inventory
        Given The inventory is sort by name from A to Z by default
        When the user selects to sort products by <sortBy>
        Then the products inventory should be reordered
    
        Examples:
        | sortBy              |
        | Name (Z to A)       |
        | Name (A to Z)       |
        | Price (low to high) |
        | Price (high to low) |

    Scenario: 3 (Negative) - The user cancels a purchase procedure
        Given the user adds some items to the shopping cart
        When he proceeds to checkout
        And he completes the billing information
        Then he should be able to cancel the purchase order on the overview page
        And the user should be redirected to the products inventory page
        And the shopping cart should retain the added items
