package tests;

// import java.io.IOException;
// import java.time.Duration;
// import java.util.*;
// import org.openqa.selenium.By;
// import org.openqa.selenium.Keys;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.interactions.Actions;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;
// import org.testng.Assert;
// import org.testng.annotations.AfterSuite;
// import org.testng.annotations.BeforeSuite;
// import org.testng.annotations.Test;

// public class FlipkartTest {

//     protected WebDriver driver;
//     private FlipkartHomePage basePage; // Declare a BasePage object

//     @BeforeSuite
//     public void setUp() throws InterruptedException {
//         // Initialize the WebDriver using the Utils class
//         driver = Utils.initializeDriver();
//         String url = Utils.getProperty("url");
//         driver.get(url); // Use the URL from config.properties
//         Thread.sleep(2000);
//         // Initialize BasePage
//         basePage = new FlipkartHomePage(driver);
//         System.out.println("Browser setup is done SUCCESSFULLY");
//     }

//     @Test
//     public void clickOnGrocery() {
//         // Find the element by XPath and click it using the BasePage method
//         WebElement element = driver.findElement(By.xpath("(//*[@class='YBLJE4'])[1]"));
//         basePage.click(element); // Use the click() method from BasePage
//     }

//     @Test
//     public void testEnterPincode() throws InterruptedException {

//         try {
//             // Wait for the input field to become visible
//             WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//             WebElement pincodeField = wait
//                     .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='pincode']")));
//             List<String> pincode = Utils.getColumnValues("Pincodes", "Pincode");
//             for (int i = 0; i < pincode.size(); i++) {
//                 pincodeField.sendKeys(pincode.get(i));
//                 pincodeField.sendKeys(Keys.ENTER);
//                 Thread.sleep(2000);
//             }

//         } catch (Exception e) {
//             e.printStackTrace();
//         }

//     }

//     @Test
//     public void testGetTextAndClick() throws IOException, InterruptedException {
//         // XPath to locate the elements
//         String xpath = "//*[@id=\"container\"]/div/div[2]/div/div/div/a/div[2]/div/div";

//         // Find elements using the XPath
//         List<WebElement> elements = driver.findElements(By.xpath(xpath));

//         // List to store the text of each element
//         List<String> itemTexts = new ArrayList<>();

//         // Extract text from each element and add to the list
//         for (WebElement element : elements) {
//             String text = element.getText();
//             itemTexts.add(text);
//         }

//         // Get the desired items from the Excel file
//         List<String> desiredItems = Utils.getColumnValues("Items", "Item");

//         // Create an Actions instance
//         Actions actions = new Actions(driver);

//         // Iterate through the desired items and hover if present
//         for (String desiredItem : desiredItems) {
//             boolean itemFound = false;

//             for (int i = 0; i < elements.size(); i++) {
//                 if (itemTexts.get(i).contains(desiredItem)) {
//                     itemFound = true;

//                     // Perform mouse hover action
//                     actions.moveToElement(elements.get(i)).perform();

//                     break;
//                 }
//             }

//             // Assert that the desired item was found
//             Assert.assertTrue(itemFound, "Desired item '" + desiredItem + "' not found in the list.");
//         }
//     }

//     @AfterSuite
//     public void tearDown() {
//         // Quit the WebDriver using the Utils class
//         Utils.quitDriver(driver);
//         System.out.println("Browser closed SUCCESSFULLY");
//     }

// WebDriver driver;

// @BeforeClass
// public void setUp() {
// driver = Utils.initializeDriver();

// driver.manage().window().maximize();

// // Load the Excel file

// }

// @Test
// public void loginTest() throws IOException {
// String pincode = Utils.ExcelUtil();
// System.out.println(pincode);
// }

// @AfterClass
// public void tearDown() {

// driver.quit();
// }

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.FlipkartHomePage;
import utils.DriverUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.testng.Assert.assertTrue;

public class FlipkartHomeTest {

    @BeforeClass
    public static void setUp() {
        DriverUtils.getDriver(); // Initialize WebDriver before tests
        Allure.step("Driver Initialized");
    }

    @Test(dataProvider = "productProvider", dataProviderClass = DP.class)
    public void testFlipkartWithDirectData(String productName) {
        FlipkartHomePage homePage = new FlipkartHomePage();

        homePage.openHomePage();
        Allure.step("Navigated to Flipkart Home Page");

        // Test Actions
        searchForProduct(productName);
        homePage.navigateToElectronics();

        // Assertion to Verify Logo
        assertTrue(homePage.isLogoVisible(), "Flipkart logo should be visible");
        Allure.step("Verified Flipkart Logo visibility");
    }

    @Test(dataProvider = "excelProductProvider", dataProviderClass = DP.class)
    public void testFlipkartWithExcelData(String productName) {
        FlipkartHomePage homePage = new FlipkartHomePage();

        homePage.openHomePage();
        Allure.step("Navigated to Flipkart Home Page");

        // Test Actions
        searchForProduct(productName);
        homePage.navigateToElectronics();

        // Assertion to Verify Logo
        assertTrue(homePage.isLogoVisible(), "Flipkart logo should be visible");
        Allure.step("Verified Flipkart Logo visibility");
    }

    @Step("Search for product: {0}")
    public void searchForProduct(String productName) {
        FlipkartHomePage homePage = new FlipkartHomePage();
        homePage.searchForProduct(productName);
        Allure.step("Searched for product: " + productName);
    }

    @AfterMethod
    public void captureScreenshotAfterTest(ITestResult result) {
        byte[] screenshot = captureScreenshot();
        if (screenshot != null) {
            Allure.addAttachment("Screenshot for Test: " + result.getName(), "image/png", new String(screenshot));
        }
        Allure.step("Captured screenshot for test: " + result.getName());
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] captureScreenshot() {
        try {
            File screenshot = ((TakesScreenshot) DriverUtils.getDriver()).getScreenshotAs(OutputType.FILE);
            return Files.readAllBytes(screenshot.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @AfterClass
    public static void tearDown() {
        DriverUtils.quitDriver();
        Allure.step("Driver Quit");
    }
}
