package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Utils {

  private static Properties properties;

  // Load the config.properties file
  static {
    try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties")) {
      properties = new Properties();
      properties.load(fileInputStream);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load config.properties file", e);
    }
  }

  // Method to get property values
  public static String getProperty(String key) {
    return properties.getProperty(key);
  }

  // Method to initialize the WebDriver
  public static WebDriver initializeDriver() {
    String browser = getProperty("browser");
    WebDriver driver;

    switch (browser.toLowerCase()) {
      case "chrome":
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        break;

      case "firefox":
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        break;

      case "edge":
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        break;

      default:
        throw new IllegalArgumentException("Unsupported browser: " + browser);
    }

    // Common browser setup
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.manage().window().maximize();
    return driver;
  }

  // Method to quit the WebDriver
  public static void quitDriver(WebDriver driver) {
    if (driver != null) {
      driver.quit();
    }
  }

  public static List<String> getColumnValues(String sheetName, String columnName) throws IOException {
    String filePath = "C:\\Users\\abhis\\Downloads\\Selenium_Gradle_Automation\\app\\src\\test\\resources\\TestData.xlsx";
    FileInputStream fileInputStream = null;
    XSSFWorkbook workbook = null;
    List<String> columnValues = new ArrayList<>();

    try {
      // Open the file
      fileInputStream = new FileInputStream(filePath);
      workbook = new XSSFWorkbook(fileInputStream);

      // Get the sheet by name
      XSSFSheet sheet = workbook.getSheet(sheetName);
      if (sheet == null) {
        throw new IllegalArgumentException("Sheet with name '" + sheetName + "' not found");
      }

      // Get the header row (assume it's the first row)
      XSSFRow headerRow = sheet.getRow(0);
      if (headerRow == null) {
        throw new IllegalArgumentException("Header row is missing in sheet '" + sheetName + "'");
      }

      // Find the column index by column name
      int cols = headerRow.getLastCellNum();
      int columnIndex = -1;
      for (int c = 0; c < cols; c++) {
        XSSFCell cell = headerRow.getCell(c);
        if (cell != null && columnName.equals(cell.getStringCellValue())) {
          columnIndex = c;
          break;
        }
      }

      if (columnIndex == -1) {
        throw new IllegalArgumentException("Column with name '" + columnName + "' not found");
      }

      // Create a DataFormatter instance
      DataFormatter dataFormatter = new DataFormatter();

      // Iterate through rows and retrieve values from the specified column
      int rows = sheet.getLastRowNum();
      for (int r = 1; r <= rows; r++) { // Start from row 1 to skip the header
        XSSFRow row = sheet.getRow(r);
        if (row == null)
          continue; // Skip null rows

        XSSFCell cell = row.getCell(columnIndex);
        if (cell != null) {
          // Use DataFormatter to get cell value as a String
          String cellValue = dataFormatter.formatCellValue(cell);
          columnValues.add(cellValue);
        }
      }

    } catch (IOException e) {
      System.err.println("Error reading Excel file: " + e.getMessage());
      throw e; // Re-throw the exception for further handling
    } finally {
      // Ensure resources are closed
      if (workbook != null) {
        workbook.close();
      }
      if (fileInputStream != null) {
        fileInputStream.close();
      }
    }

    return columnValues;
  }
}
