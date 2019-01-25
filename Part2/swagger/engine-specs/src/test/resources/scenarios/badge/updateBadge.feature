Feature: Badges Update

  There is a UserA and a UserB. The first one is the owner of the
  AppA and AppB and the other user is owner of the AppC. Therefore
  there are 3 ApiKeys, one for each app (respectively KeyA, KeyB,
  KeyC)

  Background:
    Given there is a Badges server
    And i have a payload of three apps, two being from the same owner
    And i populate the server with them and get their respective Apikeys

  Scenario: When I PUT a new badge to an existing badge ID, it updates it
    Given i have a badge payload
    And there is a badge in the repository owned by AppA
    When i PUT the payload into the lasts step /badges/id endpoint with KeyA
    And i GET lasts step badge on the /badges/id endpoint with KeyA
    Then i receive a 200 status code from the /badges endpoint
    And the badge corresponding to the given id

  Scenario: You cannot PUT a new badge to a non existing ID
    Given i have a badge payload
    When i PUT the payload to a unknown /badges/id endpoint with KeyA
    Then i receive a 404 status code from the /badges endpoint

  Scenario: You can't PUT a badge which isn't yours
    Given i have a badge payload
    And there is a badge in the repository owned by AppA
    When i PUT the payload into the lasts step /badges/id endpoint with KeyB
    Then i receive a 403 status code from the /badges endpoint

  Scenario: You must PUT a correctly formatted badge
    Given i have an incorrect badge payload
    And there is a badge in the repository owned by AppA
    When i PUT the payload into the lasts step /badges/id endpoint with KeyA
    Then i receive a 400 status code from the /badges endpoint

  Scenario: You cannot create duplicate names in one app with PUT
    Given i have a badge payload
    When i POST it to the /badges endpoint with KeyA
    When i PUT the payload into the lasts step /badges/id endpoint with KeyA
    Then i receive a 409 status code from the /badges endpoint
    But i have the location of the already existing badge