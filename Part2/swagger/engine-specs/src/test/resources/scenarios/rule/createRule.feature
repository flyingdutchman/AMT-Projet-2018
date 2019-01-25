Feature: Rules Creation

  There is a UserA and a UserB. The first one is the owner of the
  AppA and AppB and the other user is owner of the AppC. Therefore
  there are 3 ApiKeys, one for each app (respectively KeyA, KeyB,
  KeyC)

  Background:
    Given there is a Rules server
    And Rules: i have a payload of three apps, two being from the same owner
    And Rules: i populate the server with them and get their respective Apikeys

  Scenario: Create a new rule
    Given i have a rule payload
    When i POST it to the /rules endpoint with KeyA
    Then i receive a 201 status code from the /rules endpoint
    And the newly created rule

  Scenario: It is possible to create two rule with the same name in different apps
    Given i have a rule payload
    When i POST it to the /rules endpoint with KeyA
    And i POST it to the /rules endpoint with KeyB
    Then i receive a 201 status code from the /rules endpoint
    
  Scenario: it is not possible to create a rule with false syntax
    Given i have an incorrect rule payload
    When i POST it to the /rules endpoint with KeyA
    Then i receive a 400 status code from the /rules endpoint

  Scenario: You need to give a valid apiKey to use the rule api
    Given i have a rule payload
    When i POST it to the /rules endpoint with FakeKey
    Then i receive a 401 status code from the /rules endpoint