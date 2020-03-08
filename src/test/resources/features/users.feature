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

  Scenario: Getting a specific user
    Given the client has been logged in
    And the remote data source delivers following user for id '123'
      | ID  | Username | Email                | Name     |
      | 123 | johnair  | john.air@fakemail.yu | John Air |
    When I ask for a user with id '123'
    Then I should get the following user
      | ID  | Username | Email                | Name     |
      | 123 | johnair  | john.air@fakemail.yu | John Air |
