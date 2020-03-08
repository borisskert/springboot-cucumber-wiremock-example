Feature: User functionalities

  Scenario: Getting no users
    Given the client has been logged in
    And the remote data source delivers no users
    When I ask for all users
    Then should return no users

  Scenario: Getting users
    Given the client has been logged in
    And the remote data source delivers following users
      | ID | Username   | Email                      | Name           |
      | 1  | smitj      | john.smith@fakemail.com    | John Smith     |
      | 2  | mustermann | max.mustermann@fakemail.de | Max Mustermann |
    When I ask for all users
    Then I should get following users
      | ID | Username   | Email                      | Name           |
      | 1  | smitj      | john.smith@fakemail.com    | John Smith     |
      | 2  | mustermann | max.mustermann@fakemail.de | Max Mustermann |
