Feature: Badges Management

  Background:
    Given there is a Badges server

  Scenario: Create a badge
    Given I have a badge payload
    When I POST it to the /badges endpoint
    Then I receive a 201 status code

  Scenario: Get all badges
    Given There is a badge in the repository
    When I GET the /badges endpoint
    Then I receive a 200 status code
    And Get a list of badges from the repository
    
  Scenario: Get one badge
    Given There is a badge in the repository
    When I GET the /badge/{id} endpoint
    Then I receive a 200 status code
    And The badge