Feature: Badge Retrieving

  There is a UserA and a UserB. The first one is the owner of the
  AppA and AppB and the other user is owner of the AppC. Therefore
  there are 3 ApiKeys, one for each app (respectively KeyA, KeyB,
  KeyC)

  Background:
    Given there is a Badges server
    And i have a payload of three apps, two being from the same owner
    And i populate the server with them and get their respective Apikeys

  Scenario: Get all badges
    Given there are 3 badges in the repository owned by AppA,AppA,AppB
    When i GET the list of badges owned by AppA on the /badges endpoint
    Then i receive a 200 status code from the /badges endpoint
    And get a list of badges from the repository of size 2

  Scenario: Getting all badges with a wrong ApiKey returns 401
    Given there are 2 badges in the repository owned by AppA
    When i GET the list of badges owned by FakeApp on the /badges endpoint
    Then i receive a 401 status code from the /badges endpoint

  Scenario: Check that a created badge is in the GET all badges
    Given i have a badge payload
    When i POST it to the /badges endpoint with KeyA
    And i GET the list of badges owned by AppA on the /badges endpoint
    Then i see my badge in the list

  Scenario: Get one badge
    Given i have a badge payload
    And i POST it to the /badges endpoint with KeyA
    When i GET lasts step badge on the /badges/id endpoint with KeyA
    Then i receive a 200 status code from the /badges endpoint
    And the badge corresponding to the given id

  Scenario: You can't get a badge that isn't yours
    Given i have a badge payload
    And i POST it to the /badges endpoint with KeyA
    When i GET lasts step badge on the /badges/id endpoint with KeyB
    Then i receive a 403 status code from the /badges endpoint

  Scenario: Asking for an unknown badge id returns 404
    Given i have a badge payload
    And i POST it to the /badges endpoint with KeyA
    When i GET the /badges/id with an unknown id with keyA
    Then i receive a 404 status code from the /badges endpoint

  Scenario: Getting one badge with a wrong ApiKey returns 401
    Given i have a badge payload
    And i POST it to the /badges endpoint with KeyA
    When i GET lasts step badge on the /badges/id endpoint with FakeKey
    Then i receive a 401 status code from the /badges endpoint
