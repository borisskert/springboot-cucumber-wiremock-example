Feature: Product functionalities

  Scenario: Getting no products
#    Given the application is running
    Given the remote data source delivers no products
    When I ask for all products
    Then should return no products

  Scenario: Getting products
    Given the remote data source delivers following products
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
