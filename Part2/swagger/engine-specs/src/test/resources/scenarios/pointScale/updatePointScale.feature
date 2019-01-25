Feature: Badges Update

  There is a UserA and a UserB. The first one is the owner of the
  AppA and AppB and the other user is owner of the AppC. Therefore
  there are 3 ApiKeys, one for each app (respectively KeyA, KeyB,
  KeyC)

  Background:
    Given there is a PointScale server
    And PointScale: i have a payload of three apps, two being from the same owner
    And PointScale: i populate the server with them and get their respective Apikeys

  Scenario: When I PUT a new pointScale to an existing pointScale ID, it updates it
    Given i have a pointScale payload
    And there is a pointScale in the repository owned by AppA
    When i PUT the payload into the lasts step /pointScales/id endpoint with KeyA
    And i GET lasts step pointScale on the /pointScales/id endpoint with KeyA
    Then i receive a 200 status code from the /pointScales endpoint
    And the pointScale corresponding to the given id

  Scenario: You cannot PUT a new pointScale to a non existing ID
    Given i have a pointScale payload
    When i PUT the payload to a unknown /pointScales/id endpoint with KeyA
    Then i receive a 404 status code from the /pointScales endpoint

  Scenario: You can't PUT a pointScale which isn't yours
    Given i have a pointScale payload
    And there is a pointScale in the repository owned by AppA
    When i PUT the payload into the lasts step /pointScales/id endpoint with KeyB
    Then i receive a 403 status code from the /pointScales endpoint

  Scenario: You must PUT a correctly formatted pointScale
    Given i have an incorrect pointScale payload
    And there is a pointScale in the repository owned by AppA
    When i PUT the payload into the lasts step /pointScales/id endpoint with KeyA
    Then i receive a 400 status code from the /pointScales endpoint

  Scenario: You cannot create duplicate names in one app with PUT
    Given i have a pointScale payload
    When i POST it to the /pointScales endpoint with KeyA
    When i PUT the payload into the lasts step /pointScales/id endpoint with KeyA
    Then i receive a 409 status code from the /pointScales endpoint
    But i have the location of the already existing pointScale