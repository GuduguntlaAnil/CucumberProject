Feature: Excel driven login

  Scenario: Login using a specific row from Excel
    Given user is on login page
    When user logs in using excel sheet "Sheet1" row 2
    Then page url contains "inventory"

  Scenario: Login using all rows from Excel
    Given user is on login page
    When user performs login for all rows in sheet "Sheet1"
    Then Accepted UserNames Text should be displayed

  Scenario Outline: Login using Examples table
    Given user is on login page
    When user enters username "<username>"
    And user enters password "<password>"
    And user clicks on Login button
    Then page url contains "inventory"

    Examples:
      | username        | password     |
      | standard_user   | secret_sauce |
      | locked_out_user | secret_sauce |
      | problem_user    | secret_sauce |