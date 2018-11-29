Feature: Creation of badges

  Background:
    Given there is a Badges server

  Scenario: create a badge
    Given I have a badge payload
    When I POST it to the /badges endpoint
    Then I receive a 201 status code