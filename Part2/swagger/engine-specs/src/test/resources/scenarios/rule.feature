Feature: Rule Management

  Background:
    Given there is a Rule server

  Scenario: Create a new rule
    Given I have a rule payload
    When I POST it to the /rules endpoint
    Then I receive a 201 status code from the /rules endpoint
    And The newly created rule

  Scenario: Get all rules
    Given There is at least one rule in the repository
    When I ask for a list of registered rules with a GET on the /rules endpoint
    Then I receive a 200 status code from the /rules endpoint
    And Get a list of rules from the repository

  Scenario: Check that the rule has been registered
    Given I have a rule payload
    When I POST it to the /rules endpoint
    And I ask for a list of registered rules with a GET on the /rules endpoint
    Then I see my rule in the list

  Scenario: Get one rule
    Given I have a rule payload
    And I POST it to the /rules endpoint
    When I GET the /rules/id endpoint
    Then I receive a 200 status code from the /rules endpoint
    And The rule corresponding to the given id

  Scenario: It is not possible to create a rule with false syntax
    Given I have an incorrect rule payload
    When I POST it to the /rules endpoint
    Then I receive a 400 status code from the /rules endpoint



