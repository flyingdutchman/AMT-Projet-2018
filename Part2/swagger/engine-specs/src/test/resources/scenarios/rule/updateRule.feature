Feature: Rules Update

  There is a UserA and a UserB. The first one is the owner of the
  AppA and AppB and the other user is owner of the AppC. Therefore
  there are 3 ApiKeys, one for each app (respectively KeyA, KeyB,
  KeyC)

  Background:
    Given there is a Rules server
    And Rules: i have a payload of three apps, two being from the same owner
    And Rules: i populate the server with them and get their respective Apikeys

  Scenario: When I PUT a new rule to an existing rule ID, it updates it
    Given i have a rule payload
    And there is a rule in the repository owned by AppA
    When i PUT the payload into the lasts step /rules/id endpoint with KeyA
    And i GET lasts step rule on the /rules/id endpoint with KeyA
    Then i receive a 200 status code from the /rules endpoint
    And the rule corresponding to the given id

  Scenario: You cannot PUT a new rule to a non existing ID
    Given i have a rule payload
    When i PUT the payload to a unknown /rules/id endpoint with KeyA
    Then i receive a 404 status code from the /rules endpoint

  Scenario: You can't PUT a rule which isn't yours
    Given i have a rule payload
    And there is a rule in the repository owned by AppA
    When i PUT the payload into the lasts step /rules/id endpoint with KeyB
    Then i receive a 403 status code from the /rules endpoint

  Scenario: You must PUT a correctly formatted rule
    Given i have an incorrect rule payload
    And there is a rule in the repository owned by AppA
    When i PUT the payload into the lasts step /rules/id endpoint with KeyA
    Then i receive a 400 status code from the /rules endpoint
