Feature: PointScale Creation

  There is a UserA and a UserB. The first one is the owner of the
  AppA and AppB and the other user is owner of the AppC. Therefore
  there are 3 ApiKeys, one for each app (respectively KeyA, KeyB,
  KeyC)

  Background:
    Given there is a PointScale server
    And PointScale: i have a payload of three apps, two being from the same owner
    And PointScale: i populate the server with them and get their respective Apikeys

  Scenario: Create a new PointScale
    Given i have a pointScale payload
    When i POST it to the /pointScales endpoint with KeyA
    Then i receive a 201 status code from the /pointScales endpoint
    And the newly created pointScale

  Scenario: It is not possible to create two pointScales with the same name for one app
    Given i have a pointScale payload
    When i POST it to the /pointScales endpoint with KeyA
    And i POST it to the /pointScales endpoint with KeyA
    Then i receive a 409 status code from the /pointScales endpoint
    But i have the location of the already existing pointScale

  Scenario: It is possible to create two pointScales with the same name in different apps
    Given i have a pointScale payload
    When i POST it to the /pointScales endpoint with KeyA
    And i POST it to the /pointScales endpoint with KeyB
    Then i receive a 201 status code from the /pointScales endpoint
    
  Scenario: it is not possible to create a pointScale with false syntax
    Given i have an incorrect pointScale payload
    When i POST it to the /pointScales endpoint with KeyA
    Then i receive a 400 status code from the /pointScales endpoint

  Scenario: You need to give a valid apiKey to use the pointScale api
    Given i have a pointScale payload
    When i POST it to the /pointScales endpoint with FakeKey
    Then i receive a 401 status code from the /pointScales endpoint