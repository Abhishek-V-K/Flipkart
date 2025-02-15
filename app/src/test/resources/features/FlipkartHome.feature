Feature: Flipkart Home Page Testing

  Scenario: Search for a product
    Given I open the Flipkart homepage
    When I search for "iPhone 14"
    Then the Flipkart logo should be visible
