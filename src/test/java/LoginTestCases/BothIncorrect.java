package LoginTestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class BothIncorrect {

    WebDriver driver;

    @BeforeMethod
    public void openBrowser(){
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Test
    public void bothIncorrect(){
        WebElement userName=driver.findElement(By.xpath("//input[@placeholder='Username']"));
        WebElement password=driver.findElement(By.xpath("//input[@placeholder='Password']"));
        WebElement loginButton=driver.findElement(By.xpath("//button[normalize-space()='Login']"));

        userName.sendKeys("Admin");
        password.sendKeys("admin123");
        loginButton.click();
    }

    @AfterMethod
    public void closeBrowser(){
        driver.quit();
    }
}
