package tests;

// import java.io.IOException;
// import java.lang.reflect.Method;

// import org.testng.annotations.DataProvider;

// public class DP {
//   // Define the DataProvider with the correct name
//   @DataProvider(name = "pincodeProvider")
//   public Object[][] pincodeProvider(Method m) throws IOException {
//     switch (m.getName()) {
//       case "testPincode":
//         // Define test data directly
//         return new Object[][] {
//             { 560001 }, // Row 1
//             { 100001 }, // Row 2
//             { 400001 } // Row 3
//         };
//     }
//     return null;
//   }
// }

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import org.testng.annotations.DataProvider;

import utils.ExcelUtils;

public class DP {
  @DataProvider(name = "productProvider")
  public Object[][] productProvider(Method m) throws IOException {
    if (m.getName().equals("testFlipkartWithDirectData")) {
      return new Object[][] {
          { "iPhone 14" },
          { "Samsung Galaxy S23" },
          { "MacBook Air" },
          { "Sony Headphones" }
      };
    }
    return null;
  }

  @DataProvider(name = "excelProductProvider")
  public Object[][] excelProductProvider() throws IOException {
    List<String> searchItems = ExcelUtils.getSearchItems("Products");
    Object[][] data = new Object[searchItems.size()][1];

    for (int i = 0; i < searchItems.size(); i++) {
      data[i][0] = searchItems.get(i);
    }
    return data;
  }
}
