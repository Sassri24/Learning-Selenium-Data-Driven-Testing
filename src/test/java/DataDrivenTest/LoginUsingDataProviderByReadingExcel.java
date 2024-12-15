package DataDrivenTest;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

public class LoginUsingDataProviderByReadingExcel {

    WebDriver driver;

    @BeforeMethod
    public void openBrowser(){
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @DataProvider(name = "loginData")
    public Object[][] readExcelData() throws IOException {
        FileInputStream file=new FileInputStream(System.getProperty("user.dir")+"\\testData\\userDetails.xlsx");
        XSSFWorkbook workbook=new XSSFWorkbook(file);
        XSSFSheet sheet= workbook.getSheet("Sheet1");   //getSheetAt(0) --- by index---
        int rowCount=sheet.getLastRowNum();
        int columnCount=sheet.getRow(0).getLastCellNum();
        System.out.println("row count: "+ rowCount);       //output is 4 but actual row count is 5
        System.out.println("column count: "+columnCount);  //output is 3

        String[][] testData=new String[rowCount][columnCount];

        for(int r=1; r<=rowCount; r++){
            for(int c=0; c<columnCount; c++){
                testData[r-1][c] = sheet.getRow(r).getCell(c).toString();
            }
        }

        workbook.close();
        file.close();

        return testData;
    }

    @Test(dataProvider = "loginData")
    public void loginUsingDataProviderByExcel(String uName,String pass,String validation){
        WebElement userName=driver.findElement(By.xpath("//input[@placeholder='Username']"));
        WebElement password=driver.findElement(By.xpath("//input[@placeholder='Password']"));
        WebElement loginButton=driver.findElement(By.xpath("//button[normalize-space()='Login']"));

        userName.sendKeys(uName);
        password.sendKeys(pass);
        loginButton.click();

        boolean verifiedUrl=driver.getCurrentUrl().contains("dashboard");

        if(validation.equals("valid")){
            Assert.assertTrue(verifiedUrl,"Successfully login but not navigate to dashboard ");
        }else{
            Assert.assertFalse(verifiedUrl,"login not success but navigate to dashboard");
        }
    }

    @AfterMethod
    public void closeBrowser(){
        driver.quit();
    }


    //method for print excel data
        public void printExcelData() throws IOException {
        FileInputStream file=new FileInputStream(System.getProperty("user.dir")+"\\testData\\userDetails.xlsx");
        XSSFWorkbook workbook=new XSSFWorkbook(file);
        XSSFSheet sheet= workbook.getSheet("Sheet1");   //getSheetAt(0) --- by index---
        int rowCount=sheet.getLastRowNum();
        int columnCount=sheet.getRow(0).getLastCellNum();
        System.out.println("row count: "+ rowCount);       //output is 4 but actual row count is 5
        System.out.println("column count: "+columnCount);  //output is 3

        for(int r=0; r<=rowCount; r++){
            XSSFRow currentRow= sheet.getRow(r);
            for(int c=0; c<columnCount; c++){
                XSSFCell currentCell= currentRow.getCell(c);
                String cellValue= currentCell.toString();
                System.out.print(cellValue+ "\t\t");
            }
            System.out.println();
        }
    }

}


