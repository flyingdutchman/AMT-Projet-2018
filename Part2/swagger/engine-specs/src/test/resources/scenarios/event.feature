Feature: Event processing

  Background:
    Given there is a running api
    And Event: i have a payload of three apps, two being from the same owner
    And Event: i populate the server with them and get their respective Apikeys

  Scenario: Sending an event returns a positive feedback
    Given I have an event payload
    When I POST it to the /events endpoint
    Then I receive a 200 status code from the /events endpoint

  Scenario: The event triggers the concerned rules : Badges
    Given I have an event payload
    And A Badge online
    And A User online
    And A badge Rule online
    When I POST it to the /events endpoint
    Then I receive a 200 status code from the /events endpoint
    And The correct badge has been assigned

  Scenario: The event triggers the concerned rules : PointScale
    Given I have an event payload
    And A PointScale online
    And A User online
    And A pointScale Rule online
    When I POST it to the /events endpoint
    Then I receive a 200 status code from the /events endpoint
    And The correct pointScales were given

  Scenario: The event triggers the concerned rules, badges and pointscales
    Given I have an event payload
    And A PointScale online
    And A Badge online
    And A User online
    And A  complete Rule online
    When I POST it to the /events endpoint
    Then I receive a 200 status code from the /events endpoint
    And The correct pointScales were given
    And The correct badge has been assigned
