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
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePageValid {


    @Test(priority = 6, enabled = false, groups = "Home Page")
    public void ValidateLogoutFuncality() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);        driver.get("https://www.saucedemo.com/");
        WebElement username = driver.findElement(By.xpath("//input[@placeholder=\"Username\"]"));
        username.sendKeys("standard_user");
        WebElement password = driver.findElement(By.xpath("//input[@placeholder=\"Password\"]"));
        password.sendKeys("secret_sauce");
        WebElement Button = driver.findElement(By.xpath("//input[@type=\"submit\"]"));
        Button.click();
        WebElement Burrger = driver.findElement(By.id("react-burger-menu-btn"));
        Burrger.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement menue = driver.findElement(By.className("bm-menu"));
        WebElement Logout = driver.findElement(By.id("logout_sidebar_link"));
        Logout.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/"));
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/");
        driver.quit();
    }

    @Test(priority = 7,enabled = false,groups = "Home Page")
    public void ValidateAdddToCartFunctionality() {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("standard_user");
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("inventory"));

        List<WebElement> addButtons = driver.findElements(By.xpath("//button[text()='Add to cart']"));

        int productCount = addButtons.size();

        for (WebElement btn : addButtons) {
            btn.click();
        }
        WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge")));
        int actualCount = Integer.parseInt(cartBadge.getText());
        System.out.println("Expected: " + productCount);
        System.out.println("Actual: " + actualCount);
        Assert.assertEquals(actualCount, productCount);
        driver.quit();
    }



}



