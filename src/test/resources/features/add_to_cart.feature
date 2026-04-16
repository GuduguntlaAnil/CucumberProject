Feature: Add to Cart

Background:
  Given user has already logged in to application
    |username|password|
    |standard_user|secret_sauce|
  And user is on Home page
  # Ensure a baseline item exists in cart for scenarios
  And user adds "Sauce Labs Backpack" to cart
  And user navigates to cart
  And cart should contain "Sauce Labs Backpack"

@Smoke
Scenario: Verify single product already in cart
  Then cart badge should show 1
  When user navigates to cart
  Then cart should contain "Sauce Labs Backpack"

@Regression
Scenario: Add multiple products to cart and verify
  When user navigates to cart
  And user adds "Sauce Labs Bike Light" to cart
  Then cart badge should show 2
  When user navigates to cart
  Then cart should contain "Sauce Labs Backpack"
  And cart should contain "Sauce Labs Bike Light"

@E2E
Scenario: Complete checkout flow from cart
  When user clicks checkout
  And user enters checkout information
    |firstName|lastName|postalCode|
    |John     |Doe     |12345     |
  And user continues checkout
  And user finishes checkout
  Then checkout should be completed