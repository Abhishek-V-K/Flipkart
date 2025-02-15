package pages;

// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.support.PageFactory;

// public class FlipkartHomePage {
//   protected WebDriver driver;

//   public FlipkartHomePage(WebDriver driver) {
//     this.driver = driver;
//     PageFactory.initElements(driver, this);
//   }

//   protected void click(WebElement element) {
//     element.click();
//   }

//   protected void type(WebElement element, String text) {
//     element.sendKeys(text);
//   }

//   protected String getText(WebElement element) {
//     return element.getText();
//   }
// }

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.DriverUtils;

public class FlipkartHomePage {
    private WebDriver driver;

    // Constructor to initialize PageFactory
    public FlipkartHomePage() {
        this.driver = DriverUtils.getDriver();
        PageFactory.initElements(driver, this);
    }

    // 5 Elements Located Using XPath
    @FindBy(xpath = "//input[@name='q']") // Search Box
    private WebElement searchBox;

    @FindBy(xpath = "//button[contains(text(),'Login')]") // Login Button
    private WebElement loginButton;

    @FindBy(xpath = "//span[contains(text(),'Electronics')]") // Electronics Tab
    private WebElement electronicsTab;

    @FindBy(xpath = "//a[contains(@href,'offers')]") // Offers Link
    private WebElement offersLink;

    @FindBy(xpath = "//img[@alt='Flipkart']") // Flipkart Logo
    private WebElement flipkartLogo;

    // Page Actions
    public void openHomePage() {
        driver.get("https://www.flipkart.com/");
    }

    public void searchForProduct(String productName) {
        searchBox.sendKeys(productName);
        searchBox.submit();
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void navigateToElectronics() {
        electronicsTab.click();
    }

    public void clickOffers() {
        offersLink.click();
    }

    public boolean isLogoVisible() {
        return flipkartLogo.isDisplayed();
    }
}
