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
import java.util.List;
import java.util.Map;

public class PymentsValidScenarios {
    @Test(priority = 8,enabled = false,groups = "payments")
    public void ValidatecCheckoutFunctionality()
    {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/");
        WebElement username = driver.findElement(By.xpath("//input[@placeholder=\"Username\"]"));
        username.sendKeys("standard_user");
        WebElement password = driver.findElement(By.xpath("//input[@placeholder=\"Password\"]"));
        password.sendKeys("secret_sauce");
        WebElement Button = driver.findElement(By.xpath("//input[@type=\"submit\"]"));
        Button.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/inventory.html"));
        List<WebElement> items = driver.findElements(By.className("inventory_item"));
        for (WebElement item : items) {

            String name = item.findElement(By.className("inventory_item_name")).getText();
            System.out.println(name);
            WebElement AddButton=item.findElement(By.tagName("button"));
            if(AddButton.getText().equals("Add to cart"))
            {
                AddButton.click();

            }
        }

        driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"checkout\"]")).click();

        driver.findElement(By.xpath("//*[@id=\"first-name\"]")).sendKeys("Ahmed");
        driver.findElement(By.xpath("//*[@id=\"last-name\"]")).sendKeys("Shaban");
        driver.findElement(By.xpath("//*[@id=\"postal-code\"]")).sendKeys("32574");
        driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"finish\"]")).click();
        Assert.assertEquals("https://www.saucedemo.com/checkout-complete.html", driver.getCurrentUrl());


    }
    @Test(priority = 8,enabled = true,groups = "payments")
    public void ValidateTotalPrice()
    {
        double sum = 0.0;
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/");
        WebElement username = driver.findElement(By.xpath("//input[@placeholder=\"Username\"]"));
        username.sendKeys("standard_user");
        WebElement password = driver.findElement(By.xpath("//input[@placeholder=\"Password\"]"));
        password.sendKeys("secret_sauce");
        WebElement Button = driver.findElement(By.xpath("//input[@type=\"submit\"]"));
        Button.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/inventory.html"));
        List<WebElement> items = driver.findElements(By.className("inventory_item"));
        for (WebElement item : items) {

            String name = item.findElement(By.className("inventory_item_name")).getText();
            System.out.println(name);
            WebElement AddButton=item.findElement(By.tagName("button"));
            if(AddButton.getText().equals("Add to cart"))
            {
                AddButton.click();
                String priceText = item.findElement(By.className("inventory_item_price")).getText();
                double price = Double.parseDouble(priceText.replaceAll("[^0-9.]", ""));
                sum += price;
                System.out.println(sum);

            }
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.findElement(By.xpath("//*[@id=\"checkout\"]")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        driver.findElement(By.xpath("//*[@id=\"first-name\"]")).sendKeys("Ahmed");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.findElement(By.xpath("//*[@id=\"last-name\"]")).sendKeys("Shaban");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.findElement(By.xpath("//*[@id=\"postal-code\"]")).sendKeys("32574");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        double tax = Double.parseDouble(driver.findElement(By.cssSelector("[data-test='tax-label']"))
                .getText().replaceAll("[^0-9.]", ""));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        double total = Double.parseDouble(driver.findElement(By.cssSelector("[data-test='total-label']"))
                .getText().replaceAll("[^0-9.]", ""));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        System.out.println("Tax = " + tax);
        System.out.println("Total = " + total);
        System.out.println("Sum of selected products = " + sum+tax);
        Assert.assertEquals(sum, total);
        driver.quit();
    }
}
