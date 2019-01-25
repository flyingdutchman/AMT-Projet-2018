Feature: PointScale Retrieving

  There is a UserA and a UserB. The first one is the owner of the
  AppA and AppB and the other user is owner of the AppC. Therefore
  there are 3 ApiKeys, one for each app (respectively KeyA, KeyB,
  KeyC)

  Background:
    Given there is a PointScale server
    And PointScale: i have a payload of three apps, two being from the same owner
    And PointScale: i populate the server with them and get their respective Apikeys

  Scenario: Get all pointScales
    Given there are 3 pointScales in the repository owned by AppA,AppA,AppB
    When i GET the list of pointScales owned by AppA on the /pointScales endpoint
    Then i receive a 200 status code from the /pointScales endpoint
    And get a list of pointScales from the repository of size 2

  Scenario: Getting all pointScales with a wrong ApiKey returns 401
    Given there are 2 pointScales in the repository owned by AppA
    When i GET the list of pointScales owned by FakeApp on the /pointScales endpoint
    Then i receive a 401 status code from the /pointScales endpoint

  Scenario: Check that a created pointScale is in the GET all pointScales
    Given i have a pointScale payload
    When i POST it to the /pointScales endpoint with KeyA
    And i GET the list of pointScales owned by AppA on the /pointScales endpoint
    Then i see my pointScale in the list

  Scenario: Get one pointScale
    Given i have a pointScale payload
    And i POST it to the /pointScales endpoint with KeyA
    When i GET lasts step pointScale on the /pointScales/id endpoint with KeyA
    Then i receive a 200 status code from the /pointScales endpoint
    And the pointScale corresponding to the given id

  Scenario: You can't get a pointScale that isn't yours
    Given i have a pointScale payload
    And i POST it to the /pointScales endpoint with KeyA
    When i GET lasts step pointScale on the /pointScales/id endpoint with KeyB
    Then i receive a 403 status code from the /pointScales endpoint

  Scenario: Asking for an unknown pointScale id returns 404
    Given i have a pointScale payload
    And i POST it to the /pointScales endpoint with KeyA
    When i GET the /pointScales/id with an unknown id with keyA
    Then i receive a 404 status code from the /pointScales endpoint

  Scenario: Getting one pointScale with a wrong ApiKey returns 401
    Given i have a pointScale payload
    And i POST it to the /pointScales endpoint with KeyA
    When i GET lasts step pointScale on the /pointScales/id endpoint with FakeKey
    Then i receive a 401 status code from the /pointScales endpoint
