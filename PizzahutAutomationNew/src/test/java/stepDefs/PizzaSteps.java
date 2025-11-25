package stepDefs;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.en.*;

import java.time.Duration;
import java.util.NoSuchElementException;

public class PizzaSteps {

    WebDriver driver;
    WebDriverWait wait;
    String selectedPizzaPrice;

    public PizzaSteps() {
        driver = Hooks.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // Launch application
    @Given("I have launched the application")
    public void i_have_launched_the_application() {
        driver.get("https://www.pizzahut.co.in/");
        driver.manage().window().maximize();
    }

    // Enter location
    @When("I enter the location as {string}")
    public void i_enter_the_location_as(String location) {
        WebElement locationBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[placeholder='Enter your location for delivery']")));
        locationBox.sendKeys(location);
    }

    // Select first suggestion
    @When("I select the {string}")
    public void i_select_the_first_suggestion(String suggestion) {
        WebElement firstSuggestion = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[normalize-space()='Chennai International Airport (MAA)']")));
        firstSuggestion.click();
    }

    // Select second suggestion
    @When("I click the second sugesstion {string}")
    public void i_click_the_second_sugesstion(String secondSuggestion) {
        WebElement secondOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//p[contains(text(),'No-15&16, Ground floor, Uptown FEC, Chennai, Tamil')]")));
        secondOption.click();
    }


    // Verify Deals page URL
    @Then("I should land on the Deals page with page url {string}")
    public void i_should_land_on_deals_page(String expectedUrl) {
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
    }

    // Select tab (Pizzas)
    @When("I select the tab as {string}")
    public void i_select_the_tab_as(String tabName) {
        By pizzasTab = By.xpath("//a[contains(@class,'capitalize lg:border-r')]//span[contains(text(),'" + tabName + "')]");
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(pizzasTab));
        tab.click();
    }

    // Helper method to scroll to pizza card
    public WebElement findPizzaCard(String pizzaName) {
        WebElement pizzaCard;
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 0; i < 10; i++) {
            try {
                pizzaCard = driver.findElement(By.xpath("//img[@title='" + pizzaName + "']/ancestor::div[contains(@class,'product')]"));
                if (pizzaCard.isDisplayed()) return pizzaCard;
            } catch (NoSuchElementException e) {
                js.executeScript("window.scrollBy(0,300)");
            }

            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }

        throw new NoSuchElementException("Pizza " + pizzaName + " not found on the page");
    }

    // Add pizza to basket
 // Add pizza to basket
 // Add pizza to basket
    @When("I add {string} to the basket")
    public void i_add_item_to_basket(String pizzaName) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Step 1: Find the pizza card
            WebElement pizzaCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//img[@title='" + pizzaName + "']/ancestor::div[contains(@class,'product')]")
            ));
            
            // Step 2: Scroll the element into view (CORRECTED)
            js.executeScript("arguments[0].scrollIntoView(true);", pizzaCard);
            Thread.sleep(1000); // Wait for scroll animation to complete
            
            // Step 3: Find the Add button within the pizza card
            WebElement addButton = pizzaCard.findElement(
                    By.xpath(".//button[contains(@data-synth,'one-tap')]")
            );
            
            // Step 4: Wait for button to be clickable
            wait.until(ExpectedConditions.elementToBeClickable(addButton));
            
            // Step 5: Use JavaScript click to bypass element interception
            js.executeScript("arguments[0].click();", addButton);
            
            System.out.println("✓ " + pizzaName + " added to basket successfully");
            Thread.sleep(500); // Wait for item to be added
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while adding " + pizzaName + " to basket", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add " + pizzaName + " to basket: " + e.getMessage(), e);
        }
    }
    // Verify pizza in cart
    @Then("I should see the pizza {string} is added to the cart")
    public void i_should_see_pizza_added(String pizzaName) {
        WebElement pizzaCartItem = findPizzaCard(pizzaName);  // reuse method to find in cart
        if (pizzaCartItem.isDisplayed()) {
            System.out.println(pizzaName + " is added to the cart.");
        } else {
            throw new AssertionError(pizzaName + " was not found in the cart!");
        }
    }
    // Click Checkout
    @When("I click on the Checkout button")
    public void i_click_checkout() {
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Checkout')]")));
        checkoutBtn.click();
    }

    // Verify checkout page URL
    @Then("I should be landed on the secured checkout page with url {string}")
    public void i_should_land_on_checkout_page(String expectedUrl) {
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
    }

    // Enter first name
    @When("I enter the First Name {string}")
    public void i_enter_first_name(String firstName) {
        WebElement name = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout__name")));
        name.sendKeys(firstName);
    }

    // Enter mobile
    @When("I enter the Mobile {string}")
    public void i_enter_mobile(String mobile) {
        WebElement phone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout__phone")));
        phone.sendKeys(mobile);
    }

    // Enter email
    @When("I enter the email {string}")
    public void i_enter_email(String email) {
        WebElement emailBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout__email")));
        emailBox.sendKeys(email);
    }
}
