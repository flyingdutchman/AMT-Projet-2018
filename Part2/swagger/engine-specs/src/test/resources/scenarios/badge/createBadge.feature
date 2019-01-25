Feature: Badges Creation

  There is a UserA and a UserB. The first one is the owner of the
  AppA and AppB and the other user is owner of the AppC. Therefore
  there are 3 ApiKeys, one for each app (respectively KeyA, KeyB,
  KeyC)

  Background:
    Given there is a Badges server
    And i have a payload of three apps, two being from the same owner
    And i populate the server with them and get their respective Apikeys

  Scenario: Create a new badge
    Given i have a badge payload
    When i POST it to the /badges endpoint with KeyA
    Then i receive a 201 status code from the /badges endpoint
    And the newly created badge

  Scenario: It is not possible to create two badges with the same name for one app
    Given i have a badge payload
    When i POST it to the /badges endpoint with KeyA
    And i POST it to the /badges endpoint with KeyA
    Then i receive a 409 status code from the /badges endpoint
    But i have the location of the already existing badge

  Scenario: It is possible to create two badge with the same name in different apps
    Given i have a badge payload
    When i POST it to the /badges endpoint with KeyA
    And i POST it to the /badges endpoint with KeyB
    Then i receive a 201 status code from the /badges endpoint
    
  Scenario: it is not possible to create a badge with false syntax
    Given i have an incorrect badge payload
    When i POST it to the /badges endpoint with KeyA
    Then i receive a 400 status code from the /badges endpoint

  Scenario: You need to give a valid apiKey to use the badge api
    Given i have a badge payload
    When i POST it to the /badges endpoint with FakeKey
    Then i receive a 401 status code from the /badges endpoint