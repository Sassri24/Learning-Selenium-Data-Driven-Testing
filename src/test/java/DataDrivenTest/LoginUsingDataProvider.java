package DataDrivenTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginUsingDataProvider {

    WebDriver driver;

    @BeforeMethod
    public void openBrowser(){
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @DataProvider(name = "loginData")
    public String[][] dataProvider(){
        String[][] data={
                {"Admin","admin123","valid"},
                {"wrongAdmin","wrongadmin123","invalid"},
                {"wrongAdmin","admin123","invalid"},
                {"Admin","wrongadmin123","invalid"}
        };
        return data;
    }

    @Test(dataProvider = "loginData")

    public void loginUsingDataProvider(String uName,String pass,String validation){
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
}
