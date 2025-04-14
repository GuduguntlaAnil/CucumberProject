Feature: Login page feature

Scenario: Login page title
Given user is on login page
When user gets the title of the page
Then page title should be "Swag Labs"

Scenario: Accepted username text
Given user is on login page
Then Accepted UserNames Text should be displayed

Scenario: Login with correct credentials
Given user is on login page
When user enters username "standard_user"
And user enters password "secret_sauce"
And user clicks on Login button
Then page url contains "/inventory.html"
