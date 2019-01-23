Feature: Badges Management

  Background:
    Given there is a Badges server

  Scenario: Create a new badge
    Given I have a badge payload
    And An API key
    When I POST it to the /badges endpoint
    Then I receive a 201 status code from the /badges endpoint
    And The newly created badge

  Scenario: It is not possible to create two badges with the same name
    Given I have a badge payload
    And An API key
    When I POST it to the /badges endpoint
    And I POST it to the /badges endpoint
    Then I receive a 409 status code from the /badges endpoint

  Scenario: Get all badges
    Given There are two badges in the repositories
    And Having the same API keys
    When I ask for a list of registered badges with a GET on the /badges endpoint
    Then I receive a 200 status code from the /badges endpoint
    And Get a list of badges from the repository

  Scenario: Check that the badge has been registered
    Given I have a badge payload
    And An API key
    When I POST it to the /badges endpoint
    And I ask for a list of registered badges with a GET on the /badges endpoint
    Then I see my badge in the list

  Scenario: Get one badge
    Given I have a badge payload
    And An API key
    And I POST it to the /badges endpoint
    When I GET the /badges/id endpoint
    Then I receive a 200 status code from the /badges endpoint
    And The badge corresponding to the given id
    
  Scenario: It is not possible to create a badge with false syntax
    Given I have an incorrect badge payload
    And An API key
    When I POST it to the /badges endpoint
    Then I receive a 400 status code from the /badges endpoint

  Scenario: You need to give a valid apiKey to use the badge api
    Given I have a badge payload
    When I POST it to the /badges endpoint
    Then I receive a 401 status code from the /events endpoint
