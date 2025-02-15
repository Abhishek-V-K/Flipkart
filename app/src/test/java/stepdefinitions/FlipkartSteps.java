package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import pages.FlipkartHomePage;
import utils.DriverUtils;
import static org.testng.Assert.assertTrue;

public class FlipkartSteps {
  FlipkartHomePage homePage = new FlipkartHomePage();

  @Given("I open the Flipkart homepage")
  public void i_open_the_flipkart_homepage() {
    homePage.openHomePage();
  }

  @When("I search for {string}")
  public void i_search_for(String productName) {
    homePage.searchForProduct(productName);
  }

  @Then("the Flipkart logo should be visible")
  public void the_flipkart_logo_should_be_visible() {
    assertTrue(homePage.isLogoVisible(), "Flipkart logo should be visible");
    DriverUtils.quitDriver();
  }
}
