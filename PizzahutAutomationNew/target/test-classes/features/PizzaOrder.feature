Feature: This feature will be used to automate PizzaHut Website
  Scenario: Place the Order
    Given I have launched the application
    When I enter the location as "Chennai" 
    And I select the "Chennai International Airport (MAA), Chennai - Trichy Highway, Tambaram, Meenambakkam, Chennai, Tamil Nadu, India" 
    And I click the second sugesstion "Chennai - Kathipara Guindy" 
    Then I should land on the Deals page with page url "https://www.pizzahut.co.in/order/deals/" 
    And I select the tab as "Pizzas" 
    And I add "Kadhai Paneer" to the basket 
    Then I should see the pizza "Kadhai Paneer" is added to the cart
    And I click on the Checkout button 
    Then I should be landed on the secured checkout page with url "https://www.pizzahut.co.in/order/checkout/"
    And I enter the First Name "Rajesh"
    And I enter the Mobile "99999999999" 
    And I enter the email "Rajesh@yopmail.com" 