Feature: Event processing

  Background:
    Given there is a running api

  Scenario: Sending an event returns a positive feedback
    Given I have an event payload
    When I POST it to the /events endpoint
    Then I receive a 200 status code from the /events endpoint

  Scenario: The event triggers the concerned rules
    Given I have an event payload
    And A Badge online
    And A Rule online
    When I POST it to the /events endpoint
    Then I receive a 200 status code from the /events endpoint
    And The correct badge has been assigned
    And The correct pointScales were given