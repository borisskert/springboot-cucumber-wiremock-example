Feature: Product functionalities

  Scenario: Getting no products
    Given the client has been logged in
    And the remote data source delivers no products
    When I ask for all products
    Then should return no products

  Scenario: Getting products
    Given the client has been logged in
    And the remote data source delivers following products
      | ID | Name  | Price |
      | 1  | Honey | 1.23  |
      | 2  | Bread | 2.34  |
      | 3  | Beer  | 3.45  |
    When I ask for all products
    Then I should get following products
      | ID | Name  | Price |
      | 1  | Honey | 1.23  |
      | 2  | Bread | 2.34  |
      | 3  | Beer  | 3.45  |

  Scenario: Create a product
    Given the client has been logged in
    And the remote data source should return the created product
      | ID | Name     | Price |
      | 4  | Potatoes | 4.56  |
    When I request a product creation
      | Name     | Price |
      | Potatoes | 4.56  |
    Then the remote data source should be requested to create the product
      | Name     | Price |
      | Potatoes | 4.56  |
    And I should get the created product ID '4'
