package validTestCase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class logginPage {

    @DataProvider(name = "loginValidData")
    public Object[][] getData() {
        return new Object[][]{
                {"standard_user", "secret_sauce"},

                {"problem_user", "secret_sauce"},
                {"performance_glitch_user", "secret_sauce"},
                {"error_user", "secret_sauce"},
                {"visual_user", "secret_sauce"}
        };
    }

    @Test(priority = 1, enabled = false, dataProvider = "loginValidData", groups = "Smoke")
    public void ValidateLoginWithCorrectData(String usernames, String passwords) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        WebElement username = driver.findElement(By.xpath("//input[@placeholder=\"Username\"]"));
        username.sendKeys(usernames);
        WebElement password = driver.findElement(By.xpath("//input[@placeholder=\"Password\"]"));
        password.sendKeys(passwords);
        WebElement Button = driver.findElement(By.xpath("//input[@type=\"submit\"]"));

        Button.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String Expectet = driver.getCurrentUrl();
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/inventory.html"));
        String Actual = "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(Actual, Expectet);
        driver.quit();
    }




    }






