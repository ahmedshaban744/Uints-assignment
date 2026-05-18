package InvalidTestCase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class CheckOutForm {
    @DataProvider (name="CheckOutInvalidData")
    public Object[][] getData()
    {
        return new  Object [][]
        {
                {"", "","" },
                {"Ahmed","","" },
                {"","shaban","" },
                {"","","123456" },
                {"a","b","12365"},
                {"Ahmed","Shaban","mohamed"},
                {"Ahmed","Shaban",""},
                {"احمد","شعبان","123456"},
                {"123456","123456","123456"},
                {"A quiet morning begins with soft light drifting across the room. Thoughts wander between plans and memories, while small details shape the rhythm of the day. A cup of coffee rests nearby, sending warm aroma into the air. Ideas emerge slowly, like ripples spreading across still water. Focus returns, tasks take form, and progress follows step by step. Even simple moments carry meaning when observed closely and appreciated fully throughout the passing hours. Conversations arise with clarity, decisions become easier, and energy flows steadily toward completion of goals set earlier in the week guiding effort with purpose and balance overall","A quiet morning begins with soft light drifting across the room. Thoughts wander between plans and memories, while small details shape the rhythm of the day. A cup of coffee rests nearby, sending warm aroma into the air. Ideas emerge slowly, like ripples spreading across still water. Focus returns, tasks take form, and progress follows step by step. Even simple moments carry meaning when observed closely and appreciated fully throughout the passing hours. Conversations arise with clarity, decisions become easier, and energy flows steadily toward completion of goals set earlier in the week guiding effort with purpose and balance overall","125563+63"},
                {"Ahmed shaban","","12346"},
                {"ahmed@#4","@ahmeddf","1@25*"},


        };
    }

    @Test(priority = 9,enabled = true,groups = "pyments",dataProvider = "CheckOutInvalidData")
    public void ValidatefailCheckOutForm(String First_Name, String Last_Name,String ZipCode)

    {
        //login
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
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
      WebElement icon= driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a"));
        icon.click();
        wait2.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/cart.html"));

       WebElement CheckOut= driver.findElement(By.xpath("//*[@id=\"checkout\"]"));
       CheckOut.click();
       wait2.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/checkout-step-one.html"));

         WebElement first=driver.findElement(By.xpath("//*[@id=\"first-name\"]"));
         first.sendKeys(First_Name);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.xpath("//*[@id=\"last-name\"]")).sendKeys(Last_Name);

        driver.findElement(By.xpath("//*[@id=\"postal-code\"]")).sendKeys((ZipCode));

        WebElement continues=driver.findElement(By.xpath("//*[@id=\"continue\"]"));
        continues.click();
        WebElement massage= wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.error-message-container.error")));



        Assert.assertTrue(massage.isDisplayed());
       driver.close();
    }

}

