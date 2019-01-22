Feature: PointScale Management

  Background:
    Given there is a PointScale server

  Scenario: Create a new pointScale
    Given I have a pointScale payload
    When I POST it to the /pointScales endpoint
    Then I receive a 201 status code from the /pointScales endpoint
    And The newly created pointScale

  Scenario: It is not possible to create two pointScales with the same name
    Given I have a pointScale payload
    When I POST it to the /pointScales endpoint
    And I POST it to the /pointScales endpoint
    Then I receive a 409 status code from the /pointScales endpoint

  Scenario: Get all pointScales
    Given There is at least one pointScale in the repository
    When I ask for a list of registered pointScales with a GET on the /pointScales endpoint
    Then I receive a 200 status code from the /pointScales endpoint
    And Get a list of pointScales from the repository

  Scenario: Check that the pointScale has been registered
    Given I have a pointScale payload
    When I POST it to the /pointScales endpoint
    And I ask for a list of registered pointScales with a GET on the /pointScales endpoint
    Then I see my pointScale in the list

  Scenario: Get one pointScale
    Given I have a pointScale payload
    And I POST it to the /pointScales endpoint
    When I GET the /pointScales/id endpoint
    Then I receive a 200 status code from the /pointScales endpoint
    And The pointScale corresponding to the given id

  Scenario: It is not possible to create a pointScale with false syntax
    Given I have an incorrect pointScale payload
    When I POST it to the /pointScales endpoint
    Then I receive a 400 status code from the /pointScales endpoint



