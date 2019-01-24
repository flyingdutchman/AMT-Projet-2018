Feature: Badges Creation

  Background:
    Given there is a Badges server
    And i have a payload of three apps, two being from the same owner
    And i populate the server with them and get their respective Apikeys

  Scenario: Create a new badge
    Given i have a badge payload
    When i POST it to the /badges endpoint
    Then i receive a 201 status code from the /badges endpoint
    And the newly created badge

  Scenario: It is not possible to create two badges with the same name
    Given i have a badge payload
    When i POST it to the /badges endpoint
    And i POST it to the /badges endpoint
    Then i receive a 409 status code from the /badges endpoint
    
  Scenario: it is not possible to create a badge with false syntax
    Given i have an incorrect badge payload
    When i POST it to the /badges endpoint
    Then i receive a 400 status code from the /badges endpoint

  Scenario: You need to give a valid apiKey to use the badge api
    Given i have a badge payload
    When i POST it to the /badges endpoint
    Then i receive a 401 status code from the /events endpoint
