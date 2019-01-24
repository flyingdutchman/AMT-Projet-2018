Feature:

  Background:
    Given there is a Badges server
    And i have a payload of three apps, two being from the same owner
    And i populate the server with them and get their respective Apikeys

  Scenario: Get all badges
    Given there are two badges in the repositories
    When i ask for a list of registered badges with a GET on the /badges endpoint
    Then i receive a 200 status code from the /badges endpoint
    And get a list of badges from the repository

  Scenario: Check that the badge has been registered
    Given i have a badge payload
    When i POST it to the /badges endpoint
    And i ask for a list of registered badges with a GET on the /badges endpoint
    Then i see my badge in the list

  Scenario: Get one badge
    Given i have a badge payload
    And i POST it to the /badges endpoint
    When i GET the /badges/id endpoint
    Then i receive a 200 status code from the /badges endpoint
    And the badge corresponding to the given id