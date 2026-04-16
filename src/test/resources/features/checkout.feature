Feature: Checkout Flow

Background:
  Given user has already logged in to application
    |username|password|
    |standard_user|secret_sauce|
  And user is on Home page
  And user adds "Sauce Labs Backpack" to cart
  And user navigates to cart
  And cart should contain "Sauce Labs Backpack"

@E2E
Scenario: Checkout with single item
  When user clicks checkout
  And user enters checkout information
    |firstName|lastName|postalCode|
    |Jane     |Smith   |90210     |
  And user continues checkout
  Then the overview should contain the item "Sauce Labs Backpack"
  And the overview total should be displayed
  When user finishes checkout
  Then checkout should be completed

@Regression
Scenario: Checkout cancels back to cart
  When user clicks checkout
  And user enters checkout information
    |firstName|lastName|postalCode|
    |Alice    |Brown   |12345     |
  And user clicks cancel on checkout
  Then user should be back on cart page
