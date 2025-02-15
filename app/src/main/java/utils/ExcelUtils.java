package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
  private static final String FILE_PATH = "C:\\Users\\abhis\\Downloads\\Selenium_Gradle_Automation\\app\\src\\test\\resources\\ProductsSearch.xlsx";

  public static List<String> getSearchItems(String sheetName) {
    List<String> searchItems = new ArrayList<>();
    try (FileInputStream fis = new FileInputStream(FILE_PATH);
        Workbook workbook = new XSSFWorkbook(fis)) {
      Sheet sheet = workbook.getSheet(sheetName);
      for (Row row : sheet) {
        Cell cell = row.getCell(0); // Assuming data is in the first column
        if (cell != null) {
          searchItems.add(cell.getStringCellValue());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return searchItems;
  }
}
