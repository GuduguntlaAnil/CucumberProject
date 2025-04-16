@Login
Feature: Login page feature

@Smoke
Scenario: Login page title
Given user is on login page
When user gets the title of the page
Then page title should be "Swag Labs"

@Skip
Scenario: Accepted username text
Given user is on login page
Then Accepted UserNames Text should be displayed

@Regression @Skip
Scenario: Login with correct credentials
Given user is on login page
When user enters username "standard_user1"
And user enters password "secret_sauce"
And user clicks on Login button
Then page url contains "/inventory.html"
