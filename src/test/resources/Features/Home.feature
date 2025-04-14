Feature: Home Page Feature

Background:
Given user has already logged in to application
|username|password|
|standard_user|secret_sauce|

@home
Scenario: Home page Logout button
Given user is on Home page
When user gets the title of the page
Then page title should be "Swag Labs"
When user clicks on menu
Then "Logout" should be visible to user


@home
Scenario: Home page product filters count
Given user is on Home page
Then user gets filter section
|Name (A to Z)|
|Name (Z to A)|
|Price (low to high)|
|Price (high to low)|
And filter section count should be 4