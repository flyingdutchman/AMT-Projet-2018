Feature: Rule Retrieving

  There is a UserA and a UserB. The first one is the owner of the
  AppA and AppB and the other user is owner of the AppC. Therefore
  there are 3 ApiKeys, one for each app (respectively KeyA, KeyB,
  KeyC)

  Background:
    Given there is a Rules server
    And Rules: i have a payload of three apps, two being from the same owner
    And Rules: i populate the server with them and get their respective Apikeys

  Scenario: Get all rules
    Given there are 3 rules in the repository owned by AppA,AppA,AppB
    When i GET the list of rules owned by AppA on the /rules endpoint
    Then i receive a 200 status code from the /rules endpoint
    And get a list of rules from the repository of size 2

  Scenario: Getting all rules with a wrong ApiKey returns 401
    Given there are 2 rules in the repository owned by AppA
    When i GET the list of rules owned by FakeApp on the /rules endpoint
    Then i receive a 401 status code from the /rules endpoint

  Scenario: Check that a created rule is in the GET all rules
    Given i have a rule payload
    When i POST it to the /rules endpoint with KeyA
    And i GET the list of rules owned by AppA on the /rules endpoint
    Then i see my rule in the list

  Scenario: Get one rule
    Given i have a rule payload
    And i POST it to the /rules endpoint with KeyA
    When i GET lasts step rule on the /rules/id endpoint with KeyA
    Then i receive a 200 status code from the /rules endpoint
    And the rule corresponding to the given id

  Scenario: You can't get a rule that isn't yours
    Given i have a rule payload
    And i POST it to the /rules endpoint with KeyA
    When i GET lasts step rule on the /rules/id endpoint with KeyB
    Then i receive a 403 status code from the /rules endpoint

  Scenario: Asking for an unknown rule id returns 404
    Given i have a rule payload
    And i POST it to the /rules endpoint with KeyA
    When i GET the /rules/id with an unknown id with keyA
    Then i receive a 404 status code from the /rules endpoint

  Scenario: Getting one rule with a wrong ApiKey returns 401
    Given i have a rule payload
    And i POST it to the /rules endpoint with KeyA
    When i GET lasts step rule on the /rules/id endpoint with FakeKey
    Then i receive a 401 status code from the /rules endpoint
